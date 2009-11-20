package org.projectusus.core.internal.proportions.sqi;

public enum CodeProportionUnit {

    METHOD( "methods" ), CLASS( "classes" ), PACKAGE( "packages" ), FILE( "files" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    private final String label;

    private CodeProportionUnit( String label ) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
