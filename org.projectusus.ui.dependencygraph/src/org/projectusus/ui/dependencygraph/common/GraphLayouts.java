package org.projectusus.ui.dependencygraph.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.VerticalLayoutAlgorithm;

public enum GraphLayouts {

    GRID( "Grid" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new GridLayoutAlgorithm( DEFAULT_STYLE );
        }
    },

    HORIZONTAL( "Horizontal" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new HorizontalLayoutAlgorithm( DEFAULT_STYLE );
        }
    },

    HORIZONTAL_TREE( "Horizontal Tree" ) {
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

    TREE( "Tree" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new TreeLayoutAlgorithm( DEFAULT_STYLE );
        }
    },

    VERTICAL( "Vertical" ) {
        @Override
        public LayoutAlgorithm createAlgorithm() {
            return new VerticalLayoutAlgorithm( DEFAULT_STYLE );
        }
    };

    private static final int DEFAULT_STYLE = LayoutStyles.NO_LAYOUT_NODE_RESIZING;
    private final String title;

    GraphLayouts( String title ) {
        this.title = title;
    }

    public abstract LayoutAlgorithm createAlgorithm();

    public static GraphLayouts getFirst() {
        return forIndex( 0 );
    }

    public static String[] asStrings() {
        GraphLayouts[] values = values();
        int length = values.length;
        List<String> result = new ArrayList<String>( length );
        for( GraphLayouts layout : values ) {
            result.add( layout.title );
        }
        return result.toArray( new String[length] );
    }

    public static GraphLayouts forIndex( int index ) {
        return values()[index];
    }
}
