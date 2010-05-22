// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugprison.core.internal;

import static org.eclipse.core.runtime.Status.OK_STATUS;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.projectusus.bugprison.core.BugList;
import org.projectusus.bugprison.core.texts.BugPrisonTexts;
import org.projectusus.core.internal.UsusCorePlugin;

public class SaveBugsJob extends Job {

    public static final Object FAMILY = new Object();
    private final BugList bugs;
    private final IFile file;

    public SaveBugsJob( IFile file, BugList bugs ) {
        super( BugPrisonTexts.SaveBugsJob_title );
        this.file = file;
        this.bugs = bugs;
    }

    @Override
    protected IStatus run( IProgressMonitor monitor ) {
        try {
            String stringContent = generateFileContent();
            saveToFile( monitor, stringContent );
        } catch( CoreException cex ) {
            // not directly displayed to the user, but into the error log
            String msg = "Unable to save bugs."; //$NON-NLS-1$
            UsusCorePlugin.log( msg, cex );
        }
        return OK_STATUS;
    }

    private void saveToFile( IProgressMonitor monitor, String stringContent ) throws CoreException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream( stringContent.getBytes() );
        if( !file.exists() ) {
            file.create( inputStream, true, monitor );
        } else {
            file.setContents( inputStream, true, true, monitor );
        }
    }

    private String generateFileContent() {
        return new BugsWriter( bugs ).toXml();
    }

    @Override
    public boolean belongsTo( Object family ) {
        return family == FAMILY;
    }
}
