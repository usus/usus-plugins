package org.projectusus.core.internal.proportions;

import java.util.List;

import org.projectusus.core.internal.proportions.checkpoints.ICheckpoint;

public interface ICodeProportions {

    List<ICheckpoint> getCheckpoints();

    List<CodeProportion> getEntries();

    ICodeProportionsStatus getLastStatus();

    void addCodeProportionsListener( ICodeProportionsListener listener );

    void removeCodeProportionsListener( ICodeProportionsListener listener );

    void forceRecompute();
}
