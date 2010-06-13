package org.projectusus.core.internal.proportions.rawdata;


public interface MetricsResultVisitor {

    void inspect( ProjectRawData projectRawData );

    void inspect( FileRawData fileRawData );

    void inspect( ClassRawData classRawData );

    void inspect( MethodRawData methodRawData );

    JavaModelPath getPath();
}
