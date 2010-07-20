package org.projectusus.ui.dependencygraph.common;

import static org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_CLASS;
import static org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PACKAGE;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.util.Defect;

public class NodeLabelProvider extends LabelProvider {

    @Override
    public String getText( Object element ) {
        if( element instanceof GraphNode ) {
            GraphNode node = (GraphNode)element;
            return node.getNodeName();
        }
        if( element instanceof EntityConnectionData ) {
            EntityConnectionData data = (EntityConnectionData)element;
            GraphNode dest = (GraphNode)data.dest;
            return dest.getEdgeEndLabel();
        }
        throw new Defect( "Type not supported: " + element.getClass().toString() );
    }

    @Override
    public Image getImage( Object element ) {
        if( element instanceof GraphNode ) {
            GraphNode node = (GraphNode)element;
            String imageName = node.isPackage() ? IMG_OBJS_PACKAGE : IMG_OBJS_CLASS;
            return JavaUI.getSharedImages().getImage( imageName );
        }
        return null;
    }
}
