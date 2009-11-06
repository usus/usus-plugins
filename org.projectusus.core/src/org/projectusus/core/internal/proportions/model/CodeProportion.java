// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.PlatformObject;
import org.projectusus.core.internal.proportions.sqi.IsisMetrics;

public class CodeProportion extends PlatformObject {

    private final IsisMetrics metric;
    private final int violations;
    private final int basis;
    private final double sqi;
    private final List<IHotspot> hotspots;

    public CodeProportion( IsisMetrics metric ) {
        this( metric, 0, 0, 0, new ArrayList<IHotspot>() );
    }

    public CodeProportion( IsisMetrics metric, int violations, int basis, double sqi, List<IHotspot> hotspots ) {
        this.metric = metric;
        this.violations = violations;
        this.basis = basis;
        this.sqi = sqi;
        this.hotspots = sort( hotspots );
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
        return hotspots;
    }

    private List<IHotspot> sort( List<IHotspot> passedHotspots ) {
        List<IHotspot> result = new ArrayList<IHotspot>();
        result.addAll( passedHotspots );
        Collections.sort( result, new ByHotnessComparator() );
        return result;
    }
}
