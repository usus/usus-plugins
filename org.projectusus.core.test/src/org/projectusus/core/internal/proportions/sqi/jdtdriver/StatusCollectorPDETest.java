// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi.jdtdriver;

import static org.eclipse.core.runtime.Status.OK_STATUS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.junit.Test;

public class StatusCollectorPDETest {

    @Test
    public void finishDoesNothingWithoutErrorsCollected() throws CoreException {
        new StatusCollector().finish(); // no exception
    }

    @Test( expected = CoreException.class )
    public void finishThrowsCollectedException() throws CoreException {
        StatusCollector statusCollector = new StatusCollector();
        statusCollector.add( new Exception() );
        statusCollector.finish();
    }

    @Test( expected = CoreException.class )
    public void finishThrowsCollectedCoreException() throws CoreException {
        StatusCollector statusCollector = new StatusCollector();
        statusCollector.add( new CoreException( OK_STATUS ) );
        statusCollector.finish();
    }

    @Test
    public void multipleExceptionsAppearInMultiStatus() {
        StatusCollector statusCollector = new StatusCollector();
        statusCollector.add( new Exception() );
        statusCollector.add( new Exception() );
        try {
            statusCollector.finish();
        } catch( CoreException cex ) {
            IStatus status = cex.getStatus();
            assertTrue( status.isMultiStatus() );
            assertEquals( 2, ((MultiStatus)status).getChildren().length );
        }
    }
}
