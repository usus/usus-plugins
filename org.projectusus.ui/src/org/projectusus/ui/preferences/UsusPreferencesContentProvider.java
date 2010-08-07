package org.projectusus.ui.preferences;

import java.util.Set;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.projectusus.core.statistics.CockpitExtensionPref;

public class UsusPreferencesContentProvider implements IStructuredContentProvider {

    public void dispose() {
        // not needed
    }

    public void inputChanged( @SuppressWarnings( "unused" ) Viewer viewer, @SuppressWarnings( "unused" ) Object oldInput, @SuppressWarnings( "unused" ) Object newInput ) {
        // not needed
    }

    @SuppressWarnings( "unchecked" )
    public Object[] getElements( Object inputElement ) {
        return ((Set<CockpitExtensionPref>)inputElement).toArray();
    }

}
