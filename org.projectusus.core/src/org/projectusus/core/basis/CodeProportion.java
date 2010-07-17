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
    private final List<Hotspot> hotspots;
    private final boolean hasHotspots;
    private final String label;

    public CodeProportion( String label, int violations, CodeStatistic basis, List<Hotspot> hotspots ) {
        this( label, violations, basis, computeInverse( violations, basis ), hotspots, true );
    }

    public CodeProportion( String label, int violations, CodeStatistic basis ) {
        this( label, violations, basis, computeInverse( violations, basis ), new ArrayList<Hotspot>(), false );
    }

    public CodeProportion( String label, int violations, CodeStatistic basis, double levelValue ) {
        this( label, violations, basis, levelValue, new ArrayList<Hotspot>(), false );
    }

    public CodeProportion( String label, int violations, CodeStatistic basis, double levelValue, List<Hotspot> hotspots ) {
        this( label, violations, basis, levelValue, hotspots, true );
    }

    private CodeProportion( String label, int violations, CodeStatistic basis, double levelValue, List<Hotspot> hotspots, boolean hasHotspots ) {
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

    public List<Hotspot> getHotspots() {
        return hotspots;
    }

    private List<Hotspot> sort( List<Hotspot> passedHotspots ) {
        List<Hotspot> result = new ArrayList<Hotspot>();
        result.addAll( passedHotspots );
        Collections.sort( result, new ByHotnessComparator() );
        return result;
    }

    public boolean hasHotspots() {
        return hasHotspots;
    }
}
