package org.projectusus.core.statistics;

public class CockpitExtensionPref implements Comparable<CockpitExtensionPref> {

    private final String name;
    private boolean on;

    public CockpitExtensionPref( String name, boolean on ) {
        super();
        this.name = name;
        this.on = on;
    }

    public String getName() {
        return name;
    }

    public boolean isOn() {
        return on;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void setOn( boolean checked ) {
        on = checked;
    }

    public int compareTo( CockpitExtensionPref other ) {
        return toString().compareTo( other.toString() );
    }
}
