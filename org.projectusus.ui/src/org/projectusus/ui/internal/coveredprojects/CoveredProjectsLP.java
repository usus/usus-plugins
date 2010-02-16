// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.coveredprojects;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class CoveredProjectsLP extends WorkbenchLabelProvider implements ITableLabelProvider {

    public Image getColumnImage( Object element, int columnIndex ) {
        Image result = null;
        if( getColumnDesc( columnIndex ).isHasImage() ) {
            result = super.getImage( element );
        }
        return result;
    }

    public String getColumnText( Object element, int columnIndex ) {
        String result = null;
        if( getColumnDesc( columnIndex ).isHasText() ) {
            result = super.getText( element );
        }
        return result;
    }

    private ColumnDesc getColumnDesc( int columnIndex ) {
        return ColumnDesc.values()[columnIndex];
    }
}
