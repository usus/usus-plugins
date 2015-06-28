package org.projectusus.ui.dependencygraph.common;

import static org.eclipse.zest.core.widgets.ZestStyles.CONNECTIONS_DOT;
import static org.eclipse.zest.core.widgets.ZestStyles.CONNECTIONS_SOLID;
import static org.projectusus.ui.colors.UsusColors.BLACK;
import static org.projectusus.ui.colors.UsusColors.DARK_GREY;
import static org.projectusus.ui.colors.UsusColors.DARK_RED;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.util.Defect;
import org.projectusus.ui.colors.EdgeColorProvider;
import org.projectusus.ui.colors.UsusColors;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;
import org.projectusus.ui.dependencygraph.nodes.PackageRepresenter;

import com.google.common.base.Supplier;

public class NodeLabelProvider extends LabelProvider implements IEntityStyleProvider, IEntityConnectionStyleProvider {

    private static final double DEFAULT_ZOOM = 1d;
    private double zoom = DEFAULT_ZOOM;
    private boolean highlightStrongConnections;
    private final Supplier<PackageRelations> packageRelationsSupplier;

    public NodeLabelProvider( Supplier<PackageRelations> packageRelationsSupplier ) {
        this.packageRelationsSupplier = packageRelationsSupplier;
    }

    private PackageRelations getPackageRelations() {
        return packageRelationsSupplier.get();
    }

    @Override
    public String getText( Object element ) {
        if( element instanceof GraphNode ) {
            return ((GraphNode)element).getDisplayText();
        }
        if( element instanceof EntityConnectionData ) {
            return getText( (EntityConnectionData)element );
        }
        throw new Defect( "Type not supported: " + element.getClass().toString() );
    }

    private String getText( EntityConnectionData data ) {
        return ((GraphNode)data.dest).getEdgeEndLabel();
    }

    @Override
    public Image getImage( Object element ) {
        if( element instanceof GraphNode ) {
            return JavaUI.getSharedImages().getImage( ((GraphNode)element).getImageName() );
        }
        return null;
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
        return null;
    }

    public Color getBorderColor( Object entity ) {
        return getSharedColors().getColor( BLACK );
    }

    public Color getBorderHighlightColor( Object entity ) {
        return null;
    }

    public int getBorderWidth( Object entity ) {
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
        return (saturation > 0.6 || brightness < 0.85);
    }

    public IFigure getTooltip( Object entity ) {
        if( entity instanceof GraphNode ) {
            Label label = new Label();
            label.setText( ((GraphNode)entity).getTooltipText() );
            return label;
        }
        return null;
    }

    public boolean fisheyeNode( Object entity ) {
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
        if( (src instanceof GraphNode) && dest instanceof GraphNode ) {
            return ((GraphNode)src).isInDifferentPackageThan( (GraphNode)dest );
        }
        return false;
    }

    public Color getColor( Object src, Object dest ) {
        // TODO aOSD Julia Refactor: IEdgeColorProvider als Parameter in Konstruktor und dorthin delegieren
        // Erstes if -> PackageGraphEdgeColorProvider. Restliche Zeilen -> ClassGraphEdgeColorProvider
        if( highlightStrongConnections && src instanceof PackageRepresenter && dest instanceof PackageRepresenter ) {
            EdgeColorProvider edgeColorProvider = new EdgeColorProvider( getPackageRelations() );
            return edgeColorProvider.getEdgeColor( getCrossLinkCount( (PackageRepresenter)src, (PackageRepresenter)dest ) );
        }
        if( areClassesInDifferentPackages( src, dest ) ) {
            return getSharedColors().getColor( DARK_RED );
        }
        return getSharedColors().getColor( DARK_GREY );
    }

    private int getCrossLinkCount( PackageRepresenter src, PackageRepresenter target ) {
        return getPackageRelations().getCrossLinkCount( src.getPackagename(), target.getPackagename() );
    }

    public Color getHighlightColor( Object src, Object dest ) {
        return null;
    }

    public int getLineWidth( Object src, Object dest ) {
        if( areClassesInDifferentPackages( src, dest ) ) {
            return zoomed( 2 );
        }
        return zoomed( 1 );
    }

    private int zoomed( int width ) {
        return (int)Math.round( zoom * width );
    }

    public void setZoom( double zoom ) {
        this.zoom = zoom;
    }

    public void resetZoom() {
        setZoom( DEFAULT_ZOOM );
    }

    // TODO aOSD Über Listener realisieren
    public void setHighlightStrongConnections( boolean highlightStrongConnections ) {
        this.highlightStrongConnections = highlightStrongConnections;

    }

}
