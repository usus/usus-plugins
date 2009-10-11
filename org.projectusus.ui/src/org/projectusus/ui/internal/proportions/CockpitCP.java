// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelStatus;
import org.projectusus.core.internal.proportions.model.IUsusElement;

class CockpitCP implements ITreeContentProvider {

    public Object[] getElements( Object inputElement ) {
        List<Object> result = new ArrayList<Object>();
        if( inputElement instanceof IUsusModel ) {
            IUsusModel model = (IUsusModel)inputElement;
            for( IUsusElement element : model.getElements() ) {
                result.add( element );
            }
            IUsusModelStatus status = model.getLastStatus();
            result.add( "Last Run : " + formatLastTestRun( status ) );
            result.add( "Last Test: " + status.getLastTestRun() );
        }
        return result.toArray();
    }

    public Object[] getChildren( Object parentElement ) {
        Object[] result = new Object[0];
        if( parentElement instanceof IUsusElement ) {
            IUsusElement ususElement = (IUsusElement)parentElement;
            result = ususElement.getEntries().toArray();
        }
        return result;
    }

    public Object getParent( Object element ) {
        return null;
    }

    public boolean hasChildren( Object element ) {
        return getChildren( element ).length > 0;
    }

    public void dispose() {
        // unused
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
        // unused
    }

    // internal
    // /////////

    private String formatLastTestRun( IUsusModelStatus status ) {
        String success = status.isLastComputationRunSuccessful() ? "OK" : "Not completed";
        return status.getLastComputerRun() + " (" + success + ")";
    }

}
