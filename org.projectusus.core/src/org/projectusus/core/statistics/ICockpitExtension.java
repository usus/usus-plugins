package org.projectusus.core.statistics;

import org.projectusus.core.basis.CodeProportion;

public interface ICockpitExtension extends IMetricsResultVisitor {

    CodeProportion getCodeProportion();

    void visit();
}
