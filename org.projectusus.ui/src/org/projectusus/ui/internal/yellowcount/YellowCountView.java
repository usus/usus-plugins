// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.yellowcount;

import static org.eclipse.swt.SWT.BOLD;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.internal.yellowcount.IYellowCountListener;
import org.projectusus.core.internal.yellowcount.IYellowCountResult;
import org.projectusus.core.internal.yellowcount.YellowCount;

/**
 * A simple view that displays the current yellow count to the user.
 * 
 * @author Leif Frenzel
 */
public class YellowCountView extends ViewPart {

    private StyledText styledText;
    private IYellowCountListener listener;
    private final ColorRegistry colorRegistry = new ColorRegistry();

    // interface methods of ViewPart
    // //////////////////////////////

    @Override
    public void createPartControl( final Composite parent ) {
        styledText = new StyledText( parent, SWT.WRAP );
        styledText.setEditable( false );
        styledText.setBackground( getBackgroundColor() );

        listener = new IYellowCountListener() {
            public void yellowCountChanged() {
                IYellowCountResult countResult = YellowCount.getInstance().countOld();
                display( countResult );
            }
        };
        YellowCount.getInstance().addYellowCountListener( listener );
        display( YellowCount.getInstance().countOld() );
    }

    @Override
    public void setFocus() {
        styledText.setFocus();
    }

    @Override
    public void dispose() {
        YellowCount.getInstance().removeYellowCountListener( listener );
        super.dispose();
    }

    // helping methods
    // ////////////////

    private void display( final IYellowCountResult countResult ) {
        final String displayString = countResult.toString();
        Display.getDefault().asyncExec( new Runnable() {
            public void run() {
                if( styledText != null && !styledText.isDisposed() ) {
                    styledText.setText( displayString );
                    styledText.setStyleRange( createStyleRange( countResult ) );
                    styledText.setBackground( getBackgroundColor() );
                    styledText.redraw();
                }
            }
        } );
    }

    private Color getBackgroundColor() {
        int fade = YellowCount.getInstance().countOld().getYellowCount();
        fade = (fade > 255) ? 0 : 255 - fade;
        RGB rgb = new RGB( 255, 255, fade );
        if( !colorRegistry.hasValueFor( rgb.toString() ) ) {
            colorRegistry.put( rgb.toString(), rgb );
        }
        return colorRegistry.get( rgb.toString() );
    }

    private StyleRange createStyleRange( IYellowCountResult countResult ) {
        Color foreground = styledText.getForeground();
        Color background = styledText.getBackground();
        int position = countResult.getFormattedCountPosition();
        int length = countResult.getFormattedCountLength();
        return new StyleRange( position, length, foreground, background, BOLD );
    }
}
