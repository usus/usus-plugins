package org.projectusus.core.statistics;

import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.basis.CodeProportion;

/**
 * The results of raw data tree visitors can be displayed in the Usus Cockpit. In order to do this, the visitor needs to implement this interface and needs to be registered as
 * extension of the <code>org.projectusus.core.statistics</code> extension point.
 * <p>
 * The visitor's result must be returned as a <code>CodeProportion</code> object. This will be displayed in the cockpit.
 * 
 * @author Nicole Rauch
 * 
 */
public interface ICockpitExtension extends IMetricsResultVisitor {

    CodeProportion getCodeProportion();

    void visit();
}
