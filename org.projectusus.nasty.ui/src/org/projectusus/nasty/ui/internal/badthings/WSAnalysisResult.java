// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.badthings;

import java.text.MessageFormat;

import org.eclipse.jdt.core.IMethod;

class WSAnalysisResult {

    private final IMethod nukeTarget;
    private final IMethod nameSwitchTarget;

    public WSAnalysisResult( IMethod nukeTarget, IMethod nameSwitchTarget ) {
        this.nukeTarget = nukeTarget;
        this.nameSwitchTarget = nameSwitchTarget;
    }

    boolean canNuke() {
        return nukeTarget != null && nameSwitchTarget != null;
    }

    IMethod getNukeTarget() {
        return nukeTarget;
    }

    IMethod getNameSwitchTarget() {
        return nameSwitchTarget;
    }

    @Override
    public String toString() {
        String pattern = "- Can nuke: {0} - {1} => {2}";
        Boolean canNuke = Boolean.valueOf( canNuke() );
        return MessageFormat.format( pattern, canNuke, nukeTarget, nameSwitchTarget );
    }
}
