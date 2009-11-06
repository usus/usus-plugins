// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate.checkpoints;

import static org.projectusus.core.internal.proportions.modelupdate.checkpoints.XmlNames.CHECKPOINTS_FILE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.runtime.IPath;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.modelupdate.ICheckpoint;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class LoadCheckpoints {

    public List<ICheckpoint> load() {
        ArrayList<ICheckpoint> result = new ArrayList<ICheckpoint>();
        Reader reader = null;
        try {
            reader = loadCheckpoints( result );
        } catch( Exception ex ) {
            result.clear();
            // not directly displayed to the user, but into the error log
            String msg = "Unable to load code proportions checkpoints."; //$NON-NLS-1$
            UsusCorePlugin.log( msg, ex );
        } finally {
            safeClose( reader );
        }
        return result;
    }

    // internal
    // ////////

    private Reader loadCheckpoints( ArrayList<ICheckpoint> result ) throws Exception {
        Reader reader = null;
        File jioFile = new File( computeFileName() );
        if( jioFile.exists() ) {
            reader = readCheckpoints( result );
        }
        return reader;
    }

    private Reader readCheckpoints( ArrayList<ICheckpoint> result ) throws Exception {
        Reader reader = new InputStreamReader( new FileInputStream( computeFileName() ) );
        DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Element rootElement = parser.parse( new InputSource( reader ) ).getDocumentElement();
        new CheckpointReader( rootElement ).read( result );
        return reader;
    }

    private void safeClose( Reader reader ) {
        if( reader != null ) {
            try {
                reader.close();
            } catch( IOException ioex ) {
                // we can't help it then
            }
        }
    }

    private String computeFileName() {
        IPath stateLocation = getStateLocation().append( CHECKPOINTS_FILE );
        return stateLocation.toOSString();
    }

    private IPath getStateLocation() {
        return UsusCorePlugin.getDefault().getStateLocation();
    }
}
