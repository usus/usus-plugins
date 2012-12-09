// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import static org.projectusus.core.basis.LocationType.PATH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.PlatformObject;

public class CodeProportion extends PlatformObject {

    private final String label;
    private final String description;
    private final int violations;
    private final CodeStatistic basis;
    private final double average;
    private final List<Hotspot> hotspots;
    private final boolean hasHotspots;
    private final String tooltip;
    private final Histogram histogram;
    private final LocationType locationType;

    public CodeProportion( String label, String description, int violations, CodeStatistic basis, double average ) {
        this( label, description, "", violations, basis, average, new ArrayList<Hotspot>( 0 ), false, new Histogram(), PATH ); //$NON-NLS-1$
    }

    public CodeProportion( String label, String description, String tooltip, int violations, CodeStatistic basis, double average, List<Hotspot> hotspots, Histogram histogram,
            LocationType locationType ) {
        this( label, description, tooltip, violations, basis, average, hotspots, true, histogram, locationType );
    }

    private CodeProportion( String label, String description, String tooltip, int violations, CodeStatistic basis, double average, List<Hotspot> hotspots, boolean hasHotspots,
            Histogram histogram, LocationType locationType ) {
        this.label = label;
        this.description = label + ": " + description;
        this.tooltip = tooltip;
        this.violations = violations;
        this.basis = basis;
        this.hasHotspots = hasHotspots;
        this.histogram = histogram;
        this.hotspots = sort( hotspots );
        this.average = average;
        this.locationType = locationType;
    }

    public double getAverage() {
        return average;
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

    public String getMetricDescription() {
        return description;
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

    public String getToolTipText() {
        return tooltip;
    }

    public Histogram getHistogram() {
        return histogram;
    }

    public LocationType getLocationType() {
        return locationType;
    }
}
