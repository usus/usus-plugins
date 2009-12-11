// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

class LightWeightDialog extends Dialog {

    // needed for the typical light-weight control behaviour (go out of the way
    // if focus is lost). We need it for avoiding that the Shell closes already
    // when it loses focus at opening time.
    private boolean decativateListenerActive;
    private Composite area;
    private Text txtSomeExampleControl;
    private Font boldLabelFont;
    private MethodFormatter methodFormatter;

    LightWeightDialog( Shell parentShell ) {
        super( parentShell );
        // This has not for the user a 'blocking' effect, i.e. if the user
        // clicks on the screen outside, this dialog will disapper (lightweight
        // behaviour). But we need it to ensure that all events (e.g. selection)
        // on this dialog have been dispatched when the dialog is closed.
        setBlockOnOpen( true );
    }

    void setInput( IMethod method ) {
        methodFormatter = new MethodFormatter( method );
    }

    // interface methods of Dialog
    // ////////////////////////////

    @Override
    protected int getShellStyle() {
        return SWT.NO_TRIM;
    }

    @Override
    protected void configureShell( Shell newShell ) {
        applyInfoColors( newShell );
        newShell.setLayout( new GridLayout() );
        initializeDeactivationHandling( newShell );
    }

    @Override
    protected Point getInitialSize() {
        // arbitrary default value
        return new Point( 430, 180 );
    }

    @Override
    protected Control createButtonBar( Composite parent ) {
        return null;
    }

    @Override
    protected Control createDialogArea( Composite parent ) {
        area = (Composite)super.createDialogArea( parent );
        applyInfoColors( area );
        createTitleArea();
        createSomeExampleControl();
        initializeKeyHandling();
        txtSomeExampleControl.setFocus();
        return area;
    }

    @Override
    public boolean close() {
        boldLabelFont.dispose();
        boldLabelFont = null;
        return super.close();
    }

    // internal
    // ////////

    private void initializeKeyHandling() {
        KeyAdapter listener = new KeyHandler();
        txtSomeExampleControl.addKeyListener( listener );
        txtSomeExampleControl.getShell().addKeyListener( listener );
    }

    private void createTitleArea() {
        Label lblTitle = new Label( area, SWT.NONE );
        applyInfoColors( lblTitle );
        lblTitle.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        lblTitle.setText( methodFormatter.formatHeadInfo() );
        makeBold( lblTitle );
    }

    private void makeBold( Control control ) {
        if( boldLabelFont == null ) {
            Font initialFont = control.getFont();
            FontData[] fontData = initialFont.getFontData();
            for( FontData element : fontData ) {
                element.setStyle( SWT.BOLD );
            }
            boldLabelFont = new Font( control.getDisplay(), fontData );
        }
        control.setFont( boldLabelFont );
    }

    private void createSomeExampleControl() {
        txtSomeExampleControl = new Text( area, SWT.BORDER | SWT.MULTI | SWT.WRAP );
        txtSomeExampleControl.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        txtSomeExampleControl.setVisible( false );
    }

    private void applyInfoColors( Control control ) {
        Display display = Display.getCurrent();
        control.setBackground( display.getSystemColor( SWT.COLOR_INFO_BACKGROUND ) );
        control.setForeground( display.getSystemColor( SWT.COLOR_INFO_FOREGROUND ) );
    }

    private void initializeDeactivationHandling( final Shell newShell ) {
        Listener deactivateListener = new Listener() {
            public void handleEvent( Event event ) {
                if( decativateListenerActive ) {
                    newShell.dispose();
                }
            }
        };
        newShell.addListener( SWT.Deactivate, deactivateListener );
        decativateListenerActive = true;
        newShell.addShellListener( new ShellAdapter() {
            @Override
            public void shellActivated( ShellEvent event ) {
                if( event.widget == newShell && newShell.getShells().length == 0 ) {
                    decativateListenerActive = true;
                }
            }
        } );
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyReleased( KeyEvent event ) {
            // return key
            if( event.keyCode == 0x0D ) {
                close();
            }
            // ESC
            if( event.character == 0x1B ) {
                close();
            }
        }
    }
}
