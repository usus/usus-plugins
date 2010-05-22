// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugprison.core.internal;

import java.util.ArrayList;

import org.projectusus.bugprison.core.Bug;
import org.projectusus.core.internal.XmlLoader;
import org.w3c.dom.Element;

public class LoadBugs extends XmlLoader<Bug> {

    private final String bugsFilename;

    public LoadBugs( String bugsFilename ) {
        this.bugsFilename = bugsFilename;
    }

    @Override
    protected String getFileName() {
        return bugsFilename;
    }

    @Override
    protected void read( ArrayList<Bug> result, Element rootElement ) {
        new BugFileReader( rootElement ).read( result );
    }

}
