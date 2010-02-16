// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugreport;

import static org.projectusus.ui.internal.util.ISharedUsusImages.WIZARD_REPORT_BUG;
import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import org.eclipse.jface.wizard.Wizard;
import org.projectusus.core.internal.bugreport.Bug;

public class ReportBugWizard extends Wizard {

    private Bug bug;
    private BugPage bugPage;

    public ReportBugWizard( Bug bug ) {
        this.bug = bug;
        setHelpAvailable( false );
        setDefaultPageImageDescriptor( getSharedImages().getDescriptor( WIZARD_REPORT_BUG ) );
    }

    @Override
    public void addPages() {
        bugPage = new BugPage( bug );
        addPage( bugPage );
    }

    @Override
    public boolean performFinish() {
        this.bug = bugPage.getBug();
        return true;
    }

    public Bug getBug() {
        return bug;
    }

}
