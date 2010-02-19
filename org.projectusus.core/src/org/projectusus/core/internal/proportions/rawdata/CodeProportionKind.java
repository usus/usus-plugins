// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_acd;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_cc;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_cw;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_kg;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_ml;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_pc;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_ta;

import org.projectusus.core.internal.UsusCorePlugin;

public enum CodeProportionKind {

    TA( isisMetrics_ta, CodeProportionUnit.LINE, 1.0 ), //
    PC( isisMetrics_pc, CodeProportionUnit.PACKAGE, 1.0 ), //
    CC( isisMetrics_cc, CodeProportionUnit.METHOD, 100.0 ) {
        @Override
        public boolean isViolatedBy( MethodRawData rawData ) {
            return getValueFor( rawData ) > MetricsLimits.CC_LIMIT;
        }

        @Override
        public int getValueFor( MethodRawData rawData ) {
            return rawData.getCCValue();
        }
    },
    ACD( isisMetrics_acd, CodeProportionUnit.CLASS, 1.0 ) {
        @Override
        public boolean isViolatedBy( ClassRawData rawData ) {
            int classCount = UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS );
            double log_5_classCount = Math.log( classCount ) / Math.log( 5 );
            double factor = 1.5 / Math.pow( 2, log_5_classCount );
            double limit = factor * classCount;
            return rawData.getCCDResult() > limit;
        }
    }, //
    KG( isisMetrics_kg, CodeProportionUnit.CLASS, 25.0 ) {
        @Override
        public boolean isViolatedBy( ClassRawData rawData ) {
            return rawData.getNumberOfMethods() > MetricsLimits.KG_LIMIT;
        }
    }, //
    ML( isisMetrics_ml, CodeProportionUnit.METHOD, 25.0 ) {
        @Override
        public boolean isViolatedBy( MethodRawData rawData ) {
            return getValueFor( rawData ) > MetricsLimits.ML_LIMIT;
        }

        @Override
        public int getValueFor( MethodRawData rawData ) {
            return rawData.getMLValue();
        }
    }, //
    CW( isisMetrics_cw, CodeProportionUnit.FILE, 1.0 );

    private final String label;
    private final double calibration;
    private final CodeProportionUnit unit;

    private CodeProportionKind( String label, CodeProportionUnit unit, double calibration ) {
        this.label = label;
        this.unit = unit;
        this.calibration = calibration;
    }

    public String getLabel() {
        return label;
    }

    public final CodeProportionUnit getUnit() {
        return unit;
    }

    public boolean isViolatedBy( MethodRawData rawData ) {
        return false;
    }

    public boolean isViolatedBy( ClassRawData rawData ) {
        return false;
    }

    public final boolean isMethodKind() {
        return getUnit() == CodeProportionUnit.METHOD;
    }

    public final double getCalibration() {
        return calibration;
    }

    public int getValueFor( MethodRawData rawData ) {
        return 0;
    }
}
