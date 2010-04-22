package org.projectusus.core.filerelations.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ITypeBinding;

public class ClassDescriptor {

    private IFile file;
    private Classname classname;
    private Packagename packagename;

    public ClassDescriptor( IFile file, Classname classname, Packagename packagename ) {
        this.file = file;
        this.classname = classname;
        this.packagename = packagename;
        packagename.addClass( this );
    }

    public ClassDescriptor( ITypeBinding binding ) throws JavaModelException {
        this( (IFile)binding.getJavaElement().getUnderlyingResource(), new Classname( binding.getName() ), PackagenameFactory.get( binding.getPackage().getName() ) );
    }

    public void destroy() {
        packagename.removeClass( this );
        this.file = null;
        this.classname = null;
        this.packagename = null;
    }

    public IFile getFile() {
        return file;
    }

    public Classname getClassname() {
        return classname;
    }

    public Packagename getPackagename() {
        return packagename;
    }

    @Override
    public boolean equals( Object object ) {
        return object instanceof ClassDescriptor && equals( (ClassDescriptor)object );
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(). //
                append( file ). //
                append( classname ). //
                append( packagename ). //
                toHashCode();
    }

    private boolean equals( ClassDescriptor other ) {
        return new EqualsBuilder(). //
                append( file, other.file ). //
                append( classname, other.classname ). //
                append( packagename, other.packagename ). //
                isEquals();
    }

    @Override
    public String toString() {
        return packagename + "." + classname + "[" + file + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
}
