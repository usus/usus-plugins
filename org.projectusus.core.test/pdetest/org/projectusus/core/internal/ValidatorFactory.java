package org.projectusus.core.internal;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class ValidatorFactory {

    public static CockpitExtension getClassValidator( final String resultName ) {
        return new CockpitExtension( "", 0 ) {
            @Override
            public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
                addResult( location, results.getIntValue( resultName ) );
            }
        };
    }

    public static CockpitExtension getMethodValidator( final String resultName ) {
        return new CockpitExtension( "", 0 ) {
            @Override
            public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
                addResult( location, results.getIntValue( resultName ) );
            }
        };
    }

}
