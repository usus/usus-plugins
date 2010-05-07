// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage;

import static java.text.NumberFormat.getNumberInstance;
import static java.util.Locale.ENGLISH;
import static org.eclipse.osgi.util.NLS.bind;
import static org.projectusus.core.internal.util.CoreTexts.testCoverage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.mountainminds.eclemma.core.analysis.ICounter;

public class TestCoverage {

    private int totalCount;
    private int coveredCount;

    public TestCoverage( int coveredCount, int totalCount ) {
        super();
        this.coveredCount = coveredCount;
        this.totalCount = totalCount;
    }

    public TestCoverage( ICounter counter ) {
        this( (int)counter.getCoveredCount(), (int)counter.getTotalCount() );
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCoveredCount() {
        return coveredCount;
    }

    private BigDecimal getCoverageInPercent() {
        if( getTotalCount() == 0 ) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal( 1000 * getCoveredCount() ).divide( new BigDecimal( getTotalCount() ), RoundingMode.HALF_UP ).divide( BigDecimal.TEN );
    }

    public String getCoverageInPercentDisplayString() {
        NumberFormat numberInstance = getNumberInstance( ENGLISH );
        numberInstance.setMaximumIntegerDigits( 3 );
        numberInstance.setMaximumFractionDigits( 2 );
        numberInstance.setMinimumFractionDigits( 1 );
        return numberInstance.format( getCoverageInPercent() ) + " %"; //$NON-NLS-1$
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
        return new EqualsBuilder(). //
                append( coveredCount, otherCoverage.coveredCount ).//
                append( totalCount, otherCoverage.totalCount ).//
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( coveredCount ).append( totalCount ).hashCode();
    }

    @Override
    public String toString() {
        return bind( testCoverage, new Integer( getCoveredCount() ), new Integer( getTotalCount() ) );
    }
}
