package org.projectusus.core.filerelations.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.DefectFileRelations;

public class ClassDescriptor {

    private static Map<ClassDescriptorKey, ClassDescriptor> classes = new HashMap<ClassDescriptorKey, ClassDescriptor>();

    private ClassDescriptorKey key;

    protected final Set<FileRelation> outgoingRelations; // this == source
    protected final Set<FileRelation> incomingRelations; // this == target

    public static void clear() {
        classes = new HashMap<ClassDescriptorKey, ClassDescriptor>();
    }

    public static Set<ClassDescriptor> getAll() {
        return new HashSet<ClassDescriptor>( classes.values() );
    }

    public static ClassDescriptor of( BoundType type ) {
        return ClassDescriptor.of( new ClassDescriptorKey( type.getUnderlyingResource(), type.getClassname(), type.getPackagename() ) );
    }

    public static ClassDescriptor of( IFile file, Classname classname, Packagename packagename ) {
        return ClassDescriptor.of( new ClassDescriptorKey( file, classname, packagename ) );
    }

    private static ClassDescriptor of( ClassDescriptorKey key ) {
        if( classes.containsKey( key ) ) {
            return classes.get( key );
        }
        ClassDescriptor newClassDescriptor = new ClassDescriptor( key );
        classes.put( key, newClassDescriptor );
        return newClassDescriptor;
    }

    public static ClassDescriptor findFor( IFile file, Classname classname ) {
        for( ClassDescriptorKey key : classes.keySet() ) {
            if( key.file.equals( file ) && key.classname.equals( classname ) ) {
                return classes.get( key );
            }
        }
        return null;
    }

    public static void removeAllClassesIn( IFile file ) {
        Set<ClassDescriptorKey> keys = new HashSet<ClassDescriptorKey>( classes.keySet() );
        for( ClassDescriptorKey key : keys ) {
            if( key.file.equals( file ) ) {
                removeDescriptor( key );
            }
        }
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
        return key.packagename + "." + key.classname + "[" + key.file + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public void addOutgoingRelation( FileRelation fileRelation ) {
        outgoingRelations.add( fileRelation );
    }

    public void addIncomingRelation( FileRelation fileRelation ) {
        incomingRelations.add( fileRelation );
    }

    public Set<ClassDescriptor> getChildren() {
        Set<ClassDescriptor> result = new HashSet<ClassDescriptor>();
        for( FileRelation relation : outgoingRelations ) {
            result.add( relation.getTargetDescriptor() );
        }
        return result;
    }

    public Set<ClassDescriptor> getTransitiveChildren() {
        Set<ClassDescriptor> result = new HashSet<ClassDescriptor>();
        collectTransitiveChildren( result );
        return result;
    }

    private void collectTransitiveChildren( Set<ClassDescriptor> result ) {
        result.add( this );
        for( ClassDescriptor child : getChildren() ) {
            if( !result.contains( child ) ) {
                child.collectTransitiveChildren( result );
            }
        }
    }

    public int getCCD() {
        return getTransitiveChildren().size();
    }

    public Set<FileRelation> getTransitiveRelationsFrom() {
        Set<FileRelation> transitives = new HashSet<FileRelation>();
        getTransitiveRelationsFrom( transitives );
        return transitives;
    }

    private void getTransitiveRelationsFrom( Set<FileRelation> transitives ) {
        for( FileRelation relation : outgoingRelations ) {
            if( transitives.add( relation ) ) {
                relation.getTargetDescriptor().getTransitiveRelationsFrom( transitives );
            }
        }
    }

    public Set<FileRelation> getTransitiveRelationsTo() {
        Set<FileRelation> transitives = new HashSet<FileRelation>();
        getTransitiveRelationsTo( transitives );
        return transitives;
    }

    private void getTransitiveRelationsTo( Set<FileRelation> transitives ) {
        for( FileRelation relation : incomingRelations ) {
            if( transitives.add( relation ) ) {
                relation.getSourceDescriptor().getTransitiveRelationsTo( transitives );
            }
        }
    }

    public int getTransitiveParentCount() {
        return getTransitiveRelationsTo().size();
    }

    public void removeOutgoingRelation( FileRelation fileRelation ) {
        outgoingRelations.remove( fileRelation );
    }

    public void removeIncomingRelation( FileRelation fileRelation ) {
        incomingRelations.remove( fileRelation );
    }

    public void remove() {
        removeDescriptor( this.key );
        markAndRemoveAllOutgoingRelations();
        DefectFileRelations.registerForRepair( incomingRelations );
    }

    private void markAndRemoveAllOutgoingRelations() {
        for( FileRelation relation : outgoingRelations ) {
            relation.markAsObsolete();
            relation.getTargetDescriptor().removeIncomingRelation( relation );
        }
        outgoingRelations.clear();
    }

}
