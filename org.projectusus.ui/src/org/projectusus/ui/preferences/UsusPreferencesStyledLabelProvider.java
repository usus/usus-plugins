package org.projectusus.ui.preferences;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.projectusus.core.statistics.CockpitExtensionPref;

public class UsusPreferencesStyledLabelProvider extends WorkbenchLabelProvider implements IStyledLabelProvider {

    public StyledString getStyledText( Object element ) {
        if( element instanceof CockpitExtensionPref ) {
            return getStyledText( (CockpitExtensionPref)element );
        }
        return new StyledString();
    }

    private StyledString getStyledText( CockpitExtensionPref metric ) {
        return new StyledString( metric.getLabel() ).append( " \u2014 " + metric.getClassName(), StyledString.DECORATIONS_STYLER ); //$NON-NLS-1$
    }

}
