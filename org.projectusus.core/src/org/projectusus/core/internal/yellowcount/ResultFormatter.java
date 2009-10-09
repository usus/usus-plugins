// Copyright (c) 2005-2006 by Leif Frenzel.
// All rights reserved.
package org.projectusus.core.internal.yellowcount;

public class ResultFormatter {

    public static final String FIRST_TEXT = "Yellow count is  ";

    public String dump( IYellowCountResult countResult ) {
        StringBuffer sb = new StringBuffer();
        sb.append( ResultFormatter.FIRST_TEXT );
        sb.append( String.valueOf( countResult.getYellowCount() ) );
        sb.append( "  (" );
        sb.append( countResult.getYellowProjectCount() );
        sb.append( " of " );
        sb.append( countResult.getProjectCount() );
        sb.append( " projects are yellow)." );
        wiseCrack( countResult.getYellowCount(), sb );
        return sb.toString();
    }

    private void wiseCrack( int count, StringBuffer sb ) {
        if( count == 42 ) {
            sb.append( " That's cool." );
        } else if( count > 0 ) {
            sb.append( " That's a shame." );
        }
    }

}
