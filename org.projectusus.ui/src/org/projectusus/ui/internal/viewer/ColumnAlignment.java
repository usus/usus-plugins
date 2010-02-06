package org.projectusus.ui.internal.viewer;

import org.eclipse.swt.SWT;

public enum ColumnAlignment {

    LEFT( SWT.LEFT ), RIGHT( SWT.RIGHT ), CENTER( SWT.CENTER );

    private final int swtStyle;

    private ColumnAlignment( int swtStyle ) {
        this.swtStyle = swtStyle;
    }

    int toSwtStyle() {
        return swtStyle;
    }
}
