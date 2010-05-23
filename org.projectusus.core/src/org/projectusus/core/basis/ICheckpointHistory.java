package org.projectusus.core.basis;

import java.util.List;

public interface ICheckpointHistory {

    List<ICheckpoint> getCheckpoints();

    boolean isStale();

    String getStatusMessage();

}
