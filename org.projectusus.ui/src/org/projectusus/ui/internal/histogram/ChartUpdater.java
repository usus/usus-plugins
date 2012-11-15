package org.projectusus.ui.internal.histogram;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.projectusus.core.basis.Histogram;
import org.projectusus.jfeet.selection.ElementFrom;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.AnalysisDisplayModel;
import org.projectusus.ui.internal.IDisplayCategory;
import org.projectusus.ui.internal.IDisplayModelListener;
import org.projectusus.ui.internal.Snapshot;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.ISeries;

class ChartUpdater implements ISelectionListener, IDisplayModelListener {

    private final Chart chart;
    private AnalysisDisplayEntry lastEntry;

    public ChartUpdater( Chart chart ) {
        this.chart = chart;
    }

    void updateChart( AnalysisDisplayModel newModel ) {
        if( lastEntry == null ) {
            return;
        }
        for( IDisplayCategory category : newModel.getCategories() ) {
            for( AnalysisDisplayEntry entry : category.getChildren() ) {
                if( entry.isSameKindAs( lastEntry ) ) {
                    updateChart( entry );
                    return;
                }
            }
        }
    }

    void updateChart( final AnalysisDisplayEntry entry ) {
        this.lastEntry = entry;
        chart.getDisplay().asyncExec( new Runnable() {
            public void run() {
                IAxis xAxis = chart.getAxisSet().getXAxis( 0 );
                xAxis.getTitle().setText( entry.getLabel() );

                ISeries series = chart.getSeriesSet().getSeries( HistogramView.SERIES_ID );
                Histogram histogram = entry.getHistogram();
                series.setXSeries( histogram.allNumbers() );
                series.setYSeries( histogram.allValues() );

                chart.getAxisSet().adjustRange();
                chart.redraw();
            }
        } );
    }

    public void selectionChanged( @SuppressWarnings( "unused" ) IWorkbenchPart part, ISelection selection ) {
        AnalysisDisplayEntry entry = new ElementFrom( selection ).as( AnalysisDisplayEntry.class );
        if( entry != null ) {
            updateChart( entry );
        }
    }

    public void updateCategories( AnalysisDisplayModel newModel ) {
        updateChart( newModel );
    }

    @SuppressWarnings( "unused" )
    public void snapshotCreated( Snapshot snapshot ) {
        // nothing todo here
    }
}
