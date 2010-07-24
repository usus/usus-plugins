package org.projectusus.ui.dependencygraph.common;

import java.util.Comparator;

public class GraphLayoutsComparator implements Comparator<GraphLayouts> {

    public int compare( GraphLayouts first, GraphLayouts second ) {
        return first.title().compareTo( second.title() );
    }
}
