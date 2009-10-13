// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import com.mountainminds.eclemma.core.analysis.ICounter;

public class TestCoverage {

    private int totalCount;
    private int coveredCount;

    public TestCoverage( int coveredCount, int totalCount ) {
        super();
        this.coveredCount = coveredCount;
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount( int totalCount ) {
        this.totalCount = totalCount;
    }

    public int getCoveredCount() {
        return coveredCount;
    }

    public void setCoveredCount( int coveredCount ) {
        this.coveredCount = coveredCount;
    }

    public BigDecimal getCoverageInPercent() {
        if( getTotalCount() == 0 ) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal( 1000 * getCoveredCount() ).divide( new BigDecimal( getTotalCount() ), RoundingMode.HALF_UP ).divide( BigDecimal.TEN );
    }

    public String getCoverageInPercentDisplayString() {
        BigDecimal coverageInPercent = getCoverageInPercent();
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMaximumIntegerDigits( 3 );
        numberInstance.setMaximumFractionDigits( 2 );
        numberInstance.setMinimumFractionDigits( 1 );
        return numberInstance.format( coverageInPercent ) + " %"; //$NON-NLS-1$
    }

    public TestCoverage add( TestCoverage other ) {
        int newTotalCount = this.getTotalCount() + other.getTotalCount();
        int newCoveredCount = this.getCoveredCount() + other.getCoveredCount();
        return new TestCoverage( newCoveredCount, newTotalCount );
    }

    @Override
    public boolean equals( Object other ) {
        if( !(other instanceof TestCoverage) ) {
            return false;
        }
        TestCoverage otherCoverage = (TestCoverage)other;
        boolean coveredCountIsEqual = getCoveredCount() == otherCoverage.getCoveredCount();
        boolean totalCountIsEqual = getTotalCount() == otherCoverage.getTotalCount();
        return coveredCountIsEqual && totalCountIsEqual;
    }

    @Override
    public String toString() {
        return String.format( "Coverage (coveredCount=%d totalCount=%d)", new Integer( getCoveredCount() ), new Integer( getTotalCount() ) );
    }

    public static TestCoverage from( ICounter counter ) {
        int totalCount = (int)counter.getTotalCount();
        int coveredCount = (int)counter.getCoveredCount();
        return new TestCoverage( coveredCount, totalCount );
    }

}
