// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static java.lang.Math.min;
import static java.math.BigDecimal.ROUND_DOWN;

import java.math.BigDecimal;

public class CodeProportionsRatio {

    private final double result;

    CodeProportionsRatio( int part, int total ) {
        result = total == 0 ? 0 : computeRatio( part, total );
    }

    public double compute() {
        return result;
    }

    public double computeReverseIndicator() {
        return 100 - result;
    }

    // internal
    // ////////

    private double computeRatio( int part, int total ) {
        BigDecimal ratio = new BigDecimal( part ).divide( new BigDecimal( total ), 5, ROUND_DOWN );
        return min( ratio.doubleValue() * 100, 100.0 );
    }
}
