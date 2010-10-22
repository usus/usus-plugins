package org.projectusus.ui.dependencygraph.handlers;

import static org.projectusus.ui.dependencygraph.DependencyGraphPlugin.plugin;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;

public class ChangeZoom extends Action implements IMenuCreator {

    private static final String LABEL = "Zoom";
    private static final String ICON_PATH = "icons/zoom.gif";

    private final IZoomableWorkbenchPart view;
    private Menu menu;

    public ChangeZoom( IZoomableWorkbenchPart view ) {
        super( LABEL, IAction.AS_DROP_DOWN_MENU );
        this.view = view;
        setImageDescriptor( plugin().imageForPath( ICON_PATH ) );
        setMenuCreator( this );
    }

    @Override
    public void run() {
        resetZoom();
    }

    protected void resetZoom() {
        view.getZoomableViewer().zoomTo( 0, 0, 0, 0 );
    }

    public Menu getMenu( Menu parent ) {
        if( menu == null ) {
            menu = new Menu( parent );
            fill();
        }
        return menu;
    }

    public Menu getMenu( Control parent ) {
        if( menu == null ) {
            menu = new Menu( parent );
            fill();
        }
        return menu;
    }

    private void fill() {
        new ZoomContributionViewItem( view ).fill( menu, 0 );
    }

    public void dispose() {
        if( menu != null ) {
            menu.dispose();
        }
    }
}
