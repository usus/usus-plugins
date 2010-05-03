// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static java.util.Arrays.asList;
import static org.projectusus.core.internal.project.UsusProjectSupport.isUsusProject;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ACD;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CC;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CW;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.KG;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ML;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.PC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.filerelations.FileRelationMetrics;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.IUsusModelMetricsWriter;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.CodeStatistic;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.model.UsusModelCache;
import org.projectusus.core.internal.util.CoreTexts;

import com.mountainminds.eclemma.core.analysis.IJavaModelCoverage;

public class UsusModel implements IUsusModel, IUsusModelWriteAccess, IUsusModelMetricsWriter {

    private final Set<IUsusModelListener> listeners;
    private final CheckpointHistory history;
    private final UsusModelCache cache;
    private final WorkspaceRawData workspaceRawData;
    private final FileRelationMetrics fileRelations;
    private boolean needsFullRecompute;

    public UsusModel() {
        cache = new UsusModelCache();
        listeners = new HashSet<IUsusModelListener>();
        history = new CheckpointHistory();
        workspaceRawData = new WorkspaceRawData();
        fileRelations = new FileRelationMetrics();
        needsFullRecompute = false;
    }

    // interface of IUsusModelWriteAccess
    // //////////////////////////////////

    public void updateAfterComputationRun( boolean computationSuccessful, IProgressMonitor monitor ) {
        needsFullRecompute = !computationSuccessful;
        repairRelations( monitor );
        ArrayList<CodeProportion> codeProportions = getCodeProportions();
        history.addComputationResult( codeProportions );
        cache.refreshAll( codeProportions );
        notifyListeners();
    }

    public void dropRawData( IProject project ) {
        for( IFile fileInProject : workspaceRawData.getProjectRawData( project ).getAllKeys() ) {
            fileRelations.handleFileRemoval( fileInProject );
        }
        workspaceRawData.dropRawData( project );
    }

    public void dropRawData( IFile file ) {
        fileRelations.handleFileRemoval( file );
        workspaceRawData.dropRawData( file );
    }

    private void repairRelations( IProgressMonitor monitor ) {
        List<FileRelation> candidates = fileRelations.findRelationsThatNeedRepair();
        monitor.beginTask( null, candidates.size() );
        monitor.subTask( CoreTexts.ususModel_UpdatingFileRelations );
        for( FileRelation relation : candidates ) {
            removeRelationIfTargetIsGone( relation );
            monitor.worked( 1 );
        }
        monitor.done();
    }

    private void removeRelationIfTargetIsGone( FileRelation relation ) {
        IFile targetFile = relation.getTargetFile();
        FileRawData fileRawData = getFileRawData( targetFile );
        if( fileRawData == null ) {
            fileRelations.remove( relation );
        } else {
            ClassRawData classRawData = fileRawData.findClass( relation.getTargetClassname() );
            if( classRawData == null ) {
                fileRelations.remove( relation );
            }
        }
    }

    // interface of IUsusModelMetricsWriter
    // /////////////////////////////////////

    public void addClassReference( ITypeBinding sourceType, ITypeBinding targetType ) {
        ClassDescriptor source = ClassDescriptor.of( sourceType );
        ClassDescriptor target = ClassDescriptor.of( targetType );
        fileRelations.addFileRelation( source, target );
    }

    public void setCCValue( IFile file, MethodDeclaration methodDecl, int value ) {
        getFileRawData( file ).setCCValue( methodDecl, value );
    }

    public void setCCValue( IFile file, Initializer initializer, int value ) {
        getFileRawData( file ).setCCValue( initializer, value );
    }

    public void addClass( IFile file, AbstractTypeDeclaration node ) {
        getFileRawData( file ).addClass( node );
        // TODO Aufruf dieser Methode ersetzen durch FileRawData.getOrCreateRawData
        // TODO Unterer Aufruf ist unnštig, da schon ClassRawData die ClassDescriptor erzeugt
        fileRelations.addClass( node.resolveBinding() );
    }

    public void setMLValue( IFile file, MethodDeclaration methodDecl, int value ) {
        getFileRawData( file ).setMLValue( methodDecl, value );
    }

    public void setMLValue( IFile file, Initializer initializer, int value ) {
        getFileRawData( file ).setMLValue( initializer, value );
    }

    public void collectCoverageInfo( IJavaModelCoverage javaModelCoverage ) {
        // UsusCorePlugin.getUsusModelMetricsWriter().resetInstructionCoverage(); ??
        IJavaProject[] instrumentedProjects = javaModelCoverage.getInstrumentedProjects();
        if( instrumentedProjects.length == 0 ) {
            return;
        }
        for( IJavaProject javaProject : instrumentedProjects ) {
            IProject project = javaProject.getProject();
            if( isUsusProject( project ) ) {
                getProjectRawData( project ).setInstructionCoverage( javaModelCoverage.getCoverageFor( javaProject ) );
            }
        }
        CodeProportion codeProportion = getCodeProportion( CodeProportionKind.TA );
        history.addTestResult( codeProportion );
        cache.refresh( codeProportion );
        notifyListeners();
    }

    public void setYellowCount( IFile file, int markerCount ) {
        getProjectRawData( file.getProject() ).setYellowCount( file, markerCount );
    }

    public void setYellowCount( IProject project, int markerCount ) {
        getProjectRawData( project ).setYellowCount( markerCount );
    }

    public FileRelationMetrics getFileRelationMetrics() {
        return fileRelations;
    }

    // interface of IUsusModel
    // ////////////////////////

    public CheckpointHistory getHistory() {
        return history;
    }

    public IUsusElement[] getElements() {
        return cache.getElements().clone();
    }

    // ///////////////////////////////////////////////////////////////////////

    // Methoden, die auf WorkspaceRawData zugreifen:
    public CodeProportion getCodeProportion( CodeProportionKind metric ) {
        if( metric == CodeProportionKind.PC ) {
            CodeStatistic basis = new CodeStatistic( metric.getUnit(), Packagename.getAll().size() );
            int violations = fileRelations.getPackageCycles().numberOfPackagesInAnyCycles();
            List<IHotspot> hotspots = new ArrayList<IHotspot>();
            // TODO add hotspots
            return new CodeProportion( metric, violations, basis, hotspots );
        }
        return workspaceRawData.getCodeProportion( metric );
    }

    public int getNumberOf( CodeProportionUnit unit ) {
        return workspaceRawData.getNumberOf( unit );
    }

    public int getNumberOf( IProject project, CodeProportionUnit unit ) {
        ProjectRawData projectRD = workspaceRawData.getRawData( project );
        if( projectRD == null ) {
            return 0;
        }
        return projectRD.getNumberOf( unit );
    }

    public int getNumberOfMethods( IType type ) throws JavaModelException {
        ClassRawData classRawData = getClassRawData( type );
        if( classRawData == null ) {
            return 0;
        }
        return classRawData.getNumberOfMethods();
    }

    public int getOverallMetric( CodeProportionKind metric ) {
        return workspaceRawData.getOverallMetric( metric );
    }

    public int getOverallMetric( IProject project, CodeProportionKind metric ) {
        ProjectRawData projectRD = workspaceRawData.getRawData( project );
        if( projectRD != null ) {
            return projectRD.getOverallMetric( metric );
        }
        return 0;
    }

    public int getViolationCount( IProject project, CodeProportionKind metric ) {
        ProjectRawData projectRawData = getProjectRawData( project );
        return projectRawData.getViolationCount( metric );
    }

    public List<Integer> getAllClassesCCDResults() {
        Set<ClassRawData> classes = workspaceRawData.getAllClassRawData();
        List<Integer> ccdList = new ArrayList<Integer>();
        for( ClassRawData node : classes ) {
            ccdList.add( new Integer( node.getCCDResult() ) );
        }
        return ccdList;
    }

    public Set<ClassRepresenter> getAllClassRepresenters() {
        return ClassRepresenter.transformToRepresenterSet( fileRelations.getAllClassDescriptors(), fileRelations );
    }

    public Set<PackageRepresenter> getAllPackages() {
        return PackageRepresenter.transformToRepresenterSet( fileRelations.getAllPackages(), fileRelations );
    }

    public int getCCValue( IMethod method ) throws JavaModelException {
        ClassRawData classRawData = getClassRawData( method.getDeclaringType() );
        MethodRawData methodRawData = classRawData.getMethodRawData( method );
        if( methodRawData != null ) {
            return methodRawData.getCCValue();
        }
        throw new IllegalStateException();
    }

    public int getMLValue( IMethod method ) throws JavaModelException {
        ClassRawData classRawData = getClassRawData( method.getDeclaringType() );
        MethodRawData methodRawData = classRawData.getMethodRawData( method );
        if( methodRawData != null ) {
            return methodRawData.getMLValue();
        }
        throw new IllegalStateException();
    }

    public int getNumberOfCompilerWarnings( IFile file ) throws JavaModelException {
        return getFileRawData( file ).getViolationCount( CW );
    }

    // //////////////////////////////////

    FileRawData getFileRawData( IFile file ) {
        return workspaceRawData.getProjectRawData( file.getProject() ).getFileRawData( file );
    }

    ProjectRawData getProjectRawData( IProject project ) {
        return workspaceRawData.getProjectRawData( project );
    }

    ClassRawData getClassRawData( IType clazz ) throws JavaModelException {
        IFile file = (IFile)clazz.getUnderlyingResource();
        ProjectRawData projectRD = workspaceRawData.getRawData( file.getProject() );
        FileRawData fileResults = projectRD.getFileRawData( file );
        return fileResults.getOrCreateRawData( clazz );
    }

    // //////////////////////////////////

    public void addUsusModelListener( IUsusModelListener listener ) {
        listeners.add( listener );
    }

    public void removeUsusModelListener( IUsusModelListener listener ) {
        listeners.remove( listener );
    }

    // internal
    // ////////

    private void notifyListeners() {
        for( IUsusModelListener listener : listeners ) {
            listener.ususModelChanged( history );
        }
    }

    private ArrayList<CodeProportion> getCodeProportions() {
        ArrayList<CodeProportion> entries = new ArrayList<CodeProportion>();
        for( CodeProportionKind metric : asList( CC, KG, ML, ACD, CW, PC ) ) {
            entries.add( getCodeProportion( metric ) );
        }
        return entries;
    }

    public int getNumberOfProjectsViolatingCW() {
        int count = 0;
        Collection<ProjectRawData> allRawDataElements = workspaceRawData.getAllRawDataElements();
        for( ProjectRawData projectRawData : allRawDataElements ) {
            count = count + projectRawData.getViolationCount( CodeProportionKind.CW ) > 0 ? 1 : 0;
        }
        return count;
    }

    public boolean needsFullRecompute() {
        return needsFullRecompute;
    }
}
