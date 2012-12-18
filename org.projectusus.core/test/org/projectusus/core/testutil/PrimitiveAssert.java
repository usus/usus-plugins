package org.projectusus.core.testutil;

import org.hamcrest.Matcher;
import org.junit.Assert;

/**
 * Hilfsklasse, um assertThat-Aufrufe auch mit Primitivtypen und ohne Auto-(Un-)Boxing zu ermöglichen.
 * 
 */
public class PrimitiveAssert {

    public static void assertThat( final byte actual, final Matcher<Byte> matcher ) {
        Assert.assertThat( Byte.valueOf( actual ), matcher );
    }

    public static void assertThat( final short actual, final Matcher<Short> matcher ) {
        Assert.assertThat( Short.valueOf( actual ), matcher );
    }

    public static void assertThat( final int actual, final Matcher<Integer> matcher ) {
        Assert.assertThat( Integer.valueOf( actual ), matcher );
    }

    public static void assertThat( final long actual, final Matcher<Long> matcher ) {
        Assert.assertThat( Long.valueOf( actual ), matcher );
    }

    public static void assertThat( final float actual, final Matcher<Float> matcher ) {
        Assert.assertThat( Float.valueOf( actual ), matcher );
    }

    public static void assertThat( final double actual, final Matcher<Double> matcher ) {
        Assert.assertThat( Double.valueOf( actual ), matcher );
    }

    public static void assertThat( final boolean actual, final Matcher<Boolean> matcher ) {
        Assert.assertThat( Boolean.valueOf( actual ), matcher );
    }

}
