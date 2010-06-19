package org.projectusus.core.internal.proportions.rawdata;

public class SourceCodeLocation {

    private final int startPosition;
    private final String name;
    private final int lineNumber;

    public SourceCodeLocation( String name, int startPosition, int lineNumber ) {
        this.name = name;
        this.startPosition = startPosition;
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public int getSourcePosition() {
        return startPosition;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
