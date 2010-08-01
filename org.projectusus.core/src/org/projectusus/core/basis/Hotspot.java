package org.projectusus.core.basis;

public interface Hotspot extends Comparable<Hotspot> {

    String getName();

    int getMetricsValue();

    String getPath();
}
