package org.projectusus.ui.dependencygraph.handlers;

import static org.apache.commons.lang.StringUtils.join;
import static org.eclipse.core.runtime.IStatus.OK;
import static org.projectusus.ui.dependencygraph.DependencyGraphPlugin.plugin;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.projectusus.ui.dependencygraph.common.IRefreshable;
import org.projectusus.ui.dependencygraph.filters.SourceFolderFilter;

public final class SourceFolderFilterAction extends Action {

    private final SourceFolderFilter filter;
    private final IRefreshable refreshable;

    public SourceFolderFilterAction( SourceFolderFilter filter, IRefreshable refreshable ) {
        super( "", AS_CHECK_BOX );
        this.filter = filter;
        this.refreshable = refreshable;
        updateState();
    }

    @Override
    public void runWithEvent( Event event ) {
        Shell shell = event.display.getActiveShell();

        SourceFolderFilterSelectionDialog dialog = createSelectionDialog( shell );
        boolean changed = false;
        if( dialog.open() == OK ) {
            List<IPath> newVisibleSourceFolders = dialog.getSelectedSourceFolders();
            if( hasChanged( newVisibleSourceFolders ) ) {
                filter.setVisibleSourceFolders( newVisibleSourceFolders );
                changed = true;
            }
        }
        updateState();

        if( changed ) {
            refreshable.refresh();
        }
    }

    private boolean hasChanged( List<IPath> newVisibleSourceFolders ) {
        return !newVisibleSourceFolders.equals( filter.getVisibleSourceFolders() );
    }

    private SourceFolderFilterSelectionDialog createSelectionDialog( Shell shell ) {
        return new SourceFolderFilterSelectionDialog( shell, filter );
    }

    public void updateState() {
        setChecked( filter.isFiltering() );
        if( isChecked() ) {
            setImageDescriptor( plugin().imageForPath( "icons/source_folder_filter_active.png" ) );
            setText( filter.getVisibleSourceFolders().size() + "/" + filter.getAllSourceFolders().size() + " source folders" );
            setToolTipText( "Visible source folders: " + join( filter.getVisibleSourceFolders(), ", " ) );
        } else {
            setImageDescriptor( plugin().imageForPath( "icons/source_folder_filter_inactive.png" ) );
            setText( "All source folders" );
            setToolTipText( null );
        }
    }

}
