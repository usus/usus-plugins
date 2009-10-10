package org.projectusus.core.internal.proportions.checkpoints;

import java.util.Date;
import java.util.List;

import org.projectusus.core.internal.proportions.CodeProportion;

public interface ICheckpoint {

    Date getTime();

    List<CodeProportion> getEntries();
}
