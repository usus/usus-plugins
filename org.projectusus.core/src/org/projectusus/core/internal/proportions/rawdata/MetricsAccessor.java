package org.projectusus.core.internal.proportions.rawdata;

import static java.util.Arrays.asList;
import static org.projectusus.core.basis.CodeProportionKind.ACD;
import static org.projectusus.core.basis.CodeProportionKind.CC;
import static org.projectusus.core.basis.CodeProportionKind.CW;
import static org.projectusus.core.basis.CodeProportionKind.KG;
import static org.projectusus.core.basis.CodeProportionKind.ML;
import static org.projectusus.core.basis.CodeProportionKind.PC;
import static org.projectusus.core.internal.project.UsusProjectSupport.isUsusProject;

import java.util.ArrayList;
import java.util.Collection;
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
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.IHotspot;
import org.projectusus.core.filerelations.FileRelationMetrics;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.proportions.IMetricsWriter;
import org.projectusus.core.internal.util.CoreTexts;

import com.mountainminds.eclemma.core.analysis.IJavaModelCoverage;

public class MetricsAccessor implements IMetricsAccessor, IMetricsWriter {
    private final WorkspaceRawData workspaceRawData;
    private final FileRelationMetrics fileRelationMetrics;

    public MetricsAccessor() {
        super();
        workspaceRawData = new WorkspaceRawData();
        fileRelationMetrics = new FileRelationMetrics();
    }

    public void dropRawData( IProject project ) {
        for( IFile fileInProject : workspaceRawData.getProjectRawData( project ).getAllKeys() ) {
            fileRelationMetrics.handleFileRemoval( fileInProject );
        }
        workspaceRawData.dropRawData( project );
    }

    public void dropRawData( IFile file ) {
        fileRelationMetrics.handleFileRemoval( file );
        workspaceRawData.dropRawData( file );
    }

    public void addClassReference( BoundType sourceType, BoundType targetType ) {
        fileRelationMetrics.addFileRelation( ClassDescriptor.of( sourceType ), ClassDescriptor.of( targetType ) );
    }

    public void setCCValue( IFile file, MethodDeclaration methodDecl, int value ) {
        getOrCreateFileRawData( file ).setCCValue( methodDecl, value );
    }

    public void setCCValue( IFile file, Initializer initializer, int value ) {
        getOrCreateFileRawData( file ).setCCValue( initializer, value );
    }

    public void addClass( IFile file, AbstractTypeDeclaration node ) {
        getOrCreateFileRawData( file ).addClass( node );
        // TODO Aufruf dieser Methode ersetzen durch FileRawData.getOrCreateRawData
    }

    public void setMLValue( IFile file, MethodDeclaration methodDecl, int value ) {
        getOrCreateFileRawData( file ).setMLValue( methodDecl, value );
    }

    public void setMLValue( IFile file, Initializer initializer, int value ) {
        getOrCreateFileRawData( file ).setMLValue( initializer, value );
    }

    private FileRawData getOrCreateFileRawData( IFile file ) {
        return workspaceRawData.getProjectRawData( file.getProject() ).getOrCreateFileRawData( file );
    }

    private FileRawData getFileRawData( IFile file ) {
        return workspaceRawData.getProjectRawData( file.getProject() ).getFileRawData( file );
    }

    private ProjectRawData getProjectRawData( IProject project ) {
        return workspaceRawData.getProjectRawData( project );
    }

    private ClassRawData getClassRawData( IType clazz ) {
        try {
            IFile file = (IFile)clazz.getUnderlyingResource();
            FileRawData fileResults = getFileRawData( file );
            if( fileResults != null ) {
                return fileResults.getRawData( clazz );
            }
        } catch( JavaModelException jme ) {
            // nothing
        }
        return null;
    }

    public void updateCoverage( IJavaModelCoverage javaModelCoverage ) {
        for( IJavaProject javaProject : javaModelCoverage.getInstrumentedProjects() ) {
            IProject project = javaProject.getProject();
            if( isUsusProject( project ) ) {
                getProjectRawData( project ).setInstructionCoverage( javaModelCoverage.getCoverageFor( javaProject ) );
            }
        }
    }

    public void setWarningsCount( IFile file, int markerCount ) {
        getProjectRawData( file.getProject() ).setWarningsCount( file, markerCount );
    }

    public void setWarningsCount( IProject project, int markerCount ) {
        getProjectRawData( project ).setWarningsCount( markerCount );
    }

    private CodeProportion getCodeProportion( CodeProportionKind metric ) {
        if( metric == CodeProportionKind.PC ) {
            CodeStatistic basis = new CodeStatistic( metric.getUnit(), Packagename.getAll().size() );
            int violations = fileRelationMetrics.getPackageRelations().getPackageCycles().numberOfPackagesInAnyCycles();
            List<IHotspot> hotspots = new ArrayList<IHotspot>();
            // TODO add hotspots
            return new CodeProportion( metric, violations, basis, hotspots );
        }

        if( metric == ACD ) {
            CodeStatistic basis = workspaceRawData.getCodeStatistic( metric.getUnit() );
            List<IHotspot> hotspots = workspaceRawData.computeHotspots( metric );
            int violations = workspaceRawData.getViolationCount( metric );
            double levelValue = 100.0 - 100.0 * getRelativeACD();
            return new CodeProportion( metric, violations, basis, levelValue, hotspots );
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

    public int getNumberOfMethods( IType type ) {
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

    public int getCCD( IType type ) {
        ClassRawData classRawData = getClassRawData( type );
        if( classRawData == null ) {
            return 0;
        }
        return classRawData.getCCDResult();
    }

    public int getViolationCount( IProject project, CodeProportionKind metric ) {
        return getProjectRawData( project ).getViolationCount( metric );
    }

    public Set<ClassRepresenter> getAllClassRepresenters() {
        return ClassRepresenter.transformToRepresenterSet( fileRelationMetrics.getAllClassDescriptors(), fileRelationMetrics );
    }

    public Set<PackageRepresenter> getAllPackages() {
        return PackageRepresenter.transformToRepresenterSet( fileRelationMetrics.getAllPackages(), fileRelationMetrics.getPackageRelations() );
    }

    public int getCCValue( IMethod method ) {
        return getMethodRawData( method ).getCCValue();
    }

    public int getMLValue( IMethod method ) {
        return getMethodRawData( method ).getMLValue();
    }

    private MethodRawData getMethodRawData( IMethod method ) {
        ClassRawData classRawData = getClassRawData( method.getDeclaringType() );
        if( classRawData == null ) {
            throw new IllegalStateException();
        }
        MethodRawData methodRawData = classRawData.getMethodRawData( method );
        if( methodRawData == null ) {
            throw new IllegalStateException();
        }
        return methodRawData;
    }

    public double getRelativeACD() {
        return fileRelationMetrics.getRelativeACD();
    }

    public int getNumberOfWarnings( IFile file ) {
        return getFileRawData( file ).getViolationCount( CW );
    }

    public YellowCountResult getWarnings() {
        return new YellowCountResult( this.getNumberOf( CodeProportionUnit.PROJECT ), this.getNumberOf( CodeProportionKind.CW.getUnit() ), this
                .getOverallMetric( CodeProportionKind.CW ), this.getNumberOfProjectsViolatingCW() );
    }

    public ArrayList<CodeProportion> getCodeProportions() {
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
            count = count + (projectRawData.hasWarnings() ? 1 : 0);
        }
        return count;
    }

    public FileRelationMetrics getFileRelationMetrics() {
        return fileRelationMetrics;
    }

    public void repairRelations( IProgressMonitor monitor ) {
        List<FileRelation> candidates = fileRelationMetrics.findRelationsThatNeedRepair();
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
            fileRelationMetrics.remove( relation );
        } else {
            ClassRawData classRawData = fileRawData.findClass( relation.getTargetClassname() );
            if( classRawData == null ) {
                fileRelationMetrics.remove( relation );
            }
        }
    }

}
