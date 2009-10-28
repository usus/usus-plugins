// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.PlatformObject;
import org.projectusus.core.internal.proportions.sqi.IsisMetrics;
import org.projectusus.core.internal.proportions.sqi.NewWorkspaceResults;
import org.projectusus.core.internal.proportions.sqi.ProjectResults;

public class CodeProportion extends PlatformObject {

    private final IsisMetrics metric;
    private final int violations;
    private final int basis;
    private final double sqi;

    public CodeProportion( IsisMetrics metric ) {
        this( metric, 0, 0, 0 );
    }

    public CodeProportion( IsisMetrics metric, int violations, int basis, double sqi ) {
        this.metric = metric;
        this.violations = violations;
        this.basis = basis;
        this.sqi = sqi;
    }

    public Double getSQIValue() {
        return new Double( sqi );
    }

    public int getViolations() {
        return violations;
    }

    public int getBasis() {
        return basis;
    }

    @Override
    public String toString() {
        return metric.toString() + ": " + violations + " / " + basis; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public IsisMetrics getMetric() {
        return metric;
    }

    public List<IHotspot> getHotspots() {
        Map<IFile, Integer> violations = new HashMap<IFile, Integer>();
        collectViolations( metric, violations );
        List<IHotspot> result = new ArrayList<IHotspot>();
        for( IFile file : violations.keySet() ) {
            result.add( new Hotspot( file, violations.get( file ) ) );
        }
        return result;
    }

    // internal
    // ////////

    private void collectViolations( IsisMetrics metric, Map<IFile, Integer> violations ) {
        Collection<ProjectResults> projectResults = NewWorkspaceResults.getInstance().getAllProjectResults();
        for( ProjectResults result : projectResults ) {
            Map<IFile, Integer> projectViolations = result.getViolationsForProject( metric );
            violations.putAll( projectViolations );
        }
    }
}
