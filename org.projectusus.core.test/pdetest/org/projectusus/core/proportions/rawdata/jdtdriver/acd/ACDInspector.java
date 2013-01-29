package org.projectusus.core.proportions.rawdata.jdtdriver.acd;

import java.util.ArrayList;
import java.util.List;

import org.projectusus.core.filerelations.model.WrappedTypeBinding;
import org.projectusus.metrics.ACDCollector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ACDInspector extends ACDCollector {

    private Multimap<String, String> typeConnections = ArrayListMultimap.create();
    private List<WrappedTypeBinding> types = new ArrayList<WrappedTypeBinding>();

    @Override
    public void addCurrentType( WrappedTypeBinding wrap ) {
        types.add( wrap );
        super.addCurrentType( wrap ); // we need the superclass behavior for operational consistency
    }

    @Override
    public void connectTypes( WrappedTypeBinding sourceType, WrappedTypeBinding targetType ) {
        typeConnections.put( pathOf( sourceType ), pathOf( targetType ) );
    }

    private String pathOf( WrappedTypeBinding aType ) {
        return aType.getPackagename().toString() + "/" + aType.getClassname().toString();
    }

    public Multimap<String, String> getTypeConnections() {
        return typeConnections;
    }

    public List<WrappedTypeBinding> getTypes() {
        return types;
    }
}
