package org.projectusus.ui.dependencygraph;

import static org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PACKFRAG_ROOT;
import static org.eclipse.jface.window.Window.OK;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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

final class SourceFolderFilterAction extends Action {

    private final List<IPath> allSourceFolders = new ArrayList<IPath>();
    private List<IPath> visibleSourceFolders = new ArrayList<IPath>();

    {
        allSourceFolders.add( Path.fromPortableString( "src/main/java" ) );
        allSourceFolders.add( Path.fromPortableString( "src/test/java" ) );

        visibleSourceFolders.add( Path.fromPortableString( "src/main/java" ) );
    }

    SourceFolderFilterAction() {
        super( "", AS_CHECK_BOX );
        updateState();
    }

    @Override
    public void runWithEvent( Event event ) {
        Shell shell = event.display.getActiveShell();

        SelectionDialog dialog = createSelectionDialog( shell );
        if( dialog.open() == OK ) {
            visibleSourceFolders = pathsFromResult( dialog );
        }

        updateState();
    }

    private List<IPath> pathsFromResult( SelectionDialog dialog ) {
        return new ElementsFrom( new StructuredSelection( dialog.getResult() ) ).as( IPath.class );
    }

    private SelectionDialog createSelectionDialog( Shell shell ) {
        ListSelectionDialog dialog = new ListSelectionDialog( shell, allSourceFolders, ArrayContentProvider.getInstance(), new SourceFolderLabelProvider(),
                "Please select visible source folders:" );
        dialog.setTitle( "Visible source folders" );
        dialog.setInitialElementSelections( visibleSourceFolders );
        dialog.setHelpAvailable( false );
        return dialog;
    }

    private void updateState() {
        setChecked( allSourceFolders.size() > visibleSourceFolders.size() );
        if( isChecked() ) {
            setText( visibleSourceFolders.size() + "/" + allSourceFolders.size() + " source folders" );
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
