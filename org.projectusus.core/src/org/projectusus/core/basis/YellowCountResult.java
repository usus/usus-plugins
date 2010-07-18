// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import static java.text.MessageFormat.format;

public class YellowCountResult {
    private static final int COUNT_POSITION = 16;
    private static String wiseCrack_cool = " That's cool."; //$NON-NLS-1$
    private static String wiseCrack_shame = " That's a shame."; //$NON-NLS-1$
    private static String yellowCountResult_msg = "Yellow count is {0} ({1} of {2} projects are yellow)."; //$NON-NLS-1$

    private final int projectCount;
    private final int yellowCount;
    private final int yellowProjectCount;

    public YellowCountResult( int projectCount, int yellowCount, int yellowProjectCount ) {
        this.projectCount = projectCount;
        this.yellowCount = yellowCount;
        this.yellowProjectCount = yellowProjectCount;
    }

    public int getYellowCount() {
        return yellowCount;
    }

    public int getYellowProjectCount() {
        return yellowProjectCount;
    }

    public int getProjectCount() {
        return projectCount;
    }

    public int getFormattedCountLength() {
        return format( "{0}", new Integer( yellowCount ) ).length(); //$NON-NLS-1$
    }

    public int getFormattedCountPosition() {
        return COUNT_POSITION;
    }

    @Override
    public String toString() {
        Integer iYellowCount = new Integer( yellowCount );
        Integer iYellowProjectCount = new Integer( yellowProjectCount );
        Integer iProjectCount = new Integer( projectCount );
        String wiseCrack = getWiseCrackString( yellowCount );
        return format( yellowCountResult_msg, iYellowCount, iYellowProjectCount, iProjectCount ) + wiseCrack;
    }

    private String getWiseCrackString( int count ) {
        String result = ""; //$NON-NLS-1$
        if( count == 42 ) {
            result = wiseCrack_cool;
        } else if( count > 0 ) {
            result = wiseCrack_shame;
        }
        return result;
    }

}
