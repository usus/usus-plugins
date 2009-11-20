// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_acd;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_cc;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_cw;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_kg;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_ml;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_pc;
import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_ta;

public enum IsisMetrics {

    TA( isisMetrics_ta ) {
        @Override
        public CodeProportionUnit getUnit() {
            return CodeProportionUnit.METHOD;
        }
    }, //
    PC( isisMetrics_pc ) {
        @Override
        public CodeProportionUnit getUnit() {
            return CodeProportionUnit.PACKAGE;
        }
    }, //
    CC( isisMetrics_cc ) {
        @Override
        public boolean isViolatedBy( MethodRawData methodResults ) {
            return methodResults.getCCValue() > MetricsLimits.CC_LIMIT;
        }

        @Override
        public boolean isMethodTest() {
            return true;
        }

        @Override
        public double getCalibration() {
            return 100.0;
        }

        @Override
        public int getValueFor( MethodRawData methodResults ) {
            return methodResults.getCCValue();
        }

        @Override
        public CodeProportionUnit getUnit() {
            return CodeProportionUnit.METHOD;
        }
    },
    ACD( isisMetrics_acd ) {
        @Override
        public boolean isViolatedBy( ClassRawData classResult ) {
            int classCount = WorkspaceRawData.getInstance().getViolationBasis( IsisMetrics.KG );
            double log_5_classCount = Math.log( classCount ) / Math.log( 5 );
            double factor = 1.5 / Math.pow( 2, log_5_classCount );
            double limit = factor * classCount;
            return classResult.getCCDResult() > limit;
        }

        @Override
        public CodeProportionUnit getUnit() {
            return CodeProportionUnit.CLASS;
        }
    }, //
    KG( isisMetrics_kg ) {
        @Override
        public boolean isViolatedBy( ClassRawData classResult ) {
            return classResult.getNumberOfMethods() > MetricsLimits.KG_LIMIT;
        }

        @Override
        public double getCalibration() {
            return 25.0;
        }

        @Override
        public CodeProportionUnit getUnit() {
            return CodeProportionUnit.CLASS;
        }
    }, //
    ML( isisMetrics_ml ) {
        @Override
        public boolean isViolatedBy( MethodRawData methodResults ) {
            return methodResults.getMLValue() > MetricsLimits.ML_LIMIT;
        }

        @Override
        public boolean isMethodTest() {
            return true;
        }

        @Override
        public double getCalibration() {
            return 25.0;
        }

        @Override
        public int getValueFor( MethodRawData methodResults ) {
            return methodResults.getMLValue();
        }

        @Override
        public CodeProportionUnit getUnit() {
            return CodeProportionUnit.METHOD;
        }
    }, //
    CW( isisMetrics_cw ) {
        @Override
        public CodeProportionUnit getUnit() {
            return CodeProportionUnit.FILE;
        }
    };

    private final String label;

    private IsisMetrics( String label ) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public CodeProportionUnit getUnit() {
        throw new UnsupportedOperationException( "No unit defined for this metric." ); //$NON-NLS-1$
    }

    public boolean isViolatedBy( MethodRawData methodResult ) {
        return false;
    }

    public boolean isViolatedBy( ClassRawData methodResult ) {
        return false;
    }

    public boolean isMethodTest() {
        return false;
    }

    public double getCalibration() {
        throw new UnsupportedOperationException( "No computation yet for this metric." ); //$NON-NLS-1$
    }

    public int getValueFor( MethodRawData methodResults ) {
        return 0;
    }
}
