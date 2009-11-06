// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate.checkpoints;

import static java.lang.Integer.parseInt;
import static java.util.Locale.ENGLISH;
import static org.joda.time.format.DateTimeFormat.forPattern;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ATT_CASES;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ATT_SQI;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ATT_TIME;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ATT_VIOLATIONS;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.DATE_TIME_PATTERN;
import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.ELEM_CHECKPOINT;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.modelupdate.ICheckpoint;
import org.projectusus.core.internal.proportions.sqi.IsisMetrics;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class CheckpointReader {

    private static final int UNDEFINED = -1;
    private final Element rootElement;

    CheckpointReader( Element rootElement ) {
        this.rootElement = rootElement;
    }

    void read( List<ICheckpoint> checkpoints ) {
        NodeList list = rootElement.getElementsByTagName( ELEM_CHECKPOINT );
        for( int i = 0; i < list.getLength(); i++ ) {
            Node node = list.item( i );
            ICheckpoint checkpoint = readCheckpoint( node );
            if( checkpoint != null ) {
                checkpoints.add( checkpoint );
            }
        }
    }

    private ICheckpoint readCheckpoint( Node node ) {
        Checkpoint result = null;
        DateTime time = readDateTime( readAttributeValue( node, ATT_TIME ) );
        List<CodeProportion> entries = new ArrayList<CodeProportion>();
        NodeList children = node.getChildNodes();
        for( int i = 0; i < children.getLength(); i++ ) {
            Node child = children.item( i );
            if( child.getNodeType() == Node.ELEMENT_NODE ) {
                readEntry( child, entries );
            }
        }
        if( time != null && !entries.isEmpty() ) {
            result = new Checkpoint( entries, time );
        }
        return result;
    }

    private void readEntry( Node child, List<CodeProportion> entries ) {
        int cases = loadInteger( child, ATT_CASES );
        int violations = loadInteger( child, ATT_VIOLATIONS );
        Double sqi = loadDouble( child, ATT_SQI );
        IsisMetrics metric = loadMetric( child, XmlNames.ATT_METRIC );
        if( cases != UNDEFINED && violations != UNDEFINED && sqi != null && metric != null ) {
            ArrayList<IHotspot> hotspots = new ArrayList<IHotspot>();
            double sqiValue = sqi.doubleValue();
            entries.add( new CodeProportion( metric, violations, cases, sqiValue, hotspots ) );
        }
    }

    private IsisMetrics loadMetric( Node child, String attributeName ) {
        String text = readAttributeValue( child, attributeName );
        return IsisMetrics.valueOf( text );
    }

    private Double loadDouble( Node child, String attributeName ) {
        String text = readAttributeValue( child, attributeName );
        Double value = null;
        try {
            value = new Double( Double.parseDouble( text ) );
        } catch( NumberFormatException numfex ) {
            // nothing, then
        }
        return value;
    }

    private int loadInteger( Node child, String attributeName ) {
        String text = readAttributeValue( child, attributeName );
        int value = UNDEFINED;
        try {
            value = parseInt( text );
        } catch( NumberFormatException numfex ) {
            // nothing, then
        }
        return value;
    }

    private DateTime readDateTime( String text ) {
        DateTime result = null;
        if( text != null ) {
            result = formatTime( text );
        }
        return result;
    }

    private DateTime formatTime( String text ) {
        DateTimeFormatter formatter = forPattern( DATE_TIME_PATTERN ).withLocale( ENGLISH );
        return formatter.parseDateTime( text );
    }

    private String readAttributeValue( Node node, String attributeName ) {
        String result = null;
        Node attNode = node.getAttributes().getNamedItem( attributeName );
        if( attNode != null ) {
            result = attNode.getNodeValue();
        }
        return result;
    }
}
