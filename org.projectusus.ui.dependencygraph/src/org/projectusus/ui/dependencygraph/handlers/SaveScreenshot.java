package org.projectusus.ui.dependencygraph.handlers;

import static org.eclipse.ui.handlers.HandlerUtil.getActivePart;
import static org.eclipse.ui.handlers.HandlerUtil.getActiveShell;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class SaveScreenshot extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        execute( getActiveShell( event ), (DependencyGraphView)getActivePart( event ) );
        return null;
    }

    protected void execute( Shell shell, DependencyGraphView view ) {
        Image image = view.takeScreenshot();
        saveImage( shell, image, view.getFilenameForScreenshot() );
    }

    private void saveImage( Shell shell, final Image image, String filename ) {
        FileDialog dialog = new FileDialog( shell, SWT.SAVE );
        dialog.setFilterNames( new String[] { "Image Files", "All Files (*.*)" } );
        dialog.setFilterExtensions( new String[] { "*.png", "*.*" } ); // Windows wild cards
        dialog.setFileName( filename + ".png" );
        String fileName = dialog.open();
        if( filename != null ) {
            ImageLoader loader = new ImageLoader();
            loader.data = new ImageData[] { image.getImageData() };
            loader.save( fileName, SWT.IMAGE_PNG );
        }
    }

}
