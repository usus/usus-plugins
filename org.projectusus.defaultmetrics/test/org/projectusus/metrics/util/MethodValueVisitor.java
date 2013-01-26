package org.projectusus.metrics.util;

import java.util.HashMap;
import java.util.Map;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class MethodValueVisitor extends DefaultMetricsResultVisitor {
    private final String valueName;
    private Map<String, Integer> valueMap = new HashMap<String, Integer>();

    public MethodValueVisitor( String valueName ) {
        this.valueName = valueName;
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        String name = location.getName();
        Integer value = valueMap.get( name );
        int newValue = results.getIntValue( valueName ) + (value == null ? 0 : value.intValue());
        valueMap.put( name, Integer.valueOf( newValue ) );
    }

    public Map<String, Integer> getValueMap() {
        return valueMap;
    }
}
