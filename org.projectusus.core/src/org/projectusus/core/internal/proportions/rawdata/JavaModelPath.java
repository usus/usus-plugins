package org.projectusus.core.internal.proportions.rawdata;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class JavaModelPath {

    private IProject project;
    private IFile file;
    private IType type;
    private IMethod method;

    public static JavaModelPath of( IJavaElement element ) throws JavaModelException {
        IJavaElement ancestor = element.getAncestor( IJavaElement.METHOD );
        if( ancestor != null ) {
            return new JavaModelPath( (IMethod)ancestor );
        }
        ancestor = element.getAncestor( IJavaElement.TYPE );
        if( ancestor != null ) {
            return new JavaModelPath( (IType)ancestor );
        }
        return new JavaModelPath( (IFile)element.getUnderlyingResource() );
    }

    public JavaModelPath() {
        super();
    }

    /**
     * @param project
     *            must not be null.
     */
    public JavaModelPath( IProject project ) {
        this.project = project;
    }

    /**
     * @param file
     *            must not be null.
     */
    public JavaModelPath( IFile file ) {
        this( file.getProject() );
        this.file = file;
    }

    /**
     * @param type
     *            must not be null.
     */
    public JavaModelPath( IType type ) throws JavaModelException {
        this( (IFile)type.getUnderlyingResource() );
        this.type = type;
    }

    /**
     * @param method
     *            must not be null.
     */
    public JavaModelPath( IMethod method ) throws JavaModelException {
        this( (IType)method.getAncestor( IJavaElement.TYPE ) );
        this.method = method;
    }

    public boolean isRestrictedToProject() {
        return project != null;
    }

    public IProject getProject() {
        return project;
    }

    public boolean isRestrictedToFile() {
        return file != null;
    }

    public IFile getFile() {
        return file;
    }

    public boolean isRestrictedToType() {
        return type != null;
    }

    public IJavaElement getType() {
        return type;
    }

    public boolean isRestrictedToMethod() {
        return method != null;
    }

    public IMethod getMethod() {
        return method;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder( this );
        builder.append( "Project", project ).append( "File", file ).append( "Type", type ).append( "Method", method ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        return builder.toString();
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof JavaModelPath && equals( (JavaModelPath)obj );
    }

    private boolean equals( JavaModelPath other ) {
        return new EqualsBuilder().append( project, other.project ).append( file, other.file ).append( type, other.type ).append( method, other.method ).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( project ).append( file ).append( type ).append( method ).toHashCode();
    }
}
