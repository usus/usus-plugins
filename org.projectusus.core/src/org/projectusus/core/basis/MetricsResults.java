package org.projectusus.core.basis;

import java.util.HashMap;
import java.util.Map;

public class MetricsResults {

    public static final String METHODS = "NumberOfMethods"; //$NON-NLS-1$
    public static final String CLASSES = "NumberOfClasses"; //$NON-NLS-1$
    public static final String CCD = "CCD"; //$NON-NLS-1$
    public static final String CC = "CyclomaticComplexity"; //$NON-NLS-1$
    public static final String ML = "MethodLength"; //$NON-NLS-1$
    public static final String CLASS_CREATION = "ClassCreation"; //$NON-NLS-1$
    public static final String ABSTRACTNESS = "Abstractness";
    public static final String PUBLIC_FIELDS = "public fields"; //$NON-NLS-1$
    public static final String TRAIN_WRECKS = "TrainWrecks";
    public static final String CP = "ConstantParameters";

    private Map<String, Object> results = new HashMap<String, Object>();

    public void put( String key, Object value ) {
        results.put( key, value );
    }

    public void put( String key, int value ) {
        results.put( key, Integer.valueOf( value ) );
    }

    public Object get( String key ) {
        return results.get( key );
    }

    public int getIntValue( String key ) {
        return getIntValue( key, 0 );
    }

    public int getIntValue( String key, int defaultValue ) {
        Integer result = (Integer)get( key );
        if( result == null ) {
            return defaultValue;
        }
        return result.intValue();
    }

}
