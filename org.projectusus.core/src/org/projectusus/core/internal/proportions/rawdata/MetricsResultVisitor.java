package org.projectusus.core.internal.proportions.rawdata;

public interface MetricsResultVisitor {

    void inspect( ProjectRawData projectRawData );

    void inspect( FileRawData fileRawData );

    void inspect( SourceCodeLocation location, ClassRawData classRawData );

    void inspect( SourceCodeLocation location, MethodRawData methodRawData );

    JavaModelPath getPath();
}
