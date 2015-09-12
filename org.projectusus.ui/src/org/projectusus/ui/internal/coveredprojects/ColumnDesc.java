// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.coveredprojects;

enum ColumnDesc {

    PROJECT( "Project", 87, true, true ); //$NON-NLS-1$

    private final String headLabel;
    private final int weight;
    private final boolean hasImage;
    private final boolean hasText;

    ColumnDesc( String headLabel, int weight ) {
        this( headLabel, weight, false, false );
    }

    ColumnDesc( String headLabel, int weight, boolean hasImage, boolean hasText ) {
        this.headLabel = headLabel;
        this.weight = weight;
        this.hasImage = hasImage;
        this.hasText = hasText;
    }

    int getWeight() {
        return weight;
    }

    String getHeadLabel() {
        return headLabel;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public boolean isHasText() {
        return hasText;
    }
}
