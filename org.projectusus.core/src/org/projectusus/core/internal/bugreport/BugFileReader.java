// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.joda.time.DateTime;
import org.projectusus.core.internal.UsusXmlReader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class BugFileReader extends UsusXmlReader<Bug> implements BugXmlNames {

    public BugFileReader( Element rootElement ) {
        super( rootElement, BUG_TAG );
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

    @Override
    protected Bug readElement( Node node ) {
        String title = loadStringValue( node, TITLE_TAG );
        String packageName = loadStringValue( node, PACKAGE_NAME_TAG );
        String className = loadStringValue( node, CLASS_NAME_TAG );
        String methodName = loadStringValue( node, METHOD_NAME_TAG );
        DateTime reportTime = loadDateTime( node, REPORT_TIME_TAG );
        DateTime creationTime = loadDateTime( node, CREATION_TIME_TAG );

        BugMetrics bugMetrics = loadBugMetrics( node );

        Bug result = new Bug();
        result.setTitle( title );
        result.getLocation().setPackageName( packageName );
        result.getLocation().setClassName( className );
        result.getLocation().setMethodName( methodName );
        result.setReportTime( reportTime );
        result.setCreationTime( creationTime );
        result.setBugMetrics( bugMetrics );

        return result;
    }

    private BugMetrics loadBugMetrics( Node node ) {
        BugMetrics result = new BugMetrics();

        int cyclomaticComplexity = loadInteger( node, CYCLOMATIC_COMPLEXITY_TAG );
        int methodLength = loadInteger( node, METHOD_LENGTH_TAG );
        int numberOfMethods = loadInteger( node, NUMBER_OF_METHODS_TAG );

        result.setCyclomaticComplexity( cyclomaticComplexity );
        result.setMethodLength( methodLength );
        result.setNumberOfMethods( numberOfMethods );

        return result;
    }

}
