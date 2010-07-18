package org.projectusus.ui.preferences;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class UsusPreferencesLabelProvider extends WorkbenchLabelProvider implements ITableLabelProvider {

    public Image getColumnImage( Object element, int columnIndex ) {
        return null;
    }

    public String getColumnText( Object element, int columnIndex ) {
        return element.toString();
    }

}
