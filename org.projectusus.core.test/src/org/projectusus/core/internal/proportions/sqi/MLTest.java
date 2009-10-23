// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;



public class MLTest extends ML {

    private BetterFileContents contents;

    @Before
    public void setup() {
        contents = mock( BetterFileContents.class );
        when( new Boolean( contents.lineIsBlank( anyInt() ) ) ).thenReturn( Boolean.FALSE );
        when( new Boolean( contents.lineIsComment( anyInt() ) ) ).thenReturn( Boolean.FALSE );
    }

    @Override
    public BetterFileContents getBetterFileContents() {
        return contents;
    }

    @Test
    public void bracesOnSameLine() {
        int expected = 1;
        int actual = this.calcMethodLength( 1, 1 );
        assertEquals( expected, actual );
    }

    @Test
    public void bracesOnSameLine2() {
        int expected = 1;
        int actual = this.calcMethodLength( 147, 147 );
        assertEquals( expected, actual );
    }

    @Test
    public void bracesOneLineApart() {
        int expected = 2;
        int actual = this.calcMethodLength( 14, 15 );
        assertEquals( expected, actual );
    }

    @Test
    public void bracesSomeLinesApart() {
        int expected = 15;
        int actual = this.calcMethodLength( 1, 15 );
        assertEquals( expected, actual );
    }

    @Test
    public void oneLineIsComment() {
        when( new Boolean( contents.lineIsComment( 7 ) ) ).thenReturn( Boolean.TRUE );
        int expected = 14;
        int actual = this.calcMethodLength( 1, 15 );
        assertEquals( expected, actual );
    }

    @Test
    public void oneLineIsBlank() {
        when( new Boolean( contents.lineIsBlank( 7 ) ) ).thenReturn( Boolean.TRUE );
        int expected = 14;
        int actual = this.calcMethodLength( 1, 15 );
        assertEquals( expected, actual );
    }

    @Test
    public void someLinesAreBlankOrComment() {
        when( new Boolean( contents.lineIsBlank( 4 ) ) ).thenReturn( Boolean.TRUE );
        when( new Boolean( contents.lineIsBlank( 7 ) ) ).thenReturn( Boolean.TRUE );
        when( new Boolean( contents.lineIsComment( 5 ) ) ).thenReturn( Boolean.TRUE );
        when( new Boolean( contents.lineIsComment( 6 ) ) ).thenReturn( Boolean.TRUE );
        when( new Boolean( contents.lineIsComment( 7 ) ) ).thenReturn( Boolean.TRUE );
        int expected = 11;
        int actual = this.calcMethodLength( 1, 15 );
        assertEquals( expected, actual );
    }

}
