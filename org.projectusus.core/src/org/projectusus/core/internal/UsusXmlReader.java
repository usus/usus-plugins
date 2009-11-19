// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import static java.lang.Integer.parseInt;
import static java.util.Locale.ENGLISH;
import static org.joda.time.format.DateTimeFormat.forPattern;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class UsusXmlReader<T> {

    protected static final int UNDEFINED = -1;
    private final Element rootElement;
    private final String rootElementName;

    public UsusXmlReader( Element rootElement, String rootElementName ) {
        this.rootElement = rootElement;
        this.rootElementName = rootElementName;
    }

    public void read( List<T> elements ) {
        NodeList list = rootElement.getElementsByTagName( rootElementName );
        for( int i = 0; i < list.getLength(); i++ ) {
            Node node = list.item( i );
            T element = readElement( node );
            if( element != null ) {
                elements.add( element );
            }
        }
    }

    protected abstract T readElement( Node node );

    protected Double loadDouble( Node child, String attributeName ) {
        String text = loadStringValue( child, attributeName );
        Double value = null;
        try {
            value = new Double( Double.parseDouble( text ) );
        } catch( NumberFormatException numfex ) {
            // nothing, then
        }
        return value;
    }

    protected String loadStringValue( Node node, String attributeName ) {
        String result = null;
        Node attNode = node.getAttributes().getNamedItem( attributeName );
        if( attNode != null ) {
            result = attNode.getNodeValue();
        }
        return result;
    }

    protected DateTime loadDateTime( Node node, String attributeName ) {
        String text = loadStringValue( node, attributeName );
        DateTime result = null;
        if( text != null ) {
            result = formatTime( text );
        }
        return result;
    }

    protected DateTime formatTime( String text ) {
        DateTimeFormatter formatter = forPattern( UsusXmlNames.DATE_TIME_PATTERN ).withLocale( ENGLISH );
        return formatter.parseDateTime( text );
    }

    protected int loadInteger( Node child, String attributeName ) {
        String text = loadStringValue( child, attributeName );
        int value = UNDEFINED;
        try {
            value = parseInt( text );
        } catch( NumberFormatException numfex ) {
            // nothing, then
        }
        return value;
    }

}
