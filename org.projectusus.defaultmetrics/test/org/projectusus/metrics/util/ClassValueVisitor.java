package org.projectusus.metrics.util;

import java.util.HashMap;
import java.util.Map;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class ClassValueVisitor extends DefaultMetricsResultVisitor {
    private final String valueName;
    private int valueSum = 0;
    private String name;
    private Map<String, Integer> valueMap = new HashMap<String, Integer>();

    public ClassValueVisitor( String valueName ) {
        this.valueName = valueName;
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        name = location.getName();
        int intValue = results.getIntValue( valueName );
        valueSum += intValue;

        Integer value = valueMap.get( name );
        int newValue = intValue + (value == null ? 0 : value.intValue());
        valueMap.put( name, Integer.valueOf( newValue ) );
    }

    public int getValueSum() {
        return valueSum;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getValueMap() {
        return valueMap;
    }
}
