// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.projectsettings;

class Setting<Code, Value> {

    private final Code code;
    private Value value;
    private String description;
    private String link;

    protected Setting( Code code ) {
        super();
        assert (code != null);
        this.code = code;
    }

    public boolean hasValue() {
        return this.value != null;
    }

    public boolean hasCode( Code otherCode ) {
        return code.equals( otherCode );
    }

    public Code getCode() {
        return code;
    }

    public Value getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public void setValue( Value value ) {
        this.value = value;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public void setLink( String link ) {
        this.link = link;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append( getCode() );
        buffer.append( "=" ); //$NON-NLS-1$
        buffer.append( value );
        return buffer.toString();
    }

}
