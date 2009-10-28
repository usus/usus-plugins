// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import org.eclipse.core.resources.IFile;

class Hotspot implements IHotspot {

    private final int line;
    private final IFile file;

    public Hotspot( IFile file, Integer line ) {
        this.file = file;
        this.line = line.intValue();
    }

    public String getLabel() {
        return file.getName();
    }

    public int getLine() {
        return line;
    }

    public IFile getWorkspaceFile() {
        return file;
    }

    @Override
    public String toString() {
        return getLabel() + " (line " + getLine() + ")";
    }
}
