// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.badthings;

import static org.eclipse.ui.PlatformUI.getWorkbench;
import static org.eclipse.ui.plugin.AbstractUIPlugin.imageDescriptorFromPlugin;
import static org.projectusus.nasty.ui.internal.NastyUsus.getPluginId;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

abstract class SpectacularDialog {

    private Shell shell;
    private Label titleLabel;
    private Label lblClose;
    private Composite lblTitlebar;

    SpectacularDialog() {
        initShell();
        configureShell();
        createTitleArea();
        createContents( createDialogArea() );
        shell.pack( true );
        roudCorners( shell );
        shell.addControlListener( new ControlAdapter() {
            @Override
            public void controlResized( ControlEvent event ) {
                SpectacularDialog.roudCorners( shell );
                shell.redraw();
            }
        } );
    }

    abstract String getTitle();

    abstract void createContents( Composite composite );

    Shell getShell() {
        return shell;
    }

    void dispose() {
        shell.dispose();
    }

    void open() {
        shell.setLocation( new Point( 420, 420 ) );
        shell.open();
    }

    private void initShell() {
        Shell parentShell = Display.getCurrent().getActiveShell();
        if( parentShell == null ) {
            parentShell = getCurrentWBShell();
        }
        shell = new Shell( parentShell, 8 );
    }

    private Shell getCurrentWBShell() {
        IWorkbenchWindow window = getWorkbench().getActiveWorkbenchWindow();
        return window == null ? null : window.getShell();
    }

    private void createTitleArea() {
        createTitlebar();
        createCloseLabel();
        createTitleLabel();
        initListeners();
    }

    private void createTitleLabel() {
        titleLabel = new Label( lblTitlebar, SWT.NONE );
        titleLabel.setForeground( getColor( SWT.COLOR_WHITE ) );
        GridData gridData = new GridData( 16777216, 16777216, true, true );
        gridData.horizontalIndent = 4;
        titleLabel.setLayoutData( gridData );
        titleLabel.setFont( createTitleFont() );
        titleLabel.setText( getTitle() );
    }

    private Font createTitleFont() {
        FontData fontData = titleLabel.getFont().getFontData()[0];
        return new Font( Display.getCurrent(), fontData.getName(), 10, fontData.getStyle() );
    }

    private void initListeners() {
        addDragListener( lblTitlebar );
        addDragListener( titleLabel );
        addCloseListener( lblClose );
        shell.addShellListener( new ShellAdapter() {
            @Override
            public void shellActivated( ShellEvent e ) {
                lblTitlebar.setBackgroundImage( getImage( "dialog_om.png" ) );
            }

            @Override
            public void shellDeactivated( ShellEvent e ) {
                lblTitlebar.setBackgroundImage( getImage( "dialog_omd.png" ) );
            }
        } );
    }

    private void configureShell() {
        shell.setAlpha( 192 );
        shell.setBackground( getColor( SWT.COLOR_BLACK ) );
        shell.setBackgroundMode( SWT.INHERIT_DEFAULT );
        shell.setLayout( createShellLayout() );
    }

    private GridLayout createShellLayout() {
        GridLayoutFactory fillDefaults = GridLayoutFactory.fillDefaults();
        GridLayout layout = fillDefaults.create();
        layout.numColumns = 3;
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        return layout;
    }

    private void createTitlebar() {
        lblTitlebar = new Composite( shell, SWT.NONE );
        GridData layoutData = new GridData( 4, 16777216, true, false );
        layoutData.heightHint = 19;
        lblTitlebar.setLayoutData( layoutData );
        lblTitlebar.setBackgroundImage( getImage( "dialog_om.png" ) );
        lblTitlebar.setLayout( createTitleLayout() );
    }

    private GridLayout createTitleLayout() {
        GridLayout titlelayout = GridLayoutFactory.fillDefaults().create();
        titlelayout.numColumns = 2;
        titlelayout.horizontalSpacing = 0;
        titlelayout.verticalSpacing = 0;
        titlelayout.marginLeft = 10;
        titlelayout.marginRight = 10;
        return titlelayout;
    }

    private void createCloseLabel() {
        lblClose = new Label( lblTitlebar, SWT.NONE );
        lblClose.setLayoutData( new GridData( 16384, 16777216, false, true ) );
        lblClose.setImage( getImage( "close.png" ) );
        lblClose.setCursor( new Cursor( Display.getCurrent(), SWT.CURSOR_HAND ) );
    }

    private Composite createDialogArea() {
        Composite area = new Composite( shell, 0 );
        GridData areaGD = new GridData( 4, 4, true, true );
        areaGD.horizontalSpan = 3;
        area.setLayoutData( areaGD );
        GridLayout arealayout = GridLayoutFactory.fillDefaults().create();
        arealayout.numColumns = 1;
        arealayout.horizontalSpacing = 0;
        arealayout.verticalSpacing = 0;
        area.setLayout( arealayout );
        return area;
    }

    private void addDragListener( final Control control ) {
        Listener listener = new Listener() {

            Point origin;

            public void handleEvent( Event event ) {
                switch( event.type ) {
                    case 3: // '\003'
                        origin = new Point( event.x, event.y );
                        break;
                    case 4: // '\004'
                        origin = null;
                        break;
                    case 5: // '\005'
                        if( origin != null ) {
                            Point p = control.getDisplay().map( shell, null, event.x, event.y );
                            shell.setLocation( p.x - origin.x, p.y - origin.y );
                        }
                }
            }
        };
        control.addListener( 3, listener );
        control.addListener( 4, listener );
        control.addListener( 5, listener );
    }

    private void addCloseListener( Control control ) {
        control.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseDown( MouseEvent event ) {
                shell.close();
            }
        } );
    }

    private static void roudCorners( Shell shell ) {
        Region region = new Region();
        Point shellSize = shell.getSize();
        region.add( 0, 0, shellSize.x, shellSize.y );
        region.subtract( 0, 0, 5, 1 );
        region.subtract( 0, 0, 1, 5 );
        region.subtract( 0, 0, 3, 2 );
        region.subtract( 0, 0, 2, 3 );
        region.subtract( 0, shellSize.y - 1, 5, 1 );
        region.subtract( 0, shellSize.y - 5, 1, 5 );
        region.subtract( 0, shellSize.y - 2, 3, 2 );
        region.subtract( 0, shellSize.y - 3, 2, 3 );
        region.subtract( shellSize.x - 5, 0, 5, 1 );
        region.subtract( shellSize.x - 1, 0, 1, 5 );
        region.subtract( shellSize.x - 3, 0, 3, 2 );
        region.subtract( shellSize.x - 2, 0, 2, 3 );
        region.subtract( shellSize.x - 5, shellSize.y - 1, 5, 1 );
        region.subtract( shellSize.x - 1, shellSize.y - 5, 1, 5 );
        region.subtract( shellSize.x - 3, shellSize.y - 2, 3, 2 );
        region.subtract( shellSize.x - 2, shellSize.y - 3, 2, 3 );
        shell.setRegion( region );
    }

    private Color getColor( int key ) {
        return Display.getCurrent().getSystemColor( key );
    }

    private Image getImage( String name ) {
        String imageFilePath = "icons/" + name;
        ImageDescriptor desc = imageDescriptorFromPlugin( getPluginId(), imageFilePath );
        return desc.createImage();
    }
}
