package org.projectusus.ui.dependencygraph.common;

import static org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_CLASS;
import static org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PACKAGE;
import static org.eclipse.jdt.ui.JavaElementLabels.P_COMPRESSED;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.JavaElementLabels;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.internal.proportions.rawdata.PackageRepresenter;
import org.projectusus.core.util.Defect;

public class NodeLabelProvider extends LabelProvider {

    @Override
    public String getText( Object element ) {
        if( element instanceof GraphNode ) {
            return getText( (GraphNode)element );
        }
        if( element instanceof EntityConnectionData ) {
            return getText( (EntityConnectionData)element );
        }
        throw new Defect( "Type not supported: " + element.getClass().toString() );
    }

    private String getText( EntityConnectionData data ) {
        GraphNode dest = (GraphNode)data.dest;
        return dest.getEdgeEndLabel();
    }

    private String getText( GraphNode node ) {
        if( node.isPackage() ) {
            return getText( ((PackageRepresenter)node).getNodeJavaElement() );
        }
        return node.getNodeName();
    }

    public String getText( IJavaElement javaElement ) {
        return JavaElementLabels.getTextLabel( javaElement, P_COMPRESSED );
    }

    @Override
    public Image getImage( Object element ) {
        if( element instanceof GraphNode ) {
            return getImage( (GraphNode)element );
        }
        return null;
    }

    private Image getImage( GraphNode node ) {
        String imageName = node.isPackage() ? IMG_OBJS_PACKAGE : IMG_OBJS_CLASS;
        return JavaUI.getSharedImages().getImage( imageName );
    }
}
