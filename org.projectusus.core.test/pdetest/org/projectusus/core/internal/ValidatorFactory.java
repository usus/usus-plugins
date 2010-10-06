package org.projectusus.core.internal;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class ValidatorFactory {

    public static DefaultCockpitExtension getClassValidator( final String resultName ) {
        return new DefaultCockpitExtension( "", 0 ) {
            @Override
            public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
                addResult( location, results.getIntValue( resultName ) );
            }
        };
    }

    public static DefaultCockpitExtension getMethodValidator( final String resultName ) {
        return new DefaultCockpitExtension( "", 0 ) {
            @Override
            public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
                addResult( location, results.getIntValue( resultName ) );
            }
        };
    }

}
