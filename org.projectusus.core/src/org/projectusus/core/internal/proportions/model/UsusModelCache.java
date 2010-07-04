// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.projectusus.core.basis.CodeProportion;

public class UsusModelCache {
    private final Map<String, CodeProportion> isisMetricsValues;

    public UsusModelCache() {
        super();
        isisMetricsValues = new HashMap<String, CodeProportion>();
    }

    public void refresh( CodeProportion proportion ) {
        isisMetricsValues.put( proportion.getMetricLabel(), proportion );
    }

    public List<CodeProportion> getEntries() {
        List<CodeProportion> result = new ArrayList<CodeProportion>();
        result.addAll( isisMetricsValues.values() );
        sort( result, new ByIsisMetricsComparator() );
        return unmodifiableList( result );
    }

}
