package org.projectusus.ui.internal;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.FileHotspot;

public class FileDisplayHotspot extends DisplayHotspot<FileHotspot> {

    public FileDisplayHotspot( FileHotspot historyHotspot, FileHotspot currentHotspot ) {
        super( historyHotspot, currentHotspot );
    }

    @Override
    public IFile getFile() {
        return getCurrentOrOldHotspot().getFile();
    }
}
