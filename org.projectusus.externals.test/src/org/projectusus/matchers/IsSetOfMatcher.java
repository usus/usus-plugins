package org.projectusus.matchers;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsSetOfMatcher<T> extends BaseMatcher<Set<? extends T>> {

    private Matcher<?> matcher;

    public IsSetOfMatcher( T... items ) {
        this( asSet( items ) );
    }

    public IsSetOfMatcher( Set<? extends T> set ) {
        matcher = is( set );
    }

    @SuppressWarnings( "unchecked" )
    public boolean matches( Object object ) {
        return matchesSafely( (Set<? extends T>)object );
    }

    protected boolean matchesSafely( Set<? extends T> set ) {
        return matcher.matches( set );
    }

    public void describeTo( Description description ) {
        matcher.describeTo( description );
    }

    @Factory
    public static <T> org.hamcrest.Matcher<Set<? extends T>> setOf( T... items ) {
        return new IsSetOfMatcher<T>( items );
    }

    @Factory
    public static <T> org.hamcrest.Matcher<Set<? extends T>> emptySet() {
        return new IsSetOfMatcher<T>( Collections.<T> emptySet() );
    }

    private static <T> Set<T> asSet( T... items ) {
        return new HashSet<T>( Arrays.asList( items ) );
    }

}
