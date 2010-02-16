// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import static java.lang.Math.pow;
import static java.math.BigDecimal.ROUND_DOWN;

import java.math.BigDecimal;

import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;

/**
 * calculates the software quality index (SQI).
 * 
 * @author leif
 */
class SQIComputer {

    private final double sqi;

    SQIComputer( CodeStatistic basis, int violations, CodeProportionKind kind ) {
        this.sqi = basis.isEmpty() ? 0.0 : doCompute( basis, violations, kind.getCalibration() );
    }

    double compute() {
        return sqi;
    }

    // internal

    private double doCompute( CodeStatistic basis, int violations, double calibration ) {
        BigDecimal exponent = computeExponent( basis, violations, calibration );
        return doPowerOp( exponent.doubleValue() );
    }

    private BigDecimal computeExponent( CodeStatistic basis, int violations, double calibration ) {
        BigDecimal beforeCalibration = new BigDecimal( violations ).divide( basis.asBigDecimal(), 5, ROUND_DOWN );
        return beforeCalibration.multiply( new BigDecimal( calibration ) );
    }

    private double doPowerOp( double exponent ) {
        BigDecimal twoThirds = new BigDecimal( 2 ).divide( new BigDecimal( 3 ), 5, ROUND_DOWN );
        return pow( twoThirds.doubleValue(), exponent ) * 100;
    }
}
