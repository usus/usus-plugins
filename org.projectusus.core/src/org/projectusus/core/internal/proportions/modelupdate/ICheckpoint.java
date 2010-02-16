// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.modelupdate;

import java.util.List;

import org.joda.time.DateTime;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;

public interface ICheckpoint {

    DateTime getTime();

    List<CodeProportion> getEntries();

    CodeProportion findCodeProportion( CodeProportionKind kind );

}
