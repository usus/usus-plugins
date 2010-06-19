// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.IHotspot;

public class Hotspot implements IHotspot {

    private IFile file;
    private final String name;
    private final int metricsValue;
    private final int sourcePosition;
    private final int lineNumber;

    public Hotspot( String name, int metricsValue, int sourcePosition, int lineNumber ) {
        this.name = name;
        this.metricsValue = metricsValue;
        this.sourcePosition = sourcePosition;
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public IFile getFile() {
        return file;
    }

    public int getMetricsValue() {
        return metricsValue;
    }

    public int getSourcePosition() {
        return sourcePosition;
    }

    public void setFile( IFile file ) {
        this.file = file;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
