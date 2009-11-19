package org.projectusus.core.internal.bugreport;

import java.util.ArrayList;

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
