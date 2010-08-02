package org.projectusus.core.filerelations.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;

public class ClassDescriptor {

    private static Map<ClassDescriptorKey, ClassDescriptor> classes = new HashMap<ClassDescriptorKey, ClassDescriptor>();

    protected final Set<ClassDescriptor> parents;
    protected final Set<ClassDescriptor> children;

    private ClassDescriptorKey key;
    private Set<ClassDescriptor> transitiveChildrenCache = new HashSet<ClassDescriptor>();

    public static void clear() {
        classes.clear();
    }

    public static Set<ClassDescriptor> getAll() {
        return new HashSet<ClassDescriptor>( classes.values() );
    }

    public static ClassDescriptor of( BoundType type ) {
        return ClassDescriptor.of( new ClassDescriptorKey( type ) );
    }

    public static ClassDescriptor of( IFile file, Classname classname, Packagename packagename ) {
        return ClassDescriptor.of( new ClassDescriptorKey( file, classname, packagename ) );
    }

    private static ClassDescriptor of( ClassDescriptorKey key ) {
        if( classes.containsKey( key ) ) {
            return classes.get( key );
        }
        return newClassDescriptor( key );
    }

    private static ClassDescriptor newClassDescriptor( ClassDescriptorKey key ) {
        ClassDescriptor newClassDescriptor = new ClassDescriptor( key );
        classes.put( key, newClassDescriptor );
        return newClassDescriptor;
    }

    private static void removeDescriptor( ClassDescriptorKey key ) {
        ClassDescriptor descriptor = classes.remove( key );
        if( descriptor != null ) {
            descriptor.removeFromPackage();
        }
    }

    private ClassDescriptor( ClassDescriptorKey key ) {
        this.key = key;
        this.parents = new HashSet<ClassDescriptor>();
        this.children = new HashSet<ClassDescriptor>();
        key.packagename.addClass( this );
    }

    private void removeFromPackage() {
        key.packagename.removeClass( this );
    }

    public IFile getFile() {
        return key.file;
    }

    public Classname getClassname() {
        return key.classname;
    }

    public Packagename getPackagename() {
        return key.packagename;
    }

    @Override
    public boolean equals( Object object ) {
        // equality does not work! file can change...
        return object instanceof ClassDescriptor && equals( (ClassDescriptor)object );
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(). //
                append( key.file ). //
                append( key.classname ). //
                append( key.packagename ). //
                toHashCode();
    }

    private boolean equals( ClassDescriptor other ) {
        return new EqualsBuilder(). //
                append( key.file, other.key.file ). //
                append( key.classname, other.key.classname ). //
                append( key.packagename, other.key.packagename ). //
                isEquals();
    }

    @Override
    public String toString() {
        return qualifiedClassName() + "[" + key.file.getName() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public String qualifiedClassName() {
        return key.packagename + "." + key.classname; //$NON-NLS-1$ 
    }

    private void clearOwnAndParentsTransitiveChildrenCache() {
        if( !transitiveChildrenCache.isEmpty() ) {
            transitiveChildrenCache.clear();
            for( ClassDescriptor parent : parents ) {
                parent.clearOwnAndParentsTransitiveChildrenCache();
            }
        }
    }

    public Set<ClassDescriptor> getChildren() {
        return Collections.unmodifiableSet( children );
    }

    public Set<ClassDescriptor> getParents() {
        return Collections.unmodifiableSet( parents );
    }

    public Set<ClassDescriptor> getChildrenInOtherPackages() {
        Set<ClassDescriptor> result = new HashSet<ClassDescriptor>();
        for( ClassDescriptor target : children ) {
            if( !this.getPackagename().equals( target.getPackagename() ) ) {
                result.add( target );
            }
        }
        return result;
    }

    private Set<ClassDescriptor> getTransitiveChildren() {
        if( transitiveChildrenCache.isEmpty() ) {
            collectTransitiveChildren( transitiveChildrenCache );
        }
        return transitiveChildrenCache;
    }

    private void collectTransitiveChildren( Set<ClassDescriptor> result ) {
        result.add( this );
        for( ClassDescriptor child : getChildren() ) {
            if( !result.contains( child ) ) {
                child.collectTransitiveChildren( result );
            }
        }
    }

    private Set<ClassDescriptor> getTransitiveParents() {
        Set<ClassDescriptor> result = new HashSet<ClassDescriptor>();
        collectTransitiveParents( result );
        return result;
    }

    private void collectTransitiveParents( Set<ClassDescriptor> result ) {
        result.add( this );
        for( ClassDescriptor parent : parents ) {
            if( !result.contains( parent ) ) {
                parent.collectTransitiveParents( result );
            }
        }
    }

    public int getCCD() {
        return getTransitiveChildren().size();
    }

    public int getTransitiveParentCount() {
        return getTransitiveParents().size();
    }

    public void prepareRemoval() {
        markAndRemoveAllOutgoingRelations();
        ClassDescriptorCleanup.registerForCleanup( this );
    }

    private void markAndRemoveAllOutgoingRelations() {
        for( ClassDescriptor target : children ) {
            target.parents.remove( this );
        }
        children.clear();
        clearOwnAndParentsTransitiveChildrenCache();
    }

    public void removeFromPool() {
        clearOwnAndParentsTransitiveChildrenCache();
        for( ClassDescriptor source : parents ) {
            source.children.remove( this );
        }
        parents.clear();
        // eigentlich auch füŸr die outgoings, aber wir werden nur aufgerufen, wenn die outgoings schon leer sind
        removeDescriptor( this.key );
    }

    public void addChild( ClassDescriptor target ) {
        children.add( target );
        target.parents.add( this );
        clearOwnAndParentsTransitiveChildrenCache();
    }
}
