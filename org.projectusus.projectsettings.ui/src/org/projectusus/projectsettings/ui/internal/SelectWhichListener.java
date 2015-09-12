package org.projectusus.projectsettings.ui.internal;

import java.util.List;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.projectusus.projectsettings.core.WhichPrefs;

public class SelectWhichListener implements SelectionListener {

    private final WhichPrefs whichPref;
    private final Button checkbox;
    private final IWizard wizard;
    private final List<WhichPrefs> whichPrefs;

    public SelectWhichListener( Button checkbox, IWizard wizard, List<WhichPrefs> whichPrefs, WhichPrefs whichPref ) {
        super();
        this.checkbox = checkbox;
        this.wizard = wizard;
        this.whichPrefs = whichPrefs;
        this.whichPref = whichPref;
    }

    public void widgetSelected( SelectionEvent e ) {
        addOrRemoveSelected( checkbox.getSelection() );
        wizard.getContainer().updateButtons();
    }

    public void widgetDefaultSelected( SelectionEvent e ) {
        widgetSelected( e );
    }

    private void addOrRemoveSelected( boolean selection ) {
        if( selection ) {
            whichPrefs.add( whichPref );
        } else {
            whichPrefs.remove( whichPref );
        }
    }

}
