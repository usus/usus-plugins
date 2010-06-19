// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import java.util.Comparator;



public class ByHotnessComparator implements Comparator<IHotspot> {

    public int compare( IHotspot left, IHotspot right ) {
        return new Integer( right.getMetricsValue() ).compareTo( new Integer( left.getMetricsValue() ) );
    }
}
