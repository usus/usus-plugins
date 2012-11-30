package org.projectusus.ui.internal;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.SinglePackageHotspot;

public class SinglePackageDisplayHotspot extends DisplayHotspot<SinglePackageHotspot> {

    public SinglePackageDisplayHotspot( SinglePackageHotspot historyHotspot, SinglePackageHotspot currentHotspot ) {
        super( historyHotspot, currentHotspot );
    }

    @Override
    public IFile getFile() {
        return null;
    }
}
