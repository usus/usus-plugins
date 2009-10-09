// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.projectusus.core.internal.proportions.CodeProportions;
import org.projectusus.core.internal.proportions.ICodeProportionsStatus;


public class CodeProportionsCockpitCP implements IStructuredContentProvider {

    public Object[] getElements( Object inputElement ) {
        List<Object> result = new ArrayList<Object>();
        if( inputElement instanceof CodeProportions ) {
            CodeProportions codeProportions = (CodeProportions)inputElement;
            result.addAll( codeProportions.getEntries() );
            ICodeProportionsStatus status = codeProportions.getLastStatus();
            result.add( "Last Run : " + formatLastTestRun( status ) );
            result.add( "Last Test: " + status.getLastTestRun() );
        }
        return result.toArray();
    }

    public void dispose() {
        // unused
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
        // unused
    }

    // internal
    // /////////

    private String formatLastTestRun( ICodeProportionsStatus status ) {
        String success = status.isLastComputationRunSuccessful() ? "OK" : "Not completed";
        return status.getLastComputerRun() + " (" + success + ")";
    }
}
