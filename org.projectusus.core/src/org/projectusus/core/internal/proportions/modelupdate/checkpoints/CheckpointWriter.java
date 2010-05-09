// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate.checkpoints;

import static java.lang.String.valueOf;
import static java.util.Locale.ENGLISH;
import static org.joda.time.format.DateTimeFormat.forPattern;
import static org.projectusus.core.internal.UsusXmlNames.DATE_TIME_PATTERN;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ATT_CASES;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ATT_METRIC;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ATT_LEVEL;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ATT_TIME;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ATT_VIOLATIONS;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ELEM_CHECKPOINT;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ELEM_ENTRY;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ELEM_ROOT;

import java.util.List;

import org.joda.time.format.DateTimeFormatter;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.ICheckpoint;
import org.projectusus.core.internal.UsusXmlWriter;

class CheckpointWriter extends UsusXmlWriter {

    private final List<ICheckpoint> checkpoints;

    CheckpointWriter( List<ICheckpoint> checkpoints ) {
        this.checkpoints = checkpoints;
    }

    String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append( PREAMBLE );
        sb.append( openTag( ELEM_ROOT ) );
        for( ICheckpoint checkpoint : checkpoints ) {
            toXml( checkpoint, sb );
        }
        sb.append( closeTag( ELEM_ROOT ) );
        return sb.toString();
    }

    private void toXml( ICheckpoint checkpoint, StringBuilder sb ) {
        sb.append( INDENT + tagStart( ELEM_CHECKPOINT ) );
        sb.append( att( ATT_TIME, convertTime( checkpoint ) ) );
        sb.append( tagEndOpen() );
        for( CodeProportion codeProportion : checkpoint.getEntries() ) {
            toXml( codeProportion, sb );
        }
        sb.append( INDENT + closeTag( ELEM_CHECKPOINT ) );
    }

    private String convertTime( ICheckpoint checkpoint ) {
        DateTimeFormatter formatter = forPattern( DATE_TIME_PATTERN ).withLocale( ENGLISH );
        return checkpoint.getTime().toString( formatter );
    }

    private void toXml( CodeProportion codeProportion, StringBuilder sb ) {
        sb.append( INDENT + INDENT );
        sb.append( tagStart( ELEM_ENTRY ) );
        sb.append( att( ATT_METRIC, codeProportion.getMetric().toString() ) );
        sb.append( att( ATT_CASES, valueOf( codeProportion.getBasis().getValue() ) ) );
        sb.append( att( ATT_VIOLATIONS, valueOf( codeProportion.getViolations() ) ) );
        sb.append( att( ATT_LEVEL, codeProportion.getLevel().toString() ) );
        sb.append( tagEndClosed() );
    }

}
