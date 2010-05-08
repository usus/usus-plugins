// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.projectusus.core.IMetricACDHotspot;

class ACDHotspotsCP extends HotspotsCP implements IStructuredContentProvider {

    @Override
    public Object[] getChildren( Object parentElement ) {
        Object[] result = null;
        if( parentElement instanceof IMetricACDHotspot ) {
            IMetricACDHotspot acdHotspot = (IMetricACDHotspot)parentElement;
            // TODO lf get the children - what interface should they have?
        }
        return result;
    }
}
