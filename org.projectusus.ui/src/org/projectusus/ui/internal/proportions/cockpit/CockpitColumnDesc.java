// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.colors.ISharedUsusImages.OBJ_INFO;
import static org.projectusus.ui.colors.UsusUIImages.getSharedImages;
import static org.projectusus.ui.viewer.ColumnAlignment.CENTER;
import static org.projectusus.ui.viewer.ColumnAlignment.RIGHT;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.eclipse.swt.graphics.Image;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.viewer.IColumnDesc;
import org.projectusus.ui.viewer.UsusTreeColumn;

enum CockpitColumnDesc implements IColumnDesc<AnalysisDisplayEntry> {

    @UsusTreeColumn( weight = 51 )
    Indicator( "Indicator", true ) { //$NON-NLS-1$
        public String getLabel( AnalysisDisplayEntry element ) {
            return element.getLabel();
        }
    },
    @UsusTreeColumn( align = RIGHT, weight = 14, numeric = true )
    Average( "Avg. Rating", false ) { //$NON-NLS-1$
        public String getLabel( AnalysisDisplayEntry element ) {
            return formatter.format( element.getAverage() );
        }
    },
    @UsusTreeColumn( align = RIGHT, weight = 11, numeric = true )
    Violations( "Hotspots", false ) { //$NON-NLS-1$
        public String getLabel( AnalysisDisplayEntry element ) {
            return String.valueOf( element.getViolations() );
        }
    },
    @UsusTreeColumn( align = RIGHT, weight = 17 )
    Total( "Total", false ) { //$NON-NLS-1$
        public String getLabel( AnalysisDisplayEntry element ) {
            return element.getBasis();
        }
    },
    @UsusTreeColumn( align = CENTER, weight = 8, sortable = false )
    Trend( "Trend", true ) { //$NON-NLS-1$
        public String getLabel( @SuppressWarnings( "unused" ) AnalysisDisplayEntry element ) {
            return ""; // using image //$NON-NLS-1$
        }

        @Override
        public Image getImage( AnalysisDisplayEntry element ) {
            return element.getTrendImage();
        }
    };

    private final boolean hasImage;
    private final static DecimalFormat formatter = new DecimalFormat( "0.0", new DecimalFormatSymbols( Locale.US ) ); //$NON-NLS-1$
    private final String header;

    CockpitColumnDesc( String header, boolean hasImage ) {
        this.header = header;
        this.hasImage = hasImage;
    }

    public String getHeader() {
        return header;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public Image getImage( @SuppressWarnings( "unused" ) AnalysisDisplayEntry element ) {
        return getSharedImages().getImage( OBJ_INFO );
    }
}
