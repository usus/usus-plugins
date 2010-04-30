// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.eclipse.jdt.core.IMethod;
import org.junit.Test;

public class UsusInfoBuilderTest {

    @Test
    public void unavailableMethodYieldsNull() {
        IUsusInfo info = UsusInfoBuilder.of( null );
        assertEquals( UnavailableUsusInfo.class, info.getClass() );
    }

    @Test
    public void availableDataYieldsMeaningfulInfo() {
        IMethod method = mock( IMethod.class );
        IUsusInfo info = UsusInfoBuilder.of( method );
        assertEquals( UsusInfoForFile.class, info.getClass() );
    }

}
