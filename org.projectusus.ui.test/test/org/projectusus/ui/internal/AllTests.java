// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.projectusus.core.internal.coverage.CoverageTest;
import org.projectusus.core.internal.proportions.model.SQIComputerTest;
import org.projectusus.core.internal.proportions.modelupdate.CheckpointsTest;
import org.projectusus.core.internal.proportions.modelupdate.UsusModelHistoryTest;
import org.projectusus.core.internal.proportions.modelupdate.UsusModelStatusTest;
import org.projectusus.core.internal.proportions.rawdata.UsusModelTest;
import org.projectusus.core.internal.yellowcount.WiseCrackTest;
import org.projectusus.ui.internal.history.Checkpoints2GraphicsConverterTest;
import org.projectusus.ui.internal.selection.EditorInputAnalysisTest;

@RunWith( Suite.class )
@SuiteClasses( { SQIComputerTest.class, //
    EditorInputAnalysisTest.class, //
    UsusModelHistoryTest.class, //
    Checkpoints2GraphicsConverterTest.class, //
    CheckpointsTest.class, //
        CoverageTest.class, //
        WiseCrackTest.class, //
        UsusModelTest.class, //
        UsusModelStatusTest.class, //
        org.projectusus.core.filerelations.AllTests.class } )
        // TODO add MLTest.class
public class AllTests {

}
