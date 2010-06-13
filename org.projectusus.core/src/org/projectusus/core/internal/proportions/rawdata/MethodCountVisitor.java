package org.projectusus.core.internal.proportions.rawdata;

public class MethodCountVisitor extends DefaultMetricsResultVisitor {

    private int methodCount = 0;

    public MethodCountVisitor() {
        super();
    }

    public MethodCountVisitor( JavaModelPath path ) {
        super( path );
    }

    @Override
    public void inspect( @SuppressWarnings( "unused" ) MethodRawData methodRawData ) {
        methodCount++;
    }

    public int getMethodCount() {
        visit();
        return methodCount;
    }

}
