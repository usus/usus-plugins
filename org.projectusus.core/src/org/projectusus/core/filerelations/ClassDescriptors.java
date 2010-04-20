package org.projectusus.core.filerelations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.model.ClassDescriptor;

public class ClassDescriptors {

    private final Set<ClassDescriptor> classes;

    public ClassDescriptors() {
        classes = new HashSet<ClassDescriptor>();
    }

    public Set<ClassDescriptor> getAll() {
        return Collections.unmodifiableSet( classes );
    }

    public void add( ClassDescriptor classDescriptor ) {
        classes.add( classDescriptor );
    }

    public void removeAllClassesIn( IFile file ) {
        for( Iterator<ClassDescriptor> iterator = classes.iterator(); iterator.hasNext(); ) {
            if( iterator.next().getFile().equals( file ) ) {
                iterator.remove();
            }
        }
    }
}
