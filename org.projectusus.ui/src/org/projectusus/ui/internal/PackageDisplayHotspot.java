package org.projectusus.ui.internal;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.PackageHotspot;

public class PackageDisplayHotspot extends DisplayHotspot<PackageHotspot> {

    public PackageDisplayHotspot( PackageHotspot historyHotspot, PackageHotspot currentHotspot ) {
        super( historyHotspot, currentHotspot );
    }

    @Override
    public IFile getFile() {
        return null;
    }
}
