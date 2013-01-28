package org.projectusus.core.proportions.rawdata.jdtdriver.publicfields;

import java.util.Stack;

import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.metrics.PublicFieldCollector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class PublicFieldInspector extends PublicFieldCollector {

    private Multimap<String, Integer> map = ArrayListMultimap.create();
    private Stack<String> names = new Stack<String>();

    public Multimap<String, Integer> getMap() {
        return map;
    }

    public void init( TypeDeclaration node ) {
        String name = node.getName().toString();
        names.push( name );
    }

    public void calculate( int modifiers ) {
        map.put( names.peek(), Integer.valueOf( modifiers ) );
    }

    public void commit( TypeDeclaration node ) {
        names.pop();
    }
}
