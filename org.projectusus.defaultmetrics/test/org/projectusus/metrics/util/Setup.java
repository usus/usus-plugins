package org.projectusus.metrics.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.projectusus.metrics.util.TypeBindingMocker.createFile;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.metrics.MetricsCollector;
import org.projectusus.core.statistics.UsusModelProvider;

public class Setup {

    public static <T extends AbstractTypeDeclaration> T setupCollectorAndMockFor( MetricsCollector collector, Class<T> type, String name ) {
        setupCollector( collector );
        return setupMockFor( type, name );
    }

    public static void setupCollector( MetricsCollector collector ) {
        collector.setup( createFile(), UsusModelProvider.getMetricsWriter() );
    }

    public static <T extends AbstractTypeDeclaration> T setupMockFor( Class<T> type, String name ) {
        T node = mock( type );
        Setup.addNameTo( node, name );
        return node;
    }

    public static void addNameTo( AbstractTypeDeclaration theNode, String nodename ) {
        SimpleName name = mock( SimpleName.class );
        when( name.toString() ).thenReturn( nodename );
        when( theNode.getName() ).thenReturn( name );
    }

    public static <T extends AbstractTypeDeclaration> T setupMockWithStartPosition( Class<T> type, String name, int startPosition, ASTNodeHelper nodeHelper ) {
        T node = setupMockFor( type, name );
        when( Integer.valueOf( nodeHelper.getStartPositionFor( node ) ) ).thenReturn( Integer.valueOf( startPosition ) );
        return node;
    }

}
