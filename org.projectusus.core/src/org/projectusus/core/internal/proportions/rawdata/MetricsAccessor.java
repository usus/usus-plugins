package org.projectusus.core.internal.proportions.rawdata;

import static java.util.Arrays.asList;
import static org.projectusus.core.basis.CodeProportionKind.ACD;
import static org.projectusus.core.basis.CodeProportionKind.CC;
import static org.projectusus.core.basis.CodeProportionKind.KG;
import static org.projectusus.core.basis.CodeProportionKind.ML;
import static org.projectusus.core.basis.CodeProportionKind.PC;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.IMetricsAccessor;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.basis.IHotspot;
import org.projectusus.core.basis.YellowCountCache;
import org.projectusus.core.basis.YellowCountResult;
import org.projectusus.core.filerelations.internal.metrics.ACDCalculator;
import org.projectusus.core.filerelations.internal.model.CrossPackageClassRelations;
import org.projectusus.core.filerelations.internal.model.PackageRelations;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.ClassDescriptorCleanup;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.proportions.IMetricsWriter;
import org.projectusus.core.internal.util.CoreTexts;

public class MetricsAccessor implements IMetricsAccessor, IMetricsWriter {
    private final WorkspaceRawData workspaceRawData;

    public MetricsAccessor() {
        super();
        workspaceRawData = new WorkspaceRawData();
    }

    public void dropRawData( IProject project ) {
        workspaceRawData.dropRawData( project );
    }

    public void dropRawData( IFile file ) {
        ProjectRawData projectRawData = workspaceRawData.getProjectRawData( file.getProject() );
        if( projectRawData != null ) {
            projectRawData.dropRawData( file );
        }
    }

    public void addClassReference( BoundType sourceType, BoundType targetType ) {
        ClassDescriptor.of( sourceType ).addChild( ClassDescriptor.of( targetType ) );
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
        return workspaceRawData.getOrCreateProjectRawData( file.getProject() ).getOrCreateFileRawData( file );
    }

    private FileRawData getFileRawData( IFile file ) {
        ProjectRawData projectRawData = workspaceRawData.getProjectRawData( file.getProject() );
        if( projectRawData == null ) {
            return null;
        }
        return projectRawData.getFileRawData( file );
    }

    public void acceptAndGuide( MetricsResultVisitor visitor ) {
        workspaceRawData.acceptAndGuide( visitor );
    }

    private CodeProportion getCodeProportion( CodeProportionKind metric ) {
        CodeStatistic basis = getCodeStatistic( metric.getUnit() );

        if( metric == PC ) {
            int violations = new PackageRelations().getPackageCycles().numberOfPackagesInAnyCycles();
            return new CodeProportion( metric, violations, basis, new ArrayList<IHotspot>(), false );
        }
        if( metric == ACD ) {
            ACDStatistic acdStatistic = new ACDStatistic();
            double levelValue = 100.0 - 100.0 * acdStatistic.getRelativeACD();
            return new CodeProportion( metric, acdStatistic.getViolations(), basis, levelValue, acdStatistic.getHotspots(), true );
        }

        DefaultStatistic statistic = null;
        if( metric == CodeProportionKind.ML ) {
            statistic = new MethodLengthStatistic();
        }
        if( metric == CodeProportionKind.CC ) {
            statistic = new CyclomaticComplexityStatistic();
        }
        if( metric == CodeProportionKind.KG ) {
            statistic = new ClassSizeStatistic();
        }

        if( statistic == null ) {
            throw new IllegalArgumentException( "Cannot get code statistic of code proportion kind " + metric ); //$NON-NLS-1$
        }
        return new CodeProportion( metric, statistic.getViolations(), basis, statistic.getHotspots(), true );
    }

    // TODO CodeStatistic cachen?
    private CodeStatistic getCodeStatistic( CodeProportionUnit unit ) {
        if( unit == CodeProportionUnit.PACKAGE ) {
            return new PackageCountVisitor().getCodeStatistic();
        }
        if( unit == CodeProportionUnit.CLASS ) {
            return new ClassCountVisitor().getCodeStatistic();
        }
        if( unit == CodeProportionUnit.METHOD ) {
            return new MethodCountVisitor().getCodeStatistic();
        }
        throw new IllegalArgumentException( "Cannot get code statistic of code proportion unit " + unit ); //$NON-NLS-1$
    }

    public Set<GraphNode> getAllClassRepresenters() {
        return ClassRepresenter.transformToRepresenterSet( ClassDescriptor.getAll() );
    }

    public Set<GraphNode> getAllPackages() {
        return PackageRepresenter.transformToRepresenterSet( Packagename.getAll(), new PackageRelations() );
    }

    public Set<GraphNode> getAllCrossPackageClasses() {
        return CrossPackageClassRepresenter.transformToRepresenterSet( ClassDescriptor.getAll(), new CrossPackageClassRelations() );
    }

    public double getRelativeACD() {
        return ACDCalculator.getRelativeACD();
    }

    public YellowCountResult getWarnings() {
        return YellowCountCache.yellowCountCache().getResult();
    }

    public List<CodeProportion> getCodeProportions() {
        List<CodeProportion> entries = new ArrayList<CodeProportion>();
        for( CodeProportionKind metric : asList( CC, KG, ML, ACD, PC ) ) {
            entries.add( getCodeProportion( metric ) );
        }
        return entries;
    }

    public void cleanupRelations( IProgressMonitor monitor ) {
        Set<ClassDescriptor> candidates = ClassDescriptorCleanup.extractDescriptorsRegisteredForCleanup();
        monitor.beginTask( null, candidates.size() );
        monitor.subTask( CoreTexts.ususModel_UpdatingFileRelations );
        for( ClassDescriptor descriptor : candidates ) {
            removeRelationIfTargetIsGone( descriptor );
            monitor.worked( 1 );
        }
        monitor.done();
    }

    private void removeRelationIfTargetIsGone( ClassDescriptor descriptor ) {
        IFile targetFile = descriptor.getFile();
        FileRawData fileRawData = getFileRawData( targetFile );
        if( fileRawData == null ) {
            descriptor.removeFromPool();
        } else {
            ClassRawData classRawData = fileRawData.findClass( descriptor.getClassname() );
            if( classRawData == null ) {
                descriptor.removeFromPool();
            }
        }
    }

}
