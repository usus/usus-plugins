// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import java.util.Date;
import java.util.List;

import org.projectusus.core.internal.proportions.CodeProportion;

/**
 * an update command. Implementations can be sent to the Usus model to modify its status.
 * 
 * @author leif
 */
public interface IUsusModelUpdate {

    public enum Type {
        TEST_RUN, COMPUTATION_RUN
    }

    List<CodeProportion> getEntries();

    Date getTime();

    boolean isSuccessful();

    Type getType();
}
