// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.proportions.modelcomputation.DeltaCodeProportionComputationTarget;
import org.projectusus.core.internal.proportions.modelcomputation.ICodeProportionComputationTarget;

class TestResourceChangeListener implements IResourceChangeListener {
    private DeltaCodeProportionComputationTarget target;
    private Exception exception;

    public void resourceChanged( IResourceChangeEvent event ) {
        IResourceDelta delta = event.getDelta();
        if( delta != null ) {
            try {
                target = new DeltaCodeProportionComputationTarget( delta );
            } catch( CoreException cex ) {
                exception = cex;
            }
        }
    }
    
    ICodeProportionComputationTarget getTarget() {
        return target;
    }
    
    void assertNoException() throws Exception {
        // otherwise it would be lost somewhere in the log, we want it to make the test red
        if( exception != null ) {
            throw exception;
        }
    }
}