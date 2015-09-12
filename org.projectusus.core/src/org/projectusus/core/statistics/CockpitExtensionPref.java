package org.projectusus.core.statistics;

import java.text.MessageFormat;

public class CockpitExtensionPref {

    public static final boolean ON_BY_DEFAULT = true;

    private final String className;
    private final String label;
    private boolean on;

    public CockpitExtensionPref( String className, String label ) {
        this( className, label, ON_BY_DEFAULT );
    }

    public CockpitExtensionPref( String className, boolean on ) {
        this( className, null, on );
    }

    protected CockpitExtensionPref( String className, String label, boolean on ) {
        super();
        this.className = className;
        this.label = label;
        this.on = on;
    }

    public String getLabel() {
        return label;
    }

    public String getClassName() {
        return className;
    }

    public boolean isOn() {
        return on;
    }

    @Override
    public String toString() {
        return MessageFormat.format( "{0} ({1}) [{2}]", label, className, onOrOff() ); //$NON-NLS-1$
    }

    private String onOrOff() {
        return on ? "on" : "off"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void setOn( boolean checked ) {
        on = checked;
    }

    public void resetOnToDefault() {
        setOn( ON_BY_DEFAULT );
    }

    public static void restoreDefaults( Iterable<CockpitExtensionPref> extensions ) {
        for( CockpitExtensionPref pref : extensions ) {
            pref.resetOnToDefault();
        }
    }

    @Override
    public boolean equals( Object obj ) {
        if( obj instanceof CockpitExtensionPref ) {
            CockpitExtensionPref other = (CockpitExtensionPref)obj;
            return this.className.equals( other.className );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }
}
