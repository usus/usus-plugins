package org.projectusus.ui.preferences;

import static org.eclipse.jface.viewers.CheckboxTableViewer.newCheckList;

import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.projectusus.core.statistics.CockpitExtensionPref;
import org.projectusus.core.statistics.RegisteredCockpitExtensionsCollector;

public class UsusPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private SortedSet<CockpitExtensionPref> extensionsStates;

    public UsusPreferencePage() {
        super();
        setDescription( "Enable/disable the registered Usus Metric Statistics:" );
    }

    public void init( IWorkbench workbench ) {
        extensionsStates = RegisteredCockpitExtensionsCollector.getExtensionsStates();
    }

    @Override
    protected Control createContents( Composite parent ) {
        CheckboxTableViewer viewer = newCheckList( parent, SWT.BORDER );
        viewer.setContentProvider( new UsusPreferencesContentProvider() );
        viewer.setLabelProvider( new UsusPreferencesLabelProvider() );
        viewer.setInput( extensionsStates );
        viewer.setCheckedElements( collectCheckedElements() );
        viewer.addCheckStateListener( new ICheckStateListener() {
            public void checkStateChanged( CheckStateChangedEvent event ) {
                updatePrefsElement( event.getElement(), event.getChecked() );
            }
        } );
        return viewer.getControl();
    }

    private CockpitExtensionPref[] collectCheckedElements() {
        SortedSet<CockpitExtensionPref> checkedElements = new TreeSet<CockpitExtensionPref>();
        for( CockpitExtensionPref pref : extensionsStates ) {
            if( pref.isOn() ) {
                checkedElements.add( pref );
            }
        }
        CockpitExtensionPref[] array = checkedElements.toArray( new CockpitExtensionPref[0] );
        return array;
    }

    protected void updatePrefsElement( Object element, boolean checked ) {
        if( element instanceof CockpitExtensionPref ) {
            ((CockpitExtensionPref)element).setOn( checked );
        }
    }

    @Override
    public boolean performOk() {
        RegisteredCockpitExtensionsCollector.updateExtensionsStates( extensionsStates );
        return super.performOk();
    }
}
