// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugprison;

import static org.projectusus.ui.internal.viewer.ColumnAlignment.RIGHT;

import org.projectusus.bugprison.core.Bug;
import org.projectusus.ui.internal.viewer.IColumnDesc;
import org.projectusus.ui.internal.viewer.UsusTreeColumn;

enum BugsColumnDesc implements IColumnDesc<Bug> {

    @UsusTreeColumn( header = "Title", weight = 15 )
    COVERED {
        public String getLabel( Bug bug ) {
            return bug.getTitle();
        }
    },
    @UsusTreeColumn( header = "Package", weight = 40 )
    PACKAGE {
        public String getLabel( Bug bug ) {
            return bug.getLocation().getPackageName();
        }
    },
    @UsusTreeColumn( header = "Classname", weight = 20 )
    CLASS_NAME {
        public String getLabel( Bug bug ) {
            return bug.getLocation().getClassName();
        }
    },
    @UsusTreeColumn( header = "Methodname", weight = 20 )
    METHOD_NAME {
        public String getLabel( Bug bug ) {
            return bug.getLocation().getMethodName();
        }
    },
    @UsusTreeColumn( header = "Methodlength", weight = 20 )
    METHOD_LENGTH {
        public String getLabel( Bug bug ) {
            return String.valueOf( bug.getBugMetrics().getMethodLength() );
        }
    },
    @UsusTreeColumn( header = "Cyclomatic complexity", align = RIGHT, weight = 20 )
    CYCLOMATIC_COMPLEXITY {
        public String getLabel( Bug bug ) {
            return String.valueOf( bug.getBugMetrics().getCyclomaticComplexity() );
        }
    },
    @UsusTreeColumn( header = "# methods in class", align = RIGHT, weight = 20 )
    NUMBER_OF_METHODS_IN_CLASS {
        public String getLabel( Bug bug ) {
            return String.valueOf( bug.getBugMetrics().getNumberOfMethods() );
        }
    };

    public boolean hasImage() {
        return false;
    }

    public boolean isHasText() {
        return true;
    }
}
