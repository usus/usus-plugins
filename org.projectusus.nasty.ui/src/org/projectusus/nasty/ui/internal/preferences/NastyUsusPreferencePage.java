// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.projectusus.nasty.ui.internal.NastyUsus;

public class NastyUsusPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    public NastyUsusPreferencePage() {
        super( GRID );
        setPreferenceStore( NastyUsus.getDefault().getPreferenceStore() );
        setDescription( "Settings for running Usus in Nasty Mode." );
    }

    @Override
    public void createFieldEditors() {
        Composite parent = getFieldEditorParent();
        addField( new NiceNastyFieldEditor( parent ) );
    }

    public void init( IWorkbench workbench ) {
        // unused
    }
}
