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

import org.projectusus.core.ICodeProportions;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;

class CodeProportions implements ICodeProportions {

    private final Map<CodeProportionKind, CodeProportion> isisMetricsValues;

    CodeProportions() {
        isisMetricsValues = new HashMap<CodeProportionKind, CodeProportion>();
    }

    void refresh( CodeProportion proportion ) {
        isisMetricsValues.put( proportion.getMetric(), proportion );
    }

    public List<CodeProportion> getEntries() {
        List<CodeProportion> result = new ArrayList<CodeProportion>();
        result.addAll( isisMetricsValues.values() );
        sort( result, new ByIsisMetricsComparator() );
        return unmodifiableList( result );
    }

    public CodeProportion forKind( CodeProportionKind kind ) {
        return isisMetricsValues.get( kind );
    }
}
