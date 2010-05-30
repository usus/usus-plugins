// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugprison.ui.internal;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.osgi.framework.Bundle;
import org.projectusus.bugprison.core.Bug;
import org.projectusus.bugprison.ui.BugPrisonUIPlugin;
import org.projectusus.core.util.Defect;

public class ReportBugWizard extends Wizard {

    private Bug bug;
    private BugPage bugPage;

    public ReportBugWizard( Bug bug ) {
        this.bug = bug;
        setHelpAvailable( false );
        setDefaultPageImageDescriptor( loadBanner() );
    }

	private ImageDescriptor loadBanner() {
		return ImageDescriptor.createFromURL( bannerUrl() );
	}

	private URL bannerUrl() {
		try {
			Bundle bundle = BugPrisonUIPlugin.getDefault().getBundle();
			return new URL( bundle.getEntry( "/" ), "icons/full/wizban/report_bug.png" );
		} catch (MalformedURLException exception) {
            throw new Defect("could not load bug banner", exception);
		}
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
