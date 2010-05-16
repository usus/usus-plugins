// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.IUsusElement;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.internal.proportions.modelupdate.checkpoints.CheckpointHistory;

import com.mountainminds.eclemma.core.analysis.IJavaModelCoverage;

public interface IUsusModel {

    IUsusElement[] getElements();

    CheckpointHistory getHistory();

    void addUsusModelListener( IUsusModelListener listener );

    void removeUsusModelListener( IUsusModelListener listener );

    CodeProportion getCodeProportion( CodeProportionKind metric );

    boolean needsFullRecompute();

    IMetricsAccessor getMetricsAccessor();

    void collectCoverageInfo( IJavaModelCoverage javaModelCoverage );
}
