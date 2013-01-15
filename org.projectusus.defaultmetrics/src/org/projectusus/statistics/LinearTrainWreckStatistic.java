package org.projectusus.statistics;

import java.util.List;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class LinearTrainWreckStatistic extends CockpitExtension {

    public static final int TRAINWRECK_LIMIT = 0;

    private double linearViolations = 0.0;

    public LinearTrainWreckStatistic() {
        super( codeProportionUnit_METHOD_label, TRAINWRECK_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        List<Integer> trainWreckValues = valueForMethod( results );
        int trainWreckValue = calcTotalValue( trainWreckValues );
        addResult( location, trainWreckValue );
        int exceedingTrainWrecks = trainWreckValue - TRAINWRECK_LIMIT;
        if( exceedingTrainWrecks > 0 ) {
            // linearViolations += ((double)exceedingTrainWrecks / TRAINWRECK_LIMIT);
            linearViolations += exceedingTrainWrecks;
        }
    }

    private int calcTotalValue( List<Integer> trainWreckValues ) {
        int result = 0;
        for( Integer wreckCount : trainWreckValues ) {
            result = addWreckCountSquareIfGreaterThan2( result, wreckCount.intValue() );
        }
        return result;
    }

    @SuppressWarnings( "unchecked" )
    public List<Integer> valueForMethod( MetricsResults results ) {
        return (List<Integer>)results.get( MetricsResults.TRAIN_WRECKS );
    }

    private int addWreckCountSquare( int result, int wreckCount ) {
        return result + wreckCount * wreckCount;
    }

    private int addWreckCountSquareIfGreaterThan2( int result, int wreckCount ) {
        if( wreckCount > 2 ) {
            return result + wreckCount * wreckCount;
        }
        return result;
    }

    private int addWreckCount( int result, int wreckCount ) {
        return result + wreckCount;
    }

    private int multiplyByWreckCount( int result, int wreckCount ) {
        if( result == 0 ) {
            result = 1;
        }
        return result * wreckCount;
    }

    private int multiplyByWreckCountIfGreaterThan2( int result, int wreckCount ) {
        if( wreckCount > 2 ) {
            if( result == 0 ) {
                result = 1;
            }
            return result * wreckCount;
        }
        return 1;
    }

    @Override
    public double getAverage() {
        return calculateAverage( linearViolations, getBasis() );
    }

    @Override
    public String getLabel() {
        return "Train Wrecks"; //$NON-NLS-1$
    }

    @Override
    protected String getTooltip() {
        return "The underlying metric determines the number of \"train wrecks\" (i.e. chained method invocations) in the method body.\n" //$NON-NLS-1$
                + "A method body without train wrecks has a value of 0. Each occurrence of a dot between two method invocations\n" //$NON-NLS-1$
                + "increases this number by 1.\n" + getDescription(); //$NON-NLS-1$
    }

    @Override
    protected String hotspotsAreUnits() {
        return format( "with a train wreck limit greater than %d.", TRAINWRECK_LIMIT );
    }
}
