// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static java.util.Arrays.asList;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ACD;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CC;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CW;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.KG;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ML;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
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
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.IUsusModelMetricsWriter;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.model.UsusModelCache;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelHistory;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate;
import org.projectusus.core.internal.proportions.modelupdate.UsusModelHistory;

import com.mountainminds.eclemma.core.analysis.IJavaElementCoverage;

public class UsusModel implements IUsusModel, IUsusModelWriteAccess, IUsusModelMetricsWriter {

    private final Set<IUsusModelListener> listeners;
    private final UsusModelHistory history;
    private final UsusModelCache cache;
    private final WorkspaceRawData workspaceRawData;
    private final FileRelationMetrics fileRelations;

    public UsusModel() {
        cache = new UsusModelCache();
        listeners = new HashSet<IUsusModelListener>();
        history = new UsusModelHistory();
        workspaceRawData = new WorkspaceRawData();
        fileRelations = new FileRelationMetrics();
    }

    // interface of IUsusModelWriteAccess
    // //////////////////////////////////

    public void update( IUsusModelUpdate updateCommand ) {
        if( updateCommand == null || updateCommand.getType() == null ) {
            throw new IllegalArgumentException();
        }
        doUpdate( updateCommand );
    }

    public void dropRawData( IProject project ) {
        for( IFile fileInProject : workspaceRawData.getProjectRawData( project ).getAllKeys() ) {
            fileRelations.remove( fileInProject );
        }
        workspaceRawData.dropRawData( project );
    }

    public void dropRawData( IFile file ) {
        fileRelations.remove( file );
        workspaceRawData.dropRawData( file );
    }

    public void updateComputation( boolean computationSuccessful ) {
        ArrayList<CodeProportion> proportions = getCodeProportions();
        cache.refreshAll( proportions );
        history.add( new ComputationRunModelUpdate( proportions, computationSuccessful ) );
        List<FileRelation> relationsToRepair = fileRelations.findRelationsThatNeedRepair();
        repairRelations( relationsToRepair );
        notifyListeners();
    }

    private void repairRelations( List<FileRelation> relationsToRepair ) {
        for( FileRelation relation : relationsToRepair ) {
            System.out.println( "Found relation to repair: " + relation );
            removeRelationIfTargetIsGone( relation );
        }

    }

    private void removeRelationIfTargetIsGone( FileRelation relation ) {
        IFile targetFile = relation.getTargetFile();
        FileRawData fileRawData = getFileRawData( targetFile );
        if( fileRawData == null ) {
            System.out.println( "Cannot find file, removing relation " + relation );
            fileRelations.remove( relation );
        } else {
            ClassRawData classRawData = fileRawData.findClass( relation.getTargetClassname() );
            if( classRawData == null ) {
                System.out.println( "Cannot find target class, removing relation " + relation );
                fileRelations.remove( relation );
            } else {
                System.out.println( "Target class found, relation repaired." );
            }
        }
    }

    // interface of IUsusModelMetricsWriter
    // /////////////////////////////////////

    public void addClassReference( ITypeBinding sourceType, ITypeBinding targetType ) {
        try {
            ClassDescriptor source = new ClassDescriptor( sourceType );
            ClassDescriptor target = new ClassDescriptor( targetType );
            fileRelations.addFileRelation( source, target );
        } catch( JavaModelException e ) {
            e.printStackTrace();
        }
    }

    public void setCCValue( IFile file, MethodDeclaration methodDecl, int value ) {
        getFileRawData( file ).setCCValue( methodDecl, value );
    }

    public void setCCValue( IFile file, Initializer initializer, int value ) {
        getFileRawData( file ).setCCValue( initializer, value );
    }

    public void addClass( IFile file, AbstractTypeDeclaration node ) {
        getFileRawData( file ).addClass( node );
        try {
            fileRelations.addClass( node.resolveBinding() );
        } catch( JavaModelException e ) {
            e.printStackTrace();
        }
    }

    public void setMLValue( IFile file, MethodDeclaration methodDecl, int value ) {
        getFileRawData( file ).setMLValue( methodDecl, value );
    }

    public void setMLValue( IFile file, Initializer initializer, int value ) {
        getFileRawData( file ).setMLValue( initializer, value );
    }

    public void setInstructionCoverage( IProject project, IJavaElementCoverage coverage ) {
        getProjectRawData( project ).setInstructionCoverage( coverage );
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

    public IUsusModelHistory getHistory() {
        return history;
    }

    public IUsusElement[] getElements() {
        return cache.getElements();
    }

    // ///////////////////////////////////////////////////////////////////////

    // Methoden, die auf WorkspaceRawData zugreifen:
    public CodeProportion getCodeProportion( CodeProportionKind metric ) {
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

    // public int getSumOfAllDirectChildrenOfAllClasses() {
    // int sumDirectChildren = 0;
    // for( ClassRawData clazz : workspaceRawData.getAllClassRawData() ) {
    // sumDirectChildren += clazz.getChildren().size();
    // }
    // return sumDirectChildren;
    // }
    //
    // public int getSumOfAllKnownChildrenOfAllClasses() {
    // int sumKnownClasses = 0;
    // for( ClassRawData clazz : workspaceRawData.getAllClassRawData() ) {
    // sumKnownClasses += clazz.getAllChildren().size();
    // }
    // return sumKnownClasses;
    // }
    //
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

    public int getNumberOfCompilerWarnings( IMethod method ) throws JavaModelException {
        return getFileRawData( (IFile)method.getUnderlyingResource() ).getViolationCount( CW );
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

    private void doUpdate( IUsusModelUpdate updateCommand ) {
        cache.refreshAll( updateCommand.getEntries() );
        history.add( updateCommand );
        notifyListeners();
    }

    private ArrayList<CodeProportion> getCodeProportions() {
        ArrayList<CodeProportion> entries = new ArrayList<CodeProportion>();
        for( CodeProportionKind metric : asList( CC, KG, ML, ACD, CW ) ) {
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
}
