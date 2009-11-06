// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import static java.text.MessageFormat.format;
import static org.projectusus.core.internal.util.CoreTexts.yellowCountResult_msg;

class YellowCountResult implements IYellowCountResult {
    private static final int COUNT_POSITION = 17;
    private final int projectCount;
    private final int yellowCount;
    private final int yellowProjectCount;
    private final int fileCount;

    YellowCountResult( int projectCount, int fileCount, int yellowCount, int yellowProjectCount ) {
        this.projectCount = projectCount;
        this.fileCount = fileCount;
        this.yellowCount = yellowCount;
        this.yellowProjectCount = yellowProjectCount;
    }

    public int getYellowCount() {
        return yellowCount;
    }

    public int getFileCount() {
        return fileCount;
    }

    public int getYellowProjectCount() {
        return yellowProjectCount;
    }

    public int getProjectCount() {
        return projectCount;
    }

    public int getFormattedCountLength() {
        return String.valueOf( yellowCount ).length();
    }

    public int getFormattedCountPosition() {
        return COUNT_POSITION;
    }

    @Override
    public String toString() {
        Integer iYellowCount = new Integer( yellowCount );
        Integer iYellowProjectCount = new Integer( yellowProjectCount );
        Integer iProjectCount = new Integer( projectCount );
        String wiseCrack = new WiseCrack( yellowCount ).toString();
        return format( yellowCountResult_msg, iYellowCount, iYellowProjectCount, iProjectCount ) + " " + wiseCrack; //$NON-NLS-1$
    }
}
