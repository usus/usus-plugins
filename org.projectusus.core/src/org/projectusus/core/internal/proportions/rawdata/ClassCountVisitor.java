package org.projectusus.core.internal.proportions.rawdata;

public class ClassCountVisitor extends DefaultMetricsResultVisitor {

    private int classCount = 0;

    public ClassCountVisitor( JavaModelPath path ) {
        super( path );
    }

    @Override
    public void inspect( @SuppressWarnings( "unused" ) ClassRawData classRawData ) {
        classCount++;
    }

    public int getClassCount() {
        visit();
        return classCount;
    }

}
