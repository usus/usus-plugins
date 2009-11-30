package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.List;

public class AcdModel {

    // TODO Hashtable verwenden??
    List<AdjacencyNode> classes = new ArrayList<AdjacencyNode>();

    public void addClassReference( String referencingClass, String referencedClass ) {
        AdjacencyNode startNode = findOrAdd( referencingClass );
        AdjacencyNode endNode = findOrAdd( referencedClass );

        if( (startNode != endNode) && !startNode.getChildren().contains( endNode ) ) {
            startNode.getChildren().add( endNode );
        }
    }

    private AdjacencyNode findOrAdd( String id ) {
        for( AdjacencyNode node : classes ) {
            if( node.getName().equals( id ) ) {
                return node;
            }
        }
        return addNode( id );
    }

    private AdjacencyNode addNode( String id ) {
        AdjacencyNode newNode = new AdjacencyNode( id );
        classes.add( newNode );
        return newNode;
    }

    // / <summary>
    // / The relative ACD of a system with n components is ACD/n.
    // / Thus it is a percentage value in range [0%, 100%].
    // / </summary>
    // / <returns></returns>
    public double getRelativeACD() {
        if( classes.size() == 0 ) {
            return 0.0;
        }
        return getACD() / classes.size();
    }

    // / <summary>
    // / The average component dependency (ACD) of a system with n components is
    // CCD/n.
    // / </summary>
    // / <returns></returns>
    private double getACD() {
        if( classes.size() == 0 ) {
            return 0.0;
        }
        return getCCD() / (double)classes.size();
    }

    // / <summary>
    // / The cumulative component dependency (CCD) of a (sub)system is the sum
    // over all
    // / components Ci of the (sub)system of the number of components needed to
    // test each Ci incrementally.
    // / </summary>
    // / <returns></returns>
    private int getCCD() {
        int allDependencies = 0;
        for( AdjacencyNode node : classes ) {
            allDependencies += getCCD( node );
        }
        return allDependencies;
    }

    // / <summary>
    // / The cumulative component dependency (CCD) of a (sub)system is the sum
    // over all
    // / components Ci of the (sub)system of the number of components needed to
    // test each Ci incrementally.
    // / </summary>
    // / <param name="node">starting node for subsystem to analyze</param>
    // / <returns></returns>
    public int getCCD( AdjacencyNode node ) {
        markReferencedNodes( node );
        return getMarkedNodeCount();
    }

    public int getCCD( String fullyQualifiedName ) {
        AdjacencyNode node = findOrAdd( fullyQualifiedName );
        return getCCD( node );
    }

    private int getMarkedNodeCount() {
        int markedNodes = 0;
        for( AdjacencyNode node : classes ) {
            markedNodes += node.getCountAndClear();
        }
        return markedNodes;
    }

    // Knoten durch Einfuegen in Set markieren, damit Zaehlen + Loeschen in O(1) ??
    private void markReferencedNodes( AdjacencyNode node ) {
        if( node.isMarked() ) {
            return;
        }
        node.mark();
        for( AdjacencyNode childNode : node.getChildren() ) {
            markReferencedNodes( childNode );
        }
    }
}
