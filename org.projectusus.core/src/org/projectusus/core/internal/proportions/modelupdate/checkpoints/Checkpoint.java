// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate.checkpoints;

import java.util.List;

import org.joda.time.DateTime;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.ICheckpoint;

public class Checkpoint implements ICheckpoint {

    private final DateTime time;
    private final List<CodeProportion> entries;

    public Checkpoint( List<CodeProportion> entries ) {
        this( entries, new DateTime() );
    }

    Checkpoint( List<CodeProportion> entries, DateTime time ) {
        this.time = time;
        this.entries = entries;
    }

    public DateTime getTime() {
        return time;
    }

    public List<CodeProportion> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "Checkpoint " + time + " (" + entries.size() + " entries)"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    }

    public CodeProportion findCodeProportion( CodeProportionKind kind ) {
        for( CodeProportion codeProportion : getEntries() ) {
            if( kind.equals( codeProportion.getMetric() ) ) {
                return codeProportion;
            }
        }
        throw new IllegalArgumentException( "Code proportion " + kind + "not found" ); //$NON-NLS-1$//$NON-NLS-2$
    }
}
