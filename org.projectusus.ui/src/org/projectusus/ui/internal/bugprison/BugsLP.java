// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugprison;

import java.util.List;

import org.projectusus.bugprison.core.Bug;
import org.projectusus.ui.internal.viewer.ColumnDescLabelProvider;
import org.projectusus.ui.internal.viewer.IColumnDesc;

public class BugsLP extends ColumnDescLabelProvider<Bug> {

    public BugsLP( List<? extends IColumnDesc<Bug>> columnDescs ) {
        super( Bug.class, columnDescs );
    }

}
