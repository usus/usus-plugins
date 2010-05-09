// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import static java.lang.Math.max;
import static java.lang.Math.min;

class CodeProportionsRatio {

    private static final double upperLimit = 100.0;

    public static double computeInverse( int violations, CodeStatistic basis ) {
        return basis.getValue() == 0 ? 0 : upperLimit - computeRatio( violations, basis );
    }

    private static double computeRatio( int violations, CodeStatistic basis ) {
        return max( 0, min( upperLimit * violations / basis.getValue(), upperLimit ) );
    }
}
