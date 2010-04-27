package org.projectusus.core.internal.proportions.rawdata;

import static java.util.Collections.unmodifiableList;
import static org.projectusus.core.internal.util.CoreTexts.ususModelStatus_ok;
import static org.projectusus.core.internal.util.CoreTexts.ususModelStatus_stale;

import java.util.List;

import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.modelupdate.ICheckpoint;
import org.projectusus.core.internal.proportions.modelupdate.checkpoints.Checkpoint;
import org.projectusus.core.internal.proportions.modelupdate.checkpoints.LoadCheckpoints;
import org.projectusus.core.internal.proportions.modelupdate.checkpoints.SaveCheckpointsJob;

public class CheckpointHistory {

    private List<ICheckpoint> checkpoints;
    private List<CodeProportion> bufferedComputationResult;

    public CheckpointHistory() {
        checkpoints = new LoadCheckpoints().load();
        bufferedComputationResult = null;
    }

    public List<ICheckpoint> getCheckpoints() {
        return unmodifiableList( checkpoints );
    }

    public void addComputationResult( List<CodeProportion> result ) {
        this.bufferedComputationResult = result;
    }

    public void addTestResult( CodeProportion testProportion ) {
        if( computationTookPlaceAfterLastCheckpoint() ) {
            bufferedComputationResult.add( testProportion );
            checkpoints.add( new Checkpoint( bufferedComputationResult ) );
            new SaveCheckpointsJob( getCheckpoints() ).schedule();
            bufferedComputationResult = null;
        }
    }

    private boolean computationTookPlaceAfterLastCheckpoint() {
        return bufferedComputationResult != null;
    }

    public boolean isStale() {
        return computationTookPlaceAfterLastCheckpoint();
    }

    public String getStatusMessage() {
        return (isStale() ? ususModelStatus_stale : ususModelStatus_ok);
    }

}
