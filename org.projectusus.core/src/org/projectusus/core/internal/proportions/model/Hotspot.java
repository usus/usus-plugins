// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import org.eclipse.core.resources.IFile;

public class Hotspot implements IHotspot {

    private IFile file;
    private final int hotness;
    private final int sourcePosition;

    public Hotspot( int hotness, int sourcePosition ) {
        this.hotness = hotness;
        this.sourcePosition = sourcePosition;
    }

    public IFile getFile() {
        return file;
    }

    public int getHotness() {
        return hotness;
    }

    public int getSourcePosition() {
        return sourcePosition;
    }

    public void setFile( IFile file ) {
        this.file = file;
    }
}
