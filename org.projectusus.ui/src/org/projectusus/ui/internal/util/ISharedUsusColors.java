// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.util;

import org.eclipse.swt.graphics.Color;

public interface ISharedUsusColors {

    String ISIS_METRIC_PC = "ISIS_METRIC_PC"; 
    String ISIS_METRIC_ACD = "ISIS_METRIC_ACD"; 
    String ISIS_METRIC_TA = "ISIS_METRIC_TA"; 
    String ISIS_METRIC_KG = "ISIS_METRIC_KG"; 
    String ISIS_METRIC_CC = "ISIS_METRIC_CC"; 
    String ISIS_METRIC_ML = "ISIS_METRIC_ML"; 
    String ISIS_METRIC_CW = "ISIS_METRIC_CW"; 

    Color getColor( String key );
}
