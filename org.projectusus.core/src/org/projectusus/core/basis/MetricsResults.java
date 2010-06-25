package org.projectusus.core.basis;

import java.util.HashMap;
import java.util.Map;

public class MetricsResults {

    public static final String METHODS = "NumberOfMethods"; //$NON-NLS-1$
    public static final String CCD = "CCD"; //$NON-NLS-1$
    public static final String CC = "CyclomaticComplexity"; //$NON-NLS-1$
    public static final String ML = "MethodLength"; //$NON-NLS-1$

    private Map<String, Integer> results = new HashMap<String, Integer>();

    public void add( String key, Integer value ) {
        results.put( key, value );
    }

    public Integer get( String key ) {
        return results.get( key );
    }

    public int getIntValue( String key ) {
        return getIntValue( key, 0 );
    }

    public int getIntValue( String key, int defaultValue ) {
        Integer result = get( key );
        if( result == null ) {
            return defaultValue;
        }
        return result.intValue();
    }

}
