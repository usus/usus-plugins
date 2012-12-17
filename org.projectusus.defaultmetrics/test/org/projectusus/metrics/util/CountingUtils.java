package org.projectusus.metrics.util;

import org.projectusus.core.statistics.visitors.ClassCountVisitor;
import org.projectusus.core.statistics.visitors.MethodCountVisitor;

public class CountingUtils {

    public static int getNumberOfClasses() {
        return new ClassCountVisitor().visitAndReturn().getClassCount();
    }

    public static int getNumberOfMethods() {
        return new MethodCountVisitor().visitAndReturn().getMethodCount();
    }

}
