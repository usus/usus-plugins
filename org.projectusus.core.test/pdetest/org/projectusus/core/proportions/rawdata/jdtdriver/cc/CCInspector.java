package org.projectusus.core.proportions.rawdata.jdtdriver.cc;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.metrics.CCCollector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class CCInspector extends CCCollector {

    private Multimap<String, String> map = ArrayListMultimap.create();
    private List<String> names = new ArrayList<String>();
    private String currentName;

    public void init( MethodDeclaration node ) {
        currentName = node.getName().toString();
        names.add( currentName );
    }

    public void init( Initializer node ) {
        currentName = "initializer";
        names.add( currentName );
    }

    @Override
    public void calculate( ASTNode node, int amount ) {
        String amountSuffix = amount == 1 ? "" : " " + amount;
        map.put( currentName, node.getClass().getSimpleName() + amountSuffix );
    }

    public void commit( MethodDeclaration node ) {
    }

    public void commit( Initializer node ) {
    }

    public List<String> getNames() {
        return names;
    }

    public Multimap<String, String> getMap() {
        return map;
    }
}
