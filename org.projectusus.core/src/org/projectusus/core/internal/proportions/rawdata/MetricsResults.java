package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashMap;
import java.util.Map;

public class MetricsResults {

    public static final String METHODS = "NumberOfMethods"; //$NON-NLS-1$
    public static final String CCD = "CCD"; //$NON-NLS-1$
    public static final String CC = "CyclomaticComplexity"; //$NON-NLS-1$
    public static final String ML = "MethodLength"; //$NON-NLS-1$
    public static final String FILE = "File"; //$NON-NLS-1$

    private Map<String, Object> results = new HashMap<String, Object>();

    public void add( String key, Object value ) {
        results.put( key, value );
    }

    public Object get( String key ) {
        return results.get( key );
    }

}
