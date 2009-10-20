package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

public class Results<S, T extends IResults> implements IResults {

    private final ResultMapWrapper<S, T> wrapper;

    public Results() {
        wrapper = new ResultMapWrapper<S, T>();
    }

    public T getResults( S key, T newObject ) {
        return wrapper.getResults( key, newObject );
    }

    public int getResultCount() {
        return wrapper.getResultCount();
    }

    public void remove( S key ) {
        wrapper.remove( key );
    }

    public int getViolationCount( IsisMetrics metric ) {
        int violations = 0;
        for( T result : wrapper.getAllResults() ) {
            violations = violations + result.getViolationCount( metric );
        }
        return violations;
    }

    public int getViolationBasis( IsisMetrics metric ) {
        int basis = 0;
        for( T result : wrapper.getAllResults() ) {
            basis = basis + result.getViolationBasis( metric );
        }
        return basis;
    }

    public void getViolationNames( IsisMetrics metric, List<String> violations ) {
        for( T result : wrapper.getAllResults() ) {
            result.getViolationNames( metric, violations );
        }
    }

}
