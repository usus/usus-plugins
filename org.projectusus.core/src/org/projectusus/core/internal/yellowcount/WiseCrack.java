// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import static org.projectusus.core.internal.util.CoreTexts.wiseCrack_cool;
import static org.projectusus.core.internal.util.CoreTexts.wiseCrack_shame;

class WiseCrack {

    private final int count;

    WiseCrack( int count ) {
        this.count = count;
    }

    @Override
    public String toString() {
        String result = ""; //$NON-NLS-1$
        if( count == 42 ) {
            result = wiseCrack_cool;
        } else if( count > 0 ) {
            result = wiseCrack_shame;
        }
        return result;
    }
}
