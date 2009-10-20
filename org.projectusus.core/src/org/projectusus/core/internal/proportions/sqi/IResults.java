package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

public interface IResults {

    public int getViolationCount( IsisMetrics metric );

    public int getViolationBasis( IsisMetrics metric );

    public void getViolationNames( IsisMetrics metric, List<String> violations );

}
