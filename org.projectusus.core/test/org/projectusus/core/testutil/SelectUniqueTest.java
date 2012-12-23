package org.projectusus.core.testutil;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.projectusus.core.util.ForEach2;
import org.projectusus.core.util.SelectUnique;

import ch.akuhn.foreach.ForEach;

public class SelectUniqueTest {

    @Test
    public void shouldIncludeShortWordsOnlyOnce() {
        String[] words = "the quick brown fox jumps over the lazy dog".split( " " );
        for( SelectUnique<String> each : ForEach2.selectUnique( words ) ) {
            each.yield = each.value.length() < 4;
        }
        assertEquals( "[fox, the, dog]", ForEach.result().toString() );
    }

}
