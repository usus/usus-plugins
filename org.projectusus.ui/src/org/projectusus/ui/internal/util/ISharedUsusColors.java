// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.util;

import org.eclipse.swt.graphics.Color;

public interface ISharedUsusColors {

    String ISIS_METRIC_PC = "ISIS_METRIC_PC"; //$NON-NLS-1$
    String ISIS_METRIC_ACD = "ISIS_METRIC_ACD"; //$NON-NLS-1$
    String ISIS_METRIC_TA = "ISIS_METRIC_TA"; //$NON-NLS-1$
    String ISIS_METRIC_KG = "ISIS_METRIC_KG"; //$NON-NLS-1$
    String ISIS_METRIC_CC = "ISIS_METRIC_CC"; //$NON-NLS-1$
    String ISIS_METRIC_ML = "ISIS_METRIC_ML"; //$NON-NLS-1$
    String ISIS_METRIC_CW = "ISIS_METRIC_CW"; //$NON-NLS-1$

    Color getColor( String key );
}
