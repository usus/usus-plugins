package org.projectusus.ui.dependencygraph.handlers;

import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PACKFRAG_ROOT;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.projectusus.jfeet.selection.ElementsFrom;
import org.projectusus.ui.dependencygraph.DependencyGraphPlugin;
import org.projectusus.ui.dependencygraph.filters.SourceFolderFilter;

public class SourceFolderFilterSelectionDialog extends CheckedTreeSelectionDialog {

    public SourceFolderFilterSelectionDialog( Shell parent, SourceFolderFilter filter ) {
        super( parent, new SourceFolderLabelProvider(), new SourceFolderContentProvider() );
        setTitle( "Visible source folders" );
        setMessage( "Please select visible source folders:" );
        setHelpAvailable( false );
        setValidator( new AtLeastOneSourceFolderValidator() );
        setInput( filter.getAllSourceFolders() );
        setInitialElementSelections( filter.getVisibleSourceFolders() );
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

    List<IPath> getSelectedSourceFolders() {
        return new ElementsFrom( new StructuredSelection( super.getResult() ) ).as( IPath.class );
    }
}
