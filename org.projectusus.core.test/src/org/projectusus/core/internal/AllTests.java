package org.projectusus.core.internal;

import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.runner.RunWith;

@RunWith( ClasspathSuite.class )
//Doku siehe http://www.johanneslink.net/projects/cpsuite.html
@ClassnameFilters( { "!org.projectusus.core.internal.*.*PDETest" } )
public class AllTests {
//dat muss so
}
