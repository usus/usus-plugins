// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.projectusus.core.internal.proportions.CodeProportion;

/**
 * update the Usus model with results from a test run (i.e. primarily coverage data).
 * 
 * @author leif
 */
public class TestRunModelUpdate implements IUsusModelUpdate {

    private final Date time;
    private final CodeProportion entry;

    public TestRunModelUpdate( CodeProportion entry ) {
        this.entry = entry;
        this.time = new Date();
    }

    public List<CodeProportion> getEntries() {
        return entry == null ? new ArrayList<CodeProportion>() : asList( entry );
    }

    public Date getTime() {
        return time;
    }

    public Type getType() {
        return Type.TEST_RUN;
    }

    public boolean isSuccessful() {
        throw new UnsupportedOperationException( "Can't handle success status of test runs yet." ); //$NON-NLS-1$
    }
}
