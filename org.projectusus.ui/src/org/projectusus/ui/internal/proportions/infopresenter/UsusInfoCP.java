// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.MetricStatisticsCategory;
import org.projectusus.ui.internal.proportions.infopresenter.infomodel.IUsusInfo;

class UsusInfoCP implements ITreeContentProvider {

    private final IUsusInfo ususInfo;

    UsusInfoCP( IUsusInfo ususInfo ) {
        this.ususInfo = ususInfo;
    }

    public void dispose() {
        // unused
    }

    public Object[] getChildren( Object parentElement ) {
        if( (parentElement instanceof String) ) {
            return new Object[0];
        }
        return ususInfo.getCodeProportionInfos();
    }

    public Object[] getElements( Object inputElement ) {
        return new Object[] { new MetricStatisticsCategory( new AnalysisDisplayEntry[0] ) };
    }

    public Object getParent( Object element ) {
        return null;
    }

    public boolean hasChildren( Object element ) {
        return getChildren( element ).length > 0;
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
        // unused
    }
}
