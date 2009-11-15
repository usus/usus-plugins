// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;

public class BugFileWriter {

    public BugFileWriter() {
        super();
    }

    public void writToFile( IFile file, BugList bugList ) {
        try {
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream( fos );
            o.writeObject( bugList );
            fos.close();
            ByteArrayInputStream inputStream = new ByteArrayInputStream( fos.toByteArray() );
            file.create( inputStream, false, new NullProgressMonitor() );
            inputStream.close();
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public BugList readFromFile( IFile file ) {
        InputStream fis = null;

        try {
            fis = file.getContents();
            ObjectInputStream o = new ObjectInputStream( fis );
            return (BugList)o.readObject();
        } catch( Exception e ) {
            e.printStackTrace();
        } finally {
            try {
                if( fis != null ) {
                    fis.close();
                }
            } catch( Exception e ) {
                e.printStackTrace();
            }
        }
        return new BugList();
    }

}
