package org.projectusus.core.proportions.rawdata.jdtdriver.trainwreck;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.projectusus.core.metrics.MetricsCollector;

public class TrainWreckInspector extends MetricsCollector {

    private Map<String, String> map = new HashMap<String, String>();
    private String currentName;
    private StringBuilder visitedNodes;

    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public boolean visit( MethodDeclaration node ) {
        init( node.getName().toString() );
        return true;
    }

    @Override
    public boolean visit( Initializer node ) {
        init( "initializer" );
        return true;
    }

    @Override
    public void endVisit( MethodDeclaration node ) {
        map.put( currentName, visitedNodes.toString() );
    }

    @Override
    public void endVisit( Initializer node ) {
        map.put( currentName, visitedNodes.toString() );
    }

    @Override
    public boolean visit( MethodInvocation node ) {
        visitedNodes.append( "MI " );
        visitedNodes.append( node.getName().toString() );
        visitedNodes.append( " " );
        if( node.getExpression() instanceof MethodInvocation ) {
            visitedNodes.append( "on " );
        } else {
            visitedNodes.append( "# " );
        }
        return true;
    }

    private void init( String name ) {
        currentName = name;
        visitedNodes = new StringBuilder();
    }

}
