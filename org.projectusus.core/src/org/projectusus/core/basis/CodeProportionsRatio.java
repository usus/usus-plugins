// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.math.BigDecimal.ROUND_DOWN;

import java.math.BigDecimal;

public class CodeProportionsRatio {

    private static final double upperLimit = 100.0;
    private final int part;
    private final int total;

    public CodeProportionsRatio( int part, int total ) {
        this.part = part;
        this.total = total;
    }

    public double compute() {
        return total == 0 ? 0 : computeRatio( part, total );
    }

    public double computeInverseIndicator() {
        return total == 0 ? 0 : upperLimit - computeRatio( part, total );
    }

    // internal
    // ////////

    private double computeRatio( int part, int total ) {
        BigDecimal ratio = new BigDecimal( part ).divide( new BigDecimal( total ), 5, ROUND_DOWN );
        return max( 0, min( ratio.doubleValue() * upperLimit, upperLimit ) );
    }
}
