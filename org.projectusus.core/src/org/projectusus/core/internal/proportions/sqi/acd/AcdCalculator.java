package org.projectusus.core.internal.proportions.sqi.acd;

import java.util.List;

public class AcdCalculator {
    private final List<ILinkedNode> nodes;

    public AcdCalculator( List<ILinkedNode> nodes ) {
        this.nodes = nodes;
    }

    // / <summary>
    // / The relative ACD of a system with n components is ACD/n.
    // / Thus it is a percentage value in range [0%, 100%].
    // / </summary>
    // / <returns></returns>
    public double getRelativeACD() {
        if( nodes.size() == 0 ) {
            return 0.0;
        }
        return getACD() / nodes.size();
    }

    // / <summary>
    // / The average component dependency (ACD) of a system with n components is
    // CCD/n.
    // / </summary>
    // / <returns></returns>
    public double getACD() {
        if( nodes.size() == 0 ) {
            return 0.0;
        }
        return getCCD() / (double)nodes.size();
    }

    // / <summary>
    // / The cumulative component dependency (CCD) of a (sub)system is the sum
    // over all
    // / components Ci of the (sub)system of the number of components needed to
    // test each Ci incrementally.
    // / </summary>
    // / <returns></returns>
    public int getCCD() {
        int allDependencies = 0;
        for( ILinkedNode node : nodes ) {
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
    public int getCCD( ILinkedNode node ) {
        clearAllNodes();
        markReferencedNodes( node );
        return getMarkedNodeCount();
    }

    private int getMarkedNodeCount() {
        int markedNodes = 0;
        for( ILinkedNode node : nodes ) {
            if( node.isMarked() ) {
                markedNodes++;
            }
        }
        return markedNodes;
    }

    private void markReferencedNodes( ILinkedNode node ) {
        if( node.isMarked() ) {
            return;
        }
        node.setMarked( true );
        for( ILinkedNode childNode : node.getChildren() ) {
            markReferencedNodes( childNode );
        }
    }

    private void clearAllNodes() {
        for( ILinkedNode node : nodes ) {
            node.setMarked( false );
        }
    }

}
