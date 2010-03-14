package org.projectusus.core.filerelations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( { //
CCDTest.class, //
        FileRelationMetricsChildrenTest.class, //
        FileRelationMetricsRemoveTest.class } )
public class AllTests {
    // nothing goes here
}
