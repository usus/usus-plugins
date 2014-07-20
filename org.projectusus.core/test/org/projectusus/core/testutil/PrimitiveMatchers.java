package org.projectusus.core.testutil;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * Utility class to allow calling <code>is</code> on primitive types and without auto boxing/unboxing.
 *
 *
 */
public class PrimitiveMatchers {

    public static Matcher<Byte> is( final byte value ) {
        return Matchers.is( Byte.valueOf( value ) );
    }

    public static Matcher<Short> is( final short value ) {
        return Matchers.is( Short.valueOf( value ) );
    }

    public static Matcher<Integer> is( final int value ) {
        return Matchers.is( Integer.valueOf( value ) );
    }

    public static Matcher<Long> is( final long value ) {
        return Matchers.is( Long.valueOf( value ) );
    }

    public static Matcher<Float> is( final float value ) {
        return Matchers.is( Float.valueOf( value ) );
    }

    public static Matcher<Double> is( final double value ) {
        return Matchers.is( Double.valueOf( value ) );
    }

    public static Matcher<Boolean> is( final boolean value ) {
        return Matchers.is( Boolean.valueOf( value ) );
    }

    public static Matcher<Character> is( final char value ) {
        return Matchers.is( Character.valueOf( value ) );
    }

}
