// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import org.eclipse.swt.graphics.Image;
import org.projectusus.ui.internal.proportions.UsusModelLabelProvider;

public class UsusInfoLP extends UsusModelLabelProvider {

    @Override
    public String getText( Object element ) {
        return getNodeTextFor( element );
    }

    @Override
    public Image getImage( Object element ) {
        return getColumnImageFor( element );
    }
}
