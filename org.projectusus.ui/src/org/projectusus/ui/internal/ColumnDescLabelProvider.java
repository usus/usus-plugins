// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.projectusus.ui.internal.viewer.IColumnDesc;

public abstract class ColumnDescLabelProvider<T> extends LabelProvider implements ITableLabelProvider {

    private final List<? extends IColumnDesc<T>> columnDescs;
    private final Class<T> clazz;

    public ColumnDescLabelProvider( Class<T> clazz, List<? extends IColumnDesc<T>> columnDescs ) {
        this.clazz = clazz;
        this.columnDescs = columnDescs;
    }

    @SuppressWarnings( "unchecked" )
    public String getColumnText( Object element, int columnIndex ) {
        String result = element.toString();
        if( clazz.isAssignableFrom( element.getClass() ) ) {
            result = columnDescs.get( columnIndex ).getLabel( (T)element );
        }
        return result;
    }

    public Image getColumnImage( Object element, int columnIndex ) {
        return null;
    }

}
