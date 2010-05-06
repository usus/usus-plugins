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

    TA( isisMetrics_ta, CodeProportionUnit.LINE ), //
    PC( isisMetrics_pc, CodeProportionUnit.PACKAGE ), //
    CC( isisMetrics_cc, CodeProportionUnit.METHOD ) {
        @Override
        public boolean isViolatedBy( MethodRawData rawData ) {
            return getValueFor( rawData ) > MetricsLimits.CC_LIMIT;
        }

        @Override
        public int getValueFor( MethodRawData rawData ) {
            return rawData.getCCValue();
        }
    },
    ACD( isisMetrics_acd, CodeProportionUnit.CLASS ) {
        @Override
        public boolean isViolatedBy( ClassRawData rawData ) {
            int classCount = UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS );
            double limit = calculateCcdLimit( classCount );
            return rawData.getCCDResult() > limit;
        }
    }, //
    KG( isisMetrics_kg, CodeProportionUnit.CLASS ) {
        @Override
        public boolean isViolatedBy( ClassRawData rawData ) {
            return rawData.getNumberOfMethods() > MetricsLimits.KG_LIMIT;
        }
    }, //
    ML( isisMetrics_ml, CodeProportionUnit.METHOD ) {
        @Override
        public boolean isViolatedBy( MethodRawData rawData ) {
            return getValueFor( rawData ) > MetricsLimits.ML_LIMIT;
        }

        @Override
        public int getValueFor( MethodRawData rawData ) {
            return rawData.getMLValue();
        }
    }, //
    CW( isisMetrics_cw, CodeProportionUnit.ANYFILE ) {
        @Override
        public boolean operatesOnNonJavaFiles() {
            return true;
        }

        @Override
        public boolean isViolatedBy( FileRawData fileRawData ) {
            return fileRawData.getViolationCount( CW ) > 0;
        }

        @Override
        public boolean isViolatedBy( MiscFileRawData miscFileRawData ) {
            return miscFileRawData.getViolationCount( CW ) > 0;
        }
    };

    private final String label;
    private final CodeProportionUnit unit;

    private CodeProportionKind( String label, CodeProportionUnit unit ) {
        this.label = label;
        this.unit = unit;
    }

    public String getLabel() {
        return label;
    }

    public final CodeProportionUnit getUnit() {
        return unit;
    }

    public boolean isViolatedBy( @SuppressWarnings( "unused" ) MethodRawData rawData ) {
        return false;
    }

    public boolean isViolatedBy( @SuppressWarnings( "unused" ) ClassRawData rawData ) {
        return false;
    }

    public boolean isViolatedBy( @SuppressWarnings( "unused" ) MiscFileRawData miscFileRawData ) {
        return false;
    }

    public boolean isViolatedBy( @SuppressWarnings( "unused" ) FileRawData fileRawData ) {
        return false;
    }

    public final boolean isMethodKind() {
        return getUnit() == CodeProportionUnit.METHOD;
    }

    public int getValueFor( @SuppressWarnings( "unused" ) MethodRawData rawData ) {
        return 0;
    }

    public boolean operatesOnNonJavaFiles() {
        return false;
    }

    public int calculateCcdLimit( int classCount ) {
        double log_5_classCount = Math.log( classCount ) / Math.log( 5 );
        double factor = 1.5 / Math.pow( 2, log_5_classCount );
        double limit = factor * classCount;
        return (int)limit;
    }
}
