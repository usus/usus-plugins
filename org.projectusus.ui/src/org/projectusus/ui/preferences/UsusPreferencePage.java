package org.projectusus.ui.preferences;

import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.projectusus.core.statistics.CockpitExtensionPref;
import org.projectusus.core.statistics.RegisteredCockpitExtensionsCollector;

/**
 * This class represents a preference page that is contributed to the Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we can use the field support built
 * into JFace that allows us to create a page that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that belongs to the main plug-in class. That way, preferences can be accessed directly via
 * the preference store.
 */

public class UsusPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private SortedSet<CockpitExtensionPref> extensionsStates;

    public UsusPreferencePage() {
        super();
        setDescription( "A Preferences Page to enable / disable the registered Metrics Statistics" );
    }

    public void init( IWorkbench workbench ) {
        extensionsStates = RegisteredCockpitExtensionsCollector.getExtensionsStates();
    }

    @Override
    protected Control createContents( Composite parent ) {
        CheckboxTableViewer viewer = new CheckboxTableViewer( createTable( parent ) );
        viewer.setContentProvider( new UsusPreferencesContentProvider() );
        viewer.setLabelProvider( new UsusPreferencesLabelProvider() );
        viewer.setInput( extensionsStates );
        SortedSet<CockpitExtensionPref> checkedElements = new TreeSet<CockpitExtensionPref>();
        for( CockpitExtensionPref pref : extensionsStates ) {
            if( pref.isOn() ) {
                checkedElements.add( pref );
            }
        }
        viewer.setCheckedElements( checkedElements.toArray() );
        viewer.addCheckStateListener( new ICheckStateListener() {
            public void checkStateChanged( CheckStateChangedEvent event ) {
                updatePrefsElement( event.getElement(), event.getChecked() );
            }
        } );
        return viewer.getControl();
    }

    protected void updatePrefsElement( Object element, boolean checked ) {
        if( element instanceof CockpitExtensionPref ) {
            ((CockpitExtensionPref)element).setOn( checked );
        }
    }

    private Table createTable( Composite parent ) {
        Composite comp = new Composite( parent, SWT.NONE );
        comp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        int style = SWT.CHECK | SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL;
        Table result = new Table( comp, style );
        TableColumnLayout layout = new TableColumnLayout();
        comp.setLayout( layout );

        result.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        result.setLinesVisible( false );
        result.setHeaderVisible( false );

        createColumns( result, layout );
        return result;
    }

    private void createColumns( Table table, TableColumnLayout layout ) {
        TableColumn column = new TableColumn( table, SWT.NONE );
        ColumnWeightData data = new ColumnWeightData( 87 );
        layout.setColumnData( column, data );
    }

    @Override
    public boolean performOk() {
        RegisteredCockpitExtensionsCollector.updateExtensionsStates( extensionsStates );
        return super.performOk();
    }
}
