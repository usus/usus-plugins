// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import org.eclipse.core.resources.IFile;

public class MetricCWHotspot implements IMetricCWHotspot {

    private IFile file;
    private final int count;

    public MetricCWHotspot( IFile file, int count ) {
        this.file = file;
        this.count = count;
    }

    public IFile getFile() {
        return file;
    }

    public void setFile( IFile file ) {
        this.file = file;
    }

    public int getHotness() {
        return count;
    }

    public int getSourcePosition() {
        return 0;
    }

    public String getFileName() {
        return file.getName();
    }

    public int getWarningCount() {
        return count;
    }

    @Override
    public String toString() {
        return file.getName() + " (CW = " + getWarningCount() + ")"; //$NON-NLS-1$//$NON-NLS-2$ 
    }
}
