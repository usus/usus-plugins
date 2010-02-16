// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.history;

import java.util.ArrayList;
import java.util.List;

import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.modelupdate.ICheckpoint;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;

class Checkpoints2GraphicsConverter {

    private static final int MAX_DISPLAYED_ENTRIES = 16;
    private final List<ICheckpoint> checkpoints;

    Checkpoints2GraphicsConverter( List<ICheckpoint> checkpoints ) {
        if( checkpoints.size() > MAX_DISPLAYED_ENTRIES ) {
            this.checkpoints = cutToDisplaySize( checkpoints );
        } else {
            this.checkpoints = checkpoints;
        }
    }

    double[] get( CodeProportionKind metric ) {
        List<Double> values = new ArrayList<Double>();
        for( ICheckpoint checkpoint : checkpoints ) {
            values.add( computeValue( metric, checkpoint ) );
        }
        return convert( values );
    }

    private Double computeValue( CodeProportionKind metric, ICheckpoint checkpoint ) {
        CodeProportion codeProportion = find( metric, checkpoint.getEntries() );
        return codeProportion == null ? new Double( 0.0 ) : codeProportion.getSQIValue();
    }

    private double[] convert( List<Double> values ) {
        double[] result = new double[values.size()];
        for( int i = 0; i < values.size(); i++ ) {
            result[i] = values.get( i ).doubleValue();
        }
        return result;
    }

    private CodeProportion find( CodeProportionKind metric, List<CodeProportion> entries ) {
        CodeProportion result = null;
        for( CodeProportion codeProportion : entries ) {
            if( codeProportion.getMetric() == metric ) {
                result = codeProportion;
            }
        }
        return result;
    }

    private List<ICheckpoint> cutToDisplaySize( List<ICheckpoint> checkpoints ) {
        int fromIndex = checkpoints.size() - (MAX_DISPLAYED_ENTRIES + 1);
        int toIndex = checkpoints.size() - 1;
        return checkpoints.subList( fromIndex, toIndex );
    }
}
