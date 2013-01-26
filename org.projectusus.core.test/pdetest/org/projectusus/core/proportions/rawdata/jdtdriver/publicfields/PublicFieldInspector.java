package org.projectusus.core.proportions.rawdata.jdtdriver.publicfields;

import java.util.Stack;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.metrics.MetricsCollector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class PublicFieldInspector extends MetricsCollector {

    private Multimap<String, Integer> map = ArrayListMultimap.create();
    private Stack<String> names = new Stack<String>();

    public Multimap<String, Integer> getMap() {
        return map;
    }

    @Override
    public boolean visit( TypeDeclaration node ) {
        String name = node.getName().toString();
        names.push( name );
        return true;
    }

    @Override
    public void endVisit( TypeDeclaration node ) {
        names.pop();
    }

    @Override
    public boolean visit( FieldDeclaration node ) {
        map.put( names.peek(), Integer.valueOf( node.getModifiers() ) );
        return true;
    }
}
