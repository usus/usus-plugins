package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.internal.AnalysisDisplayModel.displayModel;

import org.eclipse.swt.widgets.Display;
import org.projectusus.ui.internal.Snapshot;

public class SnapshotInfoUpdater implements Runnable {

    private final SnapshotInfoBuilder builder = new SnapshotInfoBuilder();
    private final ISnapshotView view;
    private boolean running = true;

    public SnapshotInfoUpdater( ISnapshotView view ) {
        this.view = view;
    }

    public synchronized void stop() {
        running = false;
    }

    public synchronized void run() {
        if( running ) {
            update();
            schedule();
        }
    }

    public void start() {
        schedule();
    }

    private synchronized void schedule() {
        if( running ) {
            Display.getDefault().timerExec( 10000, this );
        }
    }

    private void update() {
        update( displayModel().getSnapshot() );
    }

    public void update( Snapshot snapshot ) {
        view.updateSnapshotInfo( builder.buildInfo( snapshot ) );
    }
}
