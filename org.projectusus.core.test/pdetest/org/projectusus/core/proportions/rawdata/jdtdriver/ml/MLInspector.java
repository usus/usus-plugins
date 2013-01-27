package org.projectusus.core.proportions.rawdata.jdtdriver.ml;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.projectusus.metrics.MLCollector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MLInspector extends MLCollector {

    private static final String STATIC_INITIALIZER = "static initializer";
    private static final String INITIALIZER = "initializer";

    private Multimap<String, Integer> values = ArrayListMultimap.create();
    private Stack<String> names = new Stack<String>();

    @Override
    protected void init( MethodDeclaration node ) {
        initWithEmptyListFor( node.getName().toString() );
    }

    @Override
    protected void init( Initializer node ) {
        if( (node.getModifiers() & Modifier.STATIC) == 0 ) {
            initWithEmptyListFor( INITIALIZER );
        } else {
            initWithEmptyListFor( STATIC_INITIALIZER );
        }
    }

    @Override
    protected void calculate( List<?> statements ) {
        values.put( names.peek(), Integer.valueOf( statements.size() ) );
    }

    @Override
    protected void commit( MethodDeclaration node ) {
        names.pop();
    }

    @Override
    protected void commit( Initializer node ) {
        names.pop();
    }

    public Multimap<String, Integer> getValues() {
        return values;
    }

    private void initWithEmptyListFor( String name ) {
        names.push( name );
        values.putAll( names.peek(), new ArrayList<Integer>() );
    }
}
