// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.mountainminds.eclemma.core.analysis.ICounter;

public class CoverageTest {

 

    @Test
    public void addCoverage() throws Exception {
        TestCoverage coverage = new TestCoverage( 23, 100 ).add( new TestCoverage( 17, 50 ) );
        assertEquals( 40, coverage.getCoveredCount() );
        assertEquals( 150, coverage.getTotalCount() );
    }

    @Test
    public void getCoverageInPercent_not_coveredProject() throws Exception {
        assertEquals( BigDecimal.ZERO, new TestCoverage( 0, 0 ).getCoverageInPercent() );
        assertEquals( BigDecimal.ZERO, new TestCoverage( 0, 100 ).getCoverageInPercent() );
    }

    @Test
    public void getCoverageInPercent_coveredProject() throws Exception {
        assertEquals( new BigDecimal( "43" ), new TestCoverage( 43, 100 ).getCoverageInPercent() );
        assertEquals( new BigDecimal( "4" ), new TestCoverage( 16, 400 ).getCoverageInPercent() );
    }

    @Test
    public void getCoverageInPercent_coveredProject_roundPercentage() throws Exception {
        assertEquals( new BigDecimal( "1.5" ), new TestCoverage( 3, 200 ).getCoverageInPercent() );
        assertEquals( new BigDecimal( "33.3" ), new TestCoverage( 3, 9 ).getCoverageInPercent() );
    }

    @Test
    public void getCoverageInPercentDisplayString() throws Exception {
        assertEquals( "43.0 %", new TestCoverage( 43, 100 ).getCoverageInPercentDisplayString() );
        assertEquals( "33.3 %", new TestCoverage( 3, 9 ).getCoverageInPercentDisplayString() );
    }

    @Test
    public void from() throws Exception {
        TestCoverage coverage = TestCoverage.from( createCounter( 1, 2 ) );
        assertEquals( new TestCoverage( 1, 2 ), coverage );
    }

    private ICounter createCounter( final int coveredCount, final int totalCount ) {
        return new TestCounter( coveredCount, totalCount );
    }
    
    private final class TestCounter implements ICounter {
        private final int coveredCount;
        private final int totalCount;

        private TestCounter( int coveredCount, int totalCount ) {
            this.coveredCount = coveredCount;
            this.totalCount = totalCount;
        }

        public long getCoveredCount() {
            return coveredCount;
        }

        public double getRatio() {
            return 0;
        }

        public long getTotalCount() {
            return totalCount;
        }

        public int compareTo( Object arg0 ) {
            return 0;
        }
    }
}
