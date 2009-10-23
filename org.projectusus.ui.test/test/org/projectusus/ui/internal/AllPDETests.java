// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.projectusus.core.internal.proportions.CodeProportionsPDETest;
import org.projectusus.core.internal.proportions.DeltaCodeProportionComputationTargetPDETest;

@RunWith(Suite.class)
@SuiteClasses({CodeProportionsPDETest.class, DeltaCodeProportionComputationTargetPDETest.class})
public class AllPDETests {

}
