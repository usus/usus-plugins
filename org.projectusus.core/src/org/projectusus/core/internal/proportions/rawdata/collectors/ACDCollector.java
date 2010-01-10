package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ACDCollector extends Collector {

    private AbstractTypeDeclaration currentType;

    public ACDCollector( IFile file ) {
        super( file );
    }

    @Override
    public boolean visit( TypeDeclaration node ) {
        setCurrentType( node );
        return true;
    }

    @Override
    public boolean visit( AnnotationTypeDeclaration node ) {
        setCurrentType( node );
        return true;
    }

    @Override
    public boolean visit( EnumDeclaration node ) {
        setCurrentType( node );
        return true;
    }

    // referenced types

    @Override
    public boolean visit( ArrayType node ) {
        if( currentType == null ) {
            return true;
        }
        Type element = node.getElementType();
        if( element.isSimpleType() ) {
            visit( (SimpleType)element );
        }
        return true;
    }

    @Override
    public boolean visit( SimpleType node ) {
        if( currentType == null ) {
            return true;
        }
        ITypeBinding binding = node.resolveBinding();
        if( binding == null || !binding.isFromSource() ) {
            return true;
        }
        getFileRawData().addClassReference( currentType, binding.getJavaElement() );
        return true;
    }

    /**
     * QualifiedType -- it should not be necessary to treat it:
     * 
     * A type like "A.B" can be represented either of two ways:
     * <ol>
     * <li>
     * <code>QualifiedType(SimpleType(SimpleName("A")),SimpleName("B"))</code></li>
     * <li>
     * <code>SimpleType(QualifiedName(SimpleName("A"),SimpleName("B")))</code></li>
     * </ol>
     * 
     * Therefore, it should be sufficient to treat SimpleType.
     */

    private void setCurrentType( AbstractTypeDeclaration node ) {
        currentType = node;
    }

}
