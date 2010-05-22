// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugreport.core.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.joda.time.DateTime;
import org.projectusus.bugreport.core.Bug;
import org.projectusus.bugreport.core.BugList;
import org.projectusus.bugreport.core.BugMetrics;
import org.projectusus.bugreport.core.MethodLocation;
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
        Bug result = new Bug();
        insertBugMetrics( node, result );
        insertLocationInfo( node, result );

        String title = loadStringValue( node, TITLE_TAG );
        DateTime reportTime = loadDateTime( node, REPORT_TIME_TAG );
        DateTime creationTime = loadDateTime( node, CREATION_TIME_TAG );

        result.setTitle( title );
        result.setReportTime( reportTime );
        result.setCreationTime( creationTime );

        return result;
    }

    private void insertLocationInfo( Node node, Bug result ) {
        MethodLocation location = result.getLocation();
        String packageName = loadStringValue( node, PACKAGE_NAME_TAG );
        String className = loadStringValue( node, CLASS_NAME_TAG );
        String methodName = loadStringValue( node, METHOD_NAME_TAG );
        location.setPackageName( packageName );
        location.setClassName( className );
        location.setMethodName( methodName );
    }

    private void insertBugMetrics( Node node, Bug bug ) {
        BugMetrics bugMetrics = new BugMetrics();

        int cyclomaticComplexity = loadInteger( node, CYCLOMATIC_COMPLEXITY_TAG );
        int methodLength = loadInteger( node, METHOD_LENGTH_TAG );
        int numberOfMethods = loadInteger( node, NUMBER_OF_METHODS_TAG );

        bugMetrics.setCyclomaticComplexity( cyclomaticComplexity );
        bugMetrics.setMethodLength( methodLength );
        bugMetrics.setNumberOfMethods( numberOfMethods );

        bug.setBugMetrics( bugMetrics );
    }

}
