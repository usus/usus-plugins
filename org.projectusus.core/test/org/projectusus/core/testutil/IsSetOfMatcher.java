package org.projectusus.core.testutil;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsSetOfMatcher<T> extends TypeSafeMatcher<Set<? extends T>> {

    private Matcher<?> matcher;

    public IsSetOfMatcher( T... items ) {
        this( asSet( items ) );
    }

    public IsSetOfMatcher( Set<? extends T> set ) {
        matcher = is( set );
    }

    @Override
    public boolean matchesSafely( Set<? extends T> set ) {
        return matcher.matches( set );
    }

    public void describeTo( Description description ) {
        matcher.describeTo( description );
    }

    public static <T> IsSetOfMatcher<T> isSetOf( T... items ) {
        return new IsSetOfMatcher<T>( items );
    }

    public static <T> IsSetOfMatcher<T> isEmptySet() {
        return new IsSetOfMatcher<T>( Collections.<T> emptySet() );
    }

    private static <T> Set<T> asSet( T... items ) {
        return new HashSet<T>( Arrays.asList( items ) );
    }

}
