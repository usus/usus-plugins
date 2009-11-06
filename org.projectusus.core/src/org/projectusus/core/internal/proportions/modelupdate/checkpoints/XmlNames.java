// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate.checkpoints;

class XmlNames {

    static final String CHECKPOINTS_FILE = "checkpoints.xml"; //$NON-NLS-1$

    static final String ELEM_ROOT = "checkpoints"; //$NON-NLS-1$
    static final String ELEM_CHECKPOINT = "checkpoint"; //$NON-NLS-1$
    static final String ELEM_ENTRY = "entry"; //$NON-NLS-1$

    static final String ATT_TIME = "time"; //$NON-NLS-1$
    static final String ATT_METRIC = "metric"; //$NON-NLS-1$
    static final String ATT_CASES = "cases"; //$NON-NLS-1$
    static final String ATT_VIOLATIONS = "violations"; //$NON-NLS-1$
    static final String ATT_SQI = "sqi"; //$NON-NLS-1$

    static final String PREAMBLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"; //$NON-NLS-1$
    static final String INDENT = "  "; //$NON-NLS-1$
    static final String DATE_TIME_PATTERN = "yyyyMMdd-HHmmss"; //$NON-NLS-1$
}
