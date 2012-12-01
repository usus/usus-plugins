package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

public class AbstractClassCollectorPDETest extends PDETestForMetricsComputation {

    @Test
    public void interfaceIsAbstract() throws Exception {
        setupWorkspace( "Intf.java" );

        AbstractnessVisitor visitor = createAndVisit();

        assertThat( visitor.getAbstractness(), is( 1 ) );
        assertThat( visitor.getName(), is( "Intf" ) );
    }

    @Test
    public void abstractClassIsAbstract() throws Exception {
        setupWorkspace( "Abstract.java" );

        AbstractnessVisitor visitor = createAndVisit();

        assertThat( visitor.getAbstractness(), is( 1 ) );
        assertThat( visitor.getName(), is( "Abstract" ) );
    }

    @Test
    public void annotationTypeIsConcrete() throws Exception {
        setupWorkspace( "AnnotationType.java" );

        AbstractnessVisitor visitor = createAndVisit();

        assertThat( visitor.getAbstractness(), is( 0 ) );
        assertThat( visitor.getName(), is( "AnnotationType" ) );
    }

    @Test
    public void concreteClassIsConcrete() throws Exception {
        setupWorkspace( "Concrete.java" );

        AbstractnessVisitor visitor = createAndVisit();

        assertThat( visitor.getAbstractness(), is( 0 ) );
        assertThat( visitor.getName(), is( "Concrete" ) );
    }

    @Test
    public void enumIsConcrete() throws Exception {
        setupWorkspace( "Enumm.java" );

        AbstractnessVisitor visitor = createAndVisit();

        assertThat( visitor.getAbstractness(), is( 0 ) );
        assertThat( visitor.getName(), is( "Enumm" ) );
    }

    private AbstractnessVisitor createAndVisit() {
        AbstractnessVisitor visitor = new AbstractnessVisitor();
        visitor.visit();
        return visitor;
    }

    private void setupWorkspace( String filename ) throws CoreException, Exception {
        setupWorkspace( "abstractness", filename );
    }

    private void setupWorkspace( String packagename, String filename ) throws CoreException, Exception {
        project.createFolder( packagename );
        createJavaFile( packagename + "/" + filename );
        workspace.buildFullyAndWait();
    }
}

class AbstractnessVisitor extends DefaultMetricsResultVisitor {
    private int abstractness;
    private String name;

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        name = location.getName();
        abstractness = results.getIntValue( MetricsResults.ABSTRACTNESS );
    }

    public int getAbstractness() {
        return abstractness;
    }

    public String getName() {
        return name;
    }
}
