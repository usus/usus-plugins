package org.projectusus.core.filerelations;

import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.filerelations.model.ClassDescriptor;

public class ClassDescriptorCleanup {

    private static Set<ClassDescriptor> registeredForCleanup = new HashSet<ClassDescriptor>();

    public static void clear() {
        registeredForCleanup = new HashSet<ClassDescriptor>();
    }

    public static void registerForCleanup( ClassDescriptor classDescriptor ) {
        registeredForCleanup.add( classDescriptor );
    }

    public static Set<ClassDescriptor> extractDescriptorsRegisteredForCleanup() {
        Set<ClassDescriptor> relations = new HashSet<ClassDescriptor>( registeredForCleanup );
        registeredForCleanup.clear();
        return relations;
    }
}
