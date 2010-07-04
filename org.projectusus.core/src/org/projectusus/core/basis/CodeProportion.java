// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import static org.projectusus.core.basis.CodeProportionsRatio.computeInverse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.PlatformObject;

public class CodeProportion extends PlatformObject {

    private final int violations;
    private final CodeStatistic basis;
    private final double level;
    private final List<IHotspot> hotspots;
    private final boolean hasHotspots;
    private final String label;

    public CodeProportion( String label, int violations, CodeStatistic basis, List<IHotspot> hotspots, boolean hasHotspots ) {
        this( label, violations, basis, computeInverse( violations, basis ), hotspots, hasHotspots );
    }

    public CodeProportion( String label, int violations, CodeStatistic basis, double levelValue, List<IHotspot> hotspots, boolean hasHotspots ) {
        this.label = label;
        this.violations = violations;
        this.basis = basis;
        this.hasHotspots = hasHotspots;
        this.hotspots = sort( hotspots );
        this.level = levelValue;
    }

    public double getLevel() {
        return level;
    }

    public int getViolations() {
        return violations;
    }

    public CodeStatistic getBasis() {
        return basis;
    }

    @Override
    public String toString() {
        return label + ": " + violations + " / " + basis; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public String getMetricLabel() {
        return label;
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

    public boolean hasHotspots() {
        return hasHotspots;
    }
}
