package org.projectusus.core.statistics;

import java.util.Comparator;

final class CockpitExtensionPrefComparator implements Comparator<CockpitExtensionPref> {
    public int compare( CockpitExtensionPref first, CockpitExtensionPref second ) {
        return first.getLabel().compareTo( second.getLabel() );
    }
}
