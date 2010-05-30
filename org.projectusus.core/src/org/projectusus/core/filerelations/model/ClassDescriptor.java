package org.projectusus.core.filerelations.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.ClassDescriptorCleanup;

public class ClassDescriptor {

    private static Map<ClassDescriptorKey, ClassDescriptor> classes = new HashMap<ClassDescriptorKey, ClassDescriptor>();

    private ClassDescriptorKey key;

    protected final Set<FileRelation> outgoingRelations; // this == source
    protected final Set<FileRelation> incomingRelations; // this == target

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
        this.incomingRelations = new HashSet<FileRelation>();
        this.outgoingRelations = new HashSet<FileRelation>();
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
        return key.packagename + "." + key.classname + "[" + key.file.getName() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    private void addOutgoingRelation( FileRelation fileRelation ) {
        outgoingRelations.add( fileRelation );
        fileRelation.getTargetDescriptor().incomingRelations.add( fileRelation );
        clearOwnAndParentsTransitiveChildrenCache();
    }

    private void clearOwnAndParentsTransitiveChildrenCache() {
        if( !transitiveChildrenCache.isEmpty() ) {
            transitiveChildrenCache.clear();
            for( ClassDescriptor parent : getParents() ) {
                parent.clearOwnAndParentsTransitiveChildrenCache();
            }
        }
    }

    public Set<ClassDescriptor> getChildren() {
        Set<ClassDescriptor> result = new HashSet<ClassDescriptor>();
        for( FileRelation relation : outgoingRelations ) {
            result.add( relation.getTargetDescriptor() );
        }
        return result;
    }

    public Set<ClassDescriptor> getChildrenInOtherPackages() {
        Set<ClassDescriptor> result = new HashSet<ClassDescriptor>();
        for( FileRelation relation : outgoingRelations ) {
            ClassDescriptor target = relation.getTargetDescriptor();
            if( !this.getPackagename().equals( target.getPackagename() ) ) {
                result.add( target );
            }
        }
        return result;
    }

    private Set<ClassDescriptor> getParents() {
        Set<ClassDescriptor> result = new HashSet<ClassDescriptor>();
        for( FileRelation relation : incomingRelations ) {
            result.add( relation.getSourceDescriptor() );
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
        for( ClassDescriptor parent : getParents() ) {
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

    private void removeOutgoingRelation( FileRelation fileRelation ) {
        outgoingRelations.remove( fileRelation );
    }

    private void removeIncomingRelation( FileRelation fileRelation ) {
        incomingRelations.remove( fileRelation );
    }

    public void prepareRemoval() {
        markAndRemoveAllOutgoingRelations();
        ClassDescriptorCleanup.registerForCleanup( this );
    }

    private void markAndRemoveAllOutgoingRelations() {
        removeAll( new HashSet<FileRelation>( outgoingRelations ) );
        clearOwnAndParentsTransitiveChildrenCache();
    }

    public void removeFromPool() {
        clearOwnAndParentsTransitiveChildrenCache();
        removeAll( incomingRelations );
        // eigentlich auch für die outgoings, aber wir werden nur aufgerufen, wenn die outgoings schon leer sind
        removeDescriptor( this.key );
    }

    private static void removeAll( Set<FileRelation> relationsToRemove ) {
        for( FileRelation relation : relationsToRemove ) {
            relation.remove();
            relation.getSourceDescriptor().removeOutgoingRelation( relation );
            relation.getTargetDescriptor().removeIncomingRelation( relation );
        }
    }

    public void addChild( ClassDescriptor target ) {
        addOutgoingRelation( FileRelation.of( this, target ) );
    }
}
