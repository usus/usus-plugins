package org.projectusus.core.util;

/**
 * To be thrown in places where e.g. other exceptions are caught but actually should never occur. This usually indicates a defect, i.e. a programming error.
 */
public class Defect extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public Defect( String message ) {
        super( message );
    }

    public Defect( Throwable cause ) {
        super( cause );
    }

    public Defect( String message, Throwable cause ) {
        super( message, cause );
    }
}
