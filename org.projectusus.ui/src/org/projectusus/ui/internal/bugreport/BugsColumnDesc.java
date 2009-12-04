// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugreport;

import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.ui.internal.util.UITexts;
import org.projectusus.ui.internal.viewer.IColumnDesc;

enum BugsColumnDesc implements IColumnDesc<Bug> {

    COVERED( UITexts.BugsColumnDesc_title, 15, false, true ) {
        public String getLabel( Bug bug ) {
            return bug.getTitle();
        }
    },
    PACKAGE( UITexts.BugsColumnDesc_package, 40, false, true ) {
        public String getLabel( Bug bug ) {
            return bug.getLocation().getPackageName();
        }
    },
    CLASS_NAME( UITexts.BugsColumnDesc_classname, 20, false, true ) {
        public String getLabel( Bug bug ) {
            return bug.getLocation().getClassName();
        }
    },
    METHOD_NAME( UITexts.BugsColumnDesc_methodname, 20, false, true ) {
        public String getLabel( Bug bug ) {
            return bug.getLocation().getMethodName();
        }
    },
    METHOD_LENGTH( UITexts.BugsColumnDesc_methodlength, 20, false, true ) {
        public String getLabel( Bug bug ) {
            return String.valueOf( bug.getBugMetrics().getMethodLength() );
        }
    },
    CYCLOMATIC_COMPLEXITY( UITexts.BugsColumnDesc_cyclomatic_complexity, 20, false, true ) {
        public String getLabel( Bug bug ) {
            return String.valueOf( bug.getBugMetrics().getCyclomaticComplexity() );
        }
    },
    NUMBER_OF_METHOS_IN_CLASS( UITexts.BugsColumnDesc_number_of_methods_in_class, 20, false, true ) {
        public String getLabel( Bug bug ) {
            return String.valueOf( bug.getBugMetrics().getNumberOfMethods() );
        }
    };

    private final String headLabel;
    private final int weight;
    private final boolean hasImage;
    private final boolean hasText;

    BugsColumnDesc( String headLabel, int weight ) {
        this( headLabel, weight, false, false );
    }

    BugsColumnDesc( String headLabel, int weight, boolean hasImage, boolean hasText ) {
        this.headLabel = headLabel;
        this.weight = weight;
        this.hasImage = hasImage;
        this.hasText = hasText;
    }

    public int getWeight() {
        return weight;
    }

    public String getHeadLabel() {
        return headLabel;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public boolean isHasText() {
        return hasText;
    }
}
