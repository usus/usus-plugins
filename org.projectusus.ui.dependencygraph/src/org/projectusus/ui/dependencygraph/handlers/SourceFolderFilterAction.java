package org.projectusus.ui.dependencygraph.handlers;

import static org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PACKFRAG_ROOT;
import static org.eclipse.jface.window.Window.OK;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.projectusus.jfeet.selection.ElementsFrom;
import org.projectusus.ui.dependencygraph.filters.SourceFolderFilter;

public final class SourceFolderFilterAction extends Action {

    private final SourceFolderFilter filter;

    public SourceFolderFilterAction( SourceFolderFilter filter ) {
        super( "", AS_CHECK_BOX );
        this.filter = filter;
        updateState();
    }

    @Override
    public void runWithEvent( Event event ) {
        Shell shell = event.display.getActiveShell();

        SelectionDialog dialog = createSelectionDialog( shell );
        if( dialog.open() == OK ) {
            filter.setVisibleSourceFolders( pathsFromResult( dialog ) );
        }

        updateState();
    }

    private List<IPath> pathsFromResult( SelectionDialog dialog ) {
        return new ElementsFrom( new StructuredSelection( dialog.getResult() ) ).as( IPath.class );
    }

    private SelectionDialog createSelectionDialog( Shell shell ) {
        ListSelectionDialog dialog = new ListSelectionDialog( shell, filter.getAllSourceFolders(), ArrayContentProvider.getInstance(), new SourceFolderLabelProvider(),
                "Please select visible source folders:" );
        dialog.setTitle( "Visible source folders" );
        dialog.setInitialElementSelections( filter.getVisibleSourceFolders() );
        dialog.setHelpAvailable( false );
        return dialog;
    }

    private void updateState() {
        setChecked( filter.getAllSourceFolders().size() > filter.getVisibleSourceFolders().size() );
        if( isChecked() ) {
            setText( filter.getVisibleSourceFolders().size() + "/" + filter.getAllSourceFolders().size() + " source folders" );
        } else {
            setText( "All source folders" );
        }
    }

    private final class SourceFolderLabelProvider extends LabelProvider {
        @Override
        public Image getImage( Object element ) {
            return JavaUI.getSharedImages().getImage( IMG_OBJS_PACKFRAG_ROOT );
        }
    }
}
