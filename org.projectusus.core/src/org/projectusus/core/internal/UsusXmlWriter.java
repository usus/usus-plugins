// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import static java.lang.String.valueOf;
import static java.util.Locale.ENGLISH;
import static org.joda.time.format.DateTimeFormat.forPattern;
import static org.projectusus.core.internal.UsusXmlNames.DATE_TIME_PATTERN;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

public class UsusXmlWriter {

    protected static final String PREAMBLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"; //$NON-NLS-1$
    protected static final String INDENT = "  "; //$NON-NLS-1$

    protected String att( String attributeName, String value ) {
        return attributeName + "=\"" + value + "\" "; //$NON-NLS-1$//$NON-NLS-2$
    }

    protected String att( String attributeName, DateTime value ) {
        String formatedTime = convertTime( value );
        return att( attributeName, formatedTime );
    }

    protected String att( String attributeName, int value ) {
        return att( attributeName, valueOf( value ) );
    }

    protected String tagStart( String elementName ) {
        return "<" + elementName + " "; //$NON-NLS-1$//$NON-NLS-2$
    }

    protected String tagEndClosed() {
        return "/>\n"; //$NON-NLS-1$
    }

    protected String tagEndOpen() {
        return ">\n"; //$NON-NLS-1$
    }

    protected String openTag( String elementName ) {
        return "<" + elementName + ">\n"; //$NON-NLS-1$//$NON-NLS-2$
    }

    protected String closeTag( String elementName ) {
        return "</" + elementName + ">\n"; //$NON-NLS-1$//$NON-NLS-2$
    }

    private String convertTime( DateTime time ) {
        DateTimeFormatter formatter = forPattern( DATE_TIME_PATTERN ).withLocale( ENGLISH );
        return time.toString( formatter );
    }

}
