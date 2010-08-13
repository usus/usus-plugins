package org.projectusus.ui.dependencygraph.common;

import static org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_CLASS;
import static org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PACKAGE;
import static org.eclipse.jdt.ui.JavaElementLabels.P_COMPRESSED;
import static org.eclipse.zest.core.widgets.ZestStyles.CONNECTIONS_DOT;
import static org.eclipse.zest.core.widgets.ZestStyles.CONNECTIONS_SOLID;
import static org.projectusus.ui.colors.UsusColors.BLACK;
import static org.projectusus.ui.colors.UsusColors.DARK_GREY;
import static org.projectusus.ui.colors.UsusColors.DARK_RED;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.JavaElementLabels;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.proportions.rawdata.ClassRepresenter;
import org.projectusus.core.util.Defect;
import org.projectusus.ui.colors.UsusColors;

public class NodeLabelProvider extends LabelProvider implements IEntityStyleProvider, IEntityConnectionStyleProvider {

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
            return getText( node.getNodeJavaElement() );
        }
        return node.getNodeName();
    }

    private String getText( IJavaElement javaElement ) {
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

    // From IEntityStyleProvider

    public Color getBackgroundColour( Object element ) {
        if( element instanceof GraphNode ) {
            Packagename packageName = ((GraphNode)element).getRelatedPackage();
            return UsusColors.getSharedColors().getNodeColorFor( packageName.hashCode() );
        }
        return null;
    }

    public Color getNodeHighlightColor( Object entity ) {
        // TODO Auto-generated method stub
        return null;
    }

    public Color getBorderColor( Object entity ) {
        return getSharedColors().getColor( BLACK );
    }

    public Color getBorderHighlightColor( Object entity ) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getBorderWidth( Object entity ) {
        // TODO Auto-generated method stub
        return 0;
    }

    public Color getForegroundColour( Object entity ) {
        if( isVeryDark( entity ) ) {
            return getSharedColors().getColor( UsusColors.WHITE );
        }
        return getSharedColors().getColor( BLACK );
    }

    private boolean isVeryDark( Object entity ) {
        float[] hsb = getBackgroundColour( entity ).getRGB().getHSB();
        float saturation = hsb[1];
        float brightness = hsb[2];
        return (saturation > 0.5 && brightness > 0.9);
    }

    public IFigure getTooltip( Object entity ) {
        if( entity instanceof GraphNode ) {
            Label label = new Label();
            GraphNode node = (GraphNode)entity;
            label.setText( getText( node.getNodeJavaElement() ) );
            return label;
        }
        return null;
    }

    public boolean fisheyeNode( Object entity ) {
        // TODO Auto-generated method stub
        return false;
    }

    // From IEntityConnectionStyleProvider

    public int getConnectionStyle( Object src, Object dest ) {
        if( areClassesInDifferentPackages( src, dest ) ) {
            return CONNECTIONS_SOLID;
        }
        return CONNECTIONS_DOT;
    }

    private boolean areClassesInDifferentPackages( Object src, Object dest ) {
        if( (src instanceof ClassRepresenter) && dest instanceof ClassRepresenter ) {
            ClassRepresenter srcClass = (ClassRepresenter)src;
            ClassRepresenter destClass = (ClassRepresenter)dest;
            return !srcClass.getPackagename().equals( destClass.getPackagename() );
        }
        return false;
    }

    public Color getColor( Object src, Object dest ) {
        if( areClassesInDifferentPackages( src, dest ) ) {
            return getSharedColors().getColor( DARK_RED );
        }
        return getSharedColors().getColor( DARK_GREY );
    }

    public Color getHighlightColor( Object src, Object dest ) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getLineWidth( Object src, Object dest ) {
        if( areClassesInDifferentPackages( src, dest ) ) {
            return 2;
        }
        return 1;
    }

}
