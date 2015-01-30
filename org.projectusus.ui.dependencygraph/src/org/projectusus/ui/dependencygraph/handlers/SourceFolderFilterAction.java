package org.projectusus.ui.dependencygraph.handlers;

import static org.apache.commons.lang.StringUtils.join;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.core.runtime.IStatus.OK;
import static org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PACKFRAG_ROOT;
import static org.projectusus.ui.dependencygraph.DependencyGraphPlugin.plugin;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.projectusus.jfeet.selection.ElementsFrom;
import org.projectusus.ui.dependencygraph.DependencyGraphPlugin;
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

        SelectionDialog dialog = createSelectionDialog( shell );
        boolean changed = false;
        if( dialog.open() == OK ) {
            List<IPath> newVisibleSourceFolders = pathsFromResult( dialog );
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

    private List<IPath> pathsFromResult( SelectionDialog dialog ) {
        return new ElementsFrom( new StructuredSelection( dialog.getResult() ) ).as( IPath.class );
    }

    private SelectionDialog createSelectionDialog( Shell shell ) {
        CheckedTreeSelectionDialog dialog = new CheckedTreeSelectionDialog( shell, new SourceFolderLabelProvider(), new SourceFolderContentProvider() );
        dialog.setTitle( "Visible source folders" );
        dialog.setMessage( "Please select visible source folders:" );
        dialog.setHelpAvailable( false );
        dialog.setInput( filter.getAllSourceFolders() );
        dialog.setInitialElementSelections( filter.getVisibleSourceFolders() );
        dialog.setValidator( new AtLeastOneSourceFolderValidator() );
        return dialog;
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

    private final static class AtLeastOneSourceFolderValidator implements ISelectionStatusValidator {
        public IStatus validate( Object[] selection ) {
            if( selection.length > 0 ) {
                return status( OK, "" );
            }
            return status( ERROR, "Please select at least one source folder." );
        }

        private IStatus status( int status, String message ) {
            return new Status( status, DependencyGraphPlugin.PLUGIN_ID, message );
        }
    }

    private final static class SourceFolderLabelProvider extends LabelProvider {
        @Override
        public Image getImage( Object element ) {
            return JavaUI.getSharedImages().getImage( IMG_OBJS_PACKFRAG_ROOT );
        }
    }

    private final static class SourceFolderContentProvider extends ArrayContentProvider implements ITreeContentProvider {

        public Object[] getChildren( Object parentElement ) {
            return null;
        }

        public Object getParent( Object element ) {
            return null;
        }

        public boolean hasChildren( Object element ) {
            return false;
        }
    }

}
