package org.projectusus.ui.preferences;

import static org.eclipse.jface.viewers.CheckboxTableViewer.newCheckList;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.projectusus.core.UsusCorePlugin;
import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.statistics.CockpitExtensionPref;
import org.projectusus.core.statistics.RegisteredCockpitExtensionsCollector;

public class UsusPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private SortedSet<CockpitExtensionPref> extensionsStates;
    private CheckboxTableViewer viewer;

    public UsusPreferencePage() {
        super();
        setDescription( "Enable/disable the registered Usus Metric Statistics:" ); //$NON-NLS-1$
    }

    public void init( @SuppressWarnings( "unused" ) IWorkbench workbench ) {
        extensionsStates = RegisteredCockpitExtensionsCollector.getExtensionsStates();
    }

    @Override
    protected Control createContents( Composite parent ) {
        viewer = newCheckList( parent, SWT.BORDER );
        viewer.setContentProvider( new UsusPreferencesContentProvider() );
        viewer.setLabelProvider( new DelegatingStyledCellLabelProvider( new UsusPreferencesStyledLabelProvider() ) );
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
        Set<CockpitExtensionPref> checkedElements = new HashSet<CockpitExtensionPref>();
        for( CockpitExtensionPref pref : extensionsStates ) {
            if( pref.isOn() ) {
                checkedElements.add( pref );
            }
        }
        return checkedElements.toArray( new CockpitExtensionPref[0] );
    }

    protected void updatePrefsElement( Object element, boolean checked ) {
        if( element instanceof CockpitExtensionPref ) {
            ((CockpitExtensionPref)element).setOn( checked );
        }
    }

    @Override
    public boolean performOk() {
        RegisteredCockpitExtensionsCollector.saveExtensionsStates( extensionsStates );
        UsusCorePlugin.getDefault().savePreferences();
        UsusModelProvider.ususModel().refreshCodeProportions();
        return super.performOk();
    }

    @Override
    protected void performDefaults() {
        CockpitExtensionPref.restoreDefaults( extensionsStates );
        viewer.setCheckedElements( collectCheckedElements() );
        super.performDefaults();
    }
}
