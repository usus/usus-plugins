// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate.checkpoints;

import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.CHECKPOINTS_FILE;

import java.util.ArrayList;

import org.eclipse.core.runtime.IPath;
import org.projectusus.core.basis.ICheckpoint;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.XmlLoader;
import org.w3c.dom.Element;

public class LoadCheckpoints extends XmlLoader<ICheckpoint> {

    // internal
    // ////////

    @Override
    protected void read( ArrayList<ICheckpoint> result, Element rootElement ) {
        new CheckpointReader( rootElement ).read( result );
    }

    @Override
    protected String getFileName() {
        IPath stateLocation = getStateLocation().append( CHECKPOINTS_FILE );
        return stateLocation.toOSString();
    }

    private IPath getStateLocation() {
        return UsusCorePlugin.getDefault().getStateLocation();
    }

}
