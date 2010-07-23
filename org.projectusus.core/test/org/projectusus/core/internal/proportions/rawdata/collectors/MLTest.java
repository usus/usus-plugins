// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.testutil.ReflectionUtil;

public class MLTest {

    private Block block;

    private MLCollector mlChecker;

    @Before
    public void setup() {
        block = mock( Block.class );
        mlChecker = new MLCollector();
        mlChecker.setup( mock( IFile.class ), UsusModelProvider.getMetricsWriter() );
    }

    @Test
    public void methodSize() throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
        int expected = 7;
        initBlock( expected );
        mlChecker.visit( (MethodDeclaration)null );
        mlChecker.visit( block );
        int actual = ((Counter)(ReflectionUtil.getValue( mlChecker, "statementCount" ))).getAndClearCount(); //$NON-NLS-1$
        assertEquals( expected, actual );
    }

    @Test
    public void initializerSize() throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
        int expected = 17;
        initBlock( expected );
        mlChecker.visit( (Initializer)null );
        mlChecker.visit( block );
        int actual = ((Counter)(ReflectionUtil.getValue( mlChecker, "statementCount" ))).getAndClearCount(); //$NON-NLS-1$
        assertEquals( expected, actual );
    }

    @SuppressWarnings( "unchecked" )
    private void initBlock( int count ) {
        List<Statement> statements = mock( ArrayList.class );
        when( block.statements() ).thenReturn( statements );
        when( new Integer( statements.size() ) ).thenReturn( new Integer( count ) );
    }

}
