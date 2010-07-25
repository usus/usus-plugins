// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.projectusus.ui.internal.proportions.infopresenter.infomodel.IUsusInfo;

public class UsusInfoViewer extends TreeViewer {

    public UsusInfoViewer( Composite parent, IUsusInfo ususInfo ) {
        super( parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE );
        setLabelProvider( new LabelProvider() );
        setLabelProvider( new UsusInfoLP() );
        setContentProvider( new UsusInfoCP( ususInfo ) );
        setInput( new Object() );
    }
}
