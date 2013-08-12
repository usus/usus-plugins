package org.projectusus.ui.dependencygraph.common;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.VerticalLayoutAlgorithm;

public enum GraphLayout {

    GRID( "Grid" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new GridLayoutAlgorithm( DEFAULT_STYLE );
        }
    },

    HORIZONTAL( "Linear (horizontal)" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new HorizontalLayoutAlgorithm( DEFAULT_STYLE );
        }
    },

    HORIZONTAL_TREE( "Tree (horizontal)" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new HorizontalTreeLayoutAlgorithm( DEFAULT_STYLE );
        }
    },

    RADIAL( "Radial" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new RadialLayoutAlgorithm( DEFAULT_STYLE );
        }
    },

    SPRING( "Spring" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new SpringLayoutAlgorithm( DEFAULT_STYLE );
        }
    },

    TREE( "Tree (vertical)" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new TreeLayoutAlgorithm( DEFAULT_STYLE );
        }
    },

    VERTICAL( "Linear (vertical)" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new VerticalLayoutAlgorithm( DEFAULT_STYLE );
        }
    };

    private static final int DEFAULT_STYLE = LayoutStyles.NO_LAYOUT_NODE_RESIZING;

    private final String title;

    GraphLayout( String title ) {
        this.title = title;
    }

    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title();
    }

    public abstract LayoutAlgorithm createAlgorithm();

    public static GraphLayout getDefault() {
        return SPRING;
    }
}
