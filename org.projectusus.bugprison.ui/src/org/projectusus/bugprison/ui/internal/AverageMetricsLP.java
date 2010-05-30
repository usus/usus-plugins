// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugprison.ui.internal;

import java.util.List;

import org.projectusus.bugprison.core.IAverageMetrics;
import org.projectusus.ui.viewer.ColumnDescLabelProvider;
import org.projectusus.ui.viewer.IColumnDesc;

public class AverageMetricsLP extends ColumnDescLabelProvider<IAverageMetrics> {

    public AverageMetricsLP( List<? extends IColumnDesc<IAverageMetrics>> columnDescs ) {
        super( IAverageMetrics.class, columnDescs );
    }

}
