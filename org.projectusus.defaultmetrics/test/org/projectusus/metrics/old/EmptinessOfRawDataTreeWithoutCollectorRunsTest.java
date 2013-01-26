package org.projectusus.metrics.old;

import static org.junit.Assert.assertEquals;
import static org.projectusus.metrics.util.CountingUtils.getNumberOfClasses;
import static org.projectusus.metrics.util.CountingUtils.getNumberOfMethods;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.metrics.util.ClassValueVisitor;
import org.projectusus.metrics.util.MethodValueVisitor;

public class EmptinessOfRawDataTreeWithoutCollectorRunsTest {

    @Before
    public void setup() {
        UsusModelProvider.clear();
    }

    @Test
    public void classValueVisitorCheckingAbstractness() {
        ClassValueVisitor visitor = new ClassValueVisitor( MetricsResults.ABSTRACTNESS );
        visitor.visit();

        assertEquals( 0, getNumberOfClasses() );
    }

    @Test
    public void methodValueVisitorCheckingCC() {
        MethodValueVisitor visitor = new MethodValueVisitor( MetricsResults.CC );

        visitor.visit();

        assertEquals( 0, getNumberOfClasses() );
        assertEquals( 0, getNumberOfMethods() );
    }

}
