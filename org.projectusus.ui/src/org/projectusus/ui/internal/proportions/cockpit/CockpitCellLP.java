package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.colors.ISharedUsusImages.OBJ_INFO;
import static org.projectusus.ui.colors.UsusUIImages.getSharedImages;
import static org.projectusus.ui.internal.proportions.cockpit.CockpitColumnDesc.Indicator;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.IDisplayCategory;

public class CockpitCellLP extends ColumnLabelProvider {

    @Override
    public String getToolTipText( Object element ) {
        return "Tooltip (" + element + ")"; //$NON-NLS-1$
    }

    @Override
    public Point getToolTipShift( Object object ) {
        return new Point( 5, 5 );
    }

    @Override
    public int getToolTipDisplayDelayTime( Object object ) {
        return 2000;
    }

    @Override
    public int getToolTipTimeDisplayed( Object object ) {
        return 5000;
    }

    @Override
    public void update( ViewerCell cell ) {
        cell.setImage( getColumnImage( cell.getElement(), cell.getColumnIndex() ) );
        cell.setText( getColumnText( cell.getElement(), cell.getColumnIndex() ) );

    }

    // aus CockpitLP

    public Image getColumnImage( Object element, int columnIndex ) {
        CockpitColumnDesc cockpitColumnDesc = CockpitColumnDesc.values()[columnIndex];
        if( cockpitColumnDesc == CockpitColumnDesc.Trend && element instanceof AnalysisDisplayEntry ) {
            return ((AnalysisDisplayEntry)element).getTrendImage();
        }
        if( cockpitColumnDesc.hasImage() ) {
            return getColumnImageFor( element );
        }
        return null;
    }

    public String getColumnText( Object element, int columnIndex ) {
        if( element instanceof AnalysisDisplayEntry ) {
            return getColumnTextFor( (AnalysisDisplayEntry)element, columnIndex );
        } else if( CockpitColumnDesc.values()[columnIndex] == Indicator ) {
            return getNodeTextFor( element );
        }
        return null;
    }

    // internal methods
    // ////////////////

    private String getColumnTextFor( AnalysisDisplayEntry element, int columnIndex ) {
        return CockpitColumnDesc.values()[columnIndex].getLabel( element );
    }

    // aus UsusModelLabelProvider

    protected String getNodeTextFor( Object element ) {
        String result = element.toString(); // super.getText( element );
        if( element instanceof IDisplayCategory ) {
            result = ((IDisplayCategory)element).getLabel();
        }
        return result;
    }

    protected Image getColumnImageFor( Object element ) {
        Image result = null;
        if( element instanceof IDisplayCategory ) {
            result = ((IDisplayCategory)element).getImage();
        } else if( !(element instanceof CodeProportion) ) {
            result = getSharedImages().getImage( OBJ_INFO );
        }
        return result;
    }

}
