package org.projectusus.core.internal.proportions.rawdata;

import static java.lang.Math.pow;
import static java.math.BigDecimal.ROUND_DOWN;

import java.math.BigDecimal;

/**
 * calculates the software quality index (SQI).
 * 
 * @author leif
 */
class SQIComputer {

    private final double sqi;

    SQIComputer( int basis, int violations, double calibration ) {
        this.sqi = basis == 0 ? 0.0 : doCompute( basis, violations, calibration );
    }

    double compute() {
        return sqi;
    }

    // internal

    private double doCompute( int basis, int violations, double calibration ) {
        BigDecimal exponent = computeExponent( basis, violations, calibration );
        return doPowerOp( exponent.doubleValue() );
    }

    private BigDecimal computeExponent( int basis, int violations, double calibration ) {
        BigDecimal beforeCalibration = new BigDecimal( violations ).divide( new BigDecimal( basis ), 5, ROUND_DOWN );
        return beforeCalibration.multiply( new BigDecimal( calibration ) );
    }

    private double doPowerOp( double exponent ) {
        BigDecimal twoThirds = new BigDecimal( 2 ).divide( new BigDecimal( 3 ), 5, ROUND_DOWN );
        return pow( twoThirds.doubleValue(), exponent ) * 100;
    }
}
