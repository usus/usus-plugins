package org.projectusus.core.filerelations.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.c4j.ContractReference;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.util.SelectUnique;

import ch.akuhn.foreach.ForEach;

@ContractReference( contractClassName = "ClassDescriptorContract" )
public class ClassDescriptor {

    private static Map<ClassDescriptorKey, ClassDescriptor> classes = new HashMap<ClassDescriptorKey, ClassDescriptor>();

    protected final Set<ClassDescriptor> parents;
    protected final Set<ClassDescriptor> children;

    private final ClassDescriptorKey key;
    private final Set<ClassDescriptor> transitiveChildrenCache = new HashSet<ClassDescriptor>();

    public static void clear() {
        classes.clear();
    }

    public static Set<ClassDescriptor> getAll() {
        return new HashSet<ClassDescriptor>( classes.values() );
    }

    public static ClassDescriptor of( WrappedTypeBinding type ) {
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
        key.getPackagename().addClass( this );
    }

    private void removeFromPackage() {
        key.getPackagename().removeClass( this );
    }

    public IFile getFile() {
        return key.getFile();
    }

    public Classname getClassname() {
        return key.getClassname();
    }

    public Packagename getPackagename() {
        return key.getPackagename();
    }

    @Override
    public boolean equals( Object object ) {
        // equality does not work! file can change...
        return object instanceof ClassDescriptor && equals( (ClassDescriptor)object );
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    private boolean equals( ClassDescriptor other ) {
        return key.equals( other.key );
    }

    @Override
    public String toString() {
        return qualifiedClassName() + "[" + key.getFile().getName() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public String qualifiedClassName() {
        return key.getPackagename() + "." + key.getClassname(); //$NON-NLS-1$ 
    }

    private void clearOwnAndParentsTransitiveChildrenCache() {
        if( transitiveChildrenCache.isEmpty() ) {
            return;
        }

        transitiveChildrenCache.clear();
        for( ClassDescriptor parent : parents ) {
            parent.clearOwnAndParentsTransitiveChildrenCache();
        }
    }

    public Set<ClassDescriptor> getChildren() {
        return Collections.unmodifiableSet( children );
    }

    public Set<ClassDescriptor> getParents() {
        return Collections.unmodifiableSet( parents );
    }

    public Set<ClassDescriptor> getChildrenInOtherPackages() {
        for( SelectUnique<ClassDescriptor> target : Query.with( new SelectUnique<ClassDescriptor>(), children ) ) {
            target.yield = !this.getPackagename().equals( target.value.getPackagename() );
        }
        return ForEach.result();
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
        if( !this.equals( target ) ) {
            children.add( target );
            target.parents.add( this );
            clearOwnAndParentsTransitiveChildrenCache();
        }
    }

    public Set<ClassDescriptor> getParentsInOtherPackages() {
        Set<ClassDescriptor> result = new HashSet<ClassDescriptor>();
        for( ClassDescriptor parent : parents ) {
            if( !this.getPackagename().equals( parent.getPackagename() ) ) {
                result.add( parent );
            }
        }
        return result;
    }
}
