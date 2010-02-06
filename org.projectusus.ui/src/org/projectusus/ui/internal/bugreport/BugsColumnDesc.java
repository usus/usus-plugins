// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugreport;

import static org.projectusus.ui.internal.viewer.ColumnAlignment.LEFT;
import static org.projectusus.ui.internal.viewer.ColumnAlignment.RIGHT;

import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.ui.internal.viewer.ColumnAlignment;
import org.projectusus.ui.internal.viewer.IColumnDesc;

enum BugsColumnDesc implements IColumnDesc<Bug> {

    COVERED( "Title", 15 ) {
        public String getLabel( Bug bug ) {
            return bug.getTitle();
        }
    },
    PACKAGE( "Package", 40 ) {
        public String getLabel( Bug bug ) {
            return bug.getLocation().getPackageName();
        }
    },
    CLASS_NAME( "Classname", 20 ) {
        public String getLabel( Bug bug ) {
            return bug.getLocation().getClassName();
        }
    },
    METHOD_NAME( "Methodname", 20 ) {
        public String getLabel( Bug bug ) {
            return bug.getLocation().getMethodName();
        }
    },
    METHOD_LENGTH( "Methodlength", 20 ) {
        public String getLabel( Bug bug ) {
            return String.valueOf( bug.getBugMetrics().getMethodLength() );
        }
    },
    CYCLOMATIC_COMPLEXITY( "Cyclomatic complexity", RIGHT, 20 ) {
        public String getLabel( Bug bug ) {
            return String.valueOf( bug.getBugMetrics().getCyclomaticComplexity() );
        }
    },
    NUMBER_OF_METHODS_IN_CLASS( "# methods in class", RIGHT, 20 ) {
        public String getLabel( Bug bug ) {
            return String.valueOf( bug.getBugMetrics().getNumberOfMethods() );
        }
    };

    private final String headLabel;
    private final int weight;
    private final ColumnAlignment align;

    private BugsColumnDesc( String headLabel, ColumnAlignment align, int weight ) {
        this.headLabel = headLabel;
        this.align = align;
        this.weight = weight;
    }

    private BugsColumnDesc( String headLabel, int weight ) {
        this( headLabel, LEFT, weight );
    }

    public int getWeight() {
        return weight;
    }

    public String getHeadLabel() {
        return headLabel;
    }

    public boolean hasImage() {
        return false;
    }

    public ColumnAlignment getColumnAlignment() {
        return align;
    }

    public boolean isHasText() {
        return true;
    }
}
