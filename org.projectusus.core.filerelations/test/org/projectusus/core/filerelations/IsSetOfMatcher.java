package org.projectusus.core.filerelations;

import static org.hamcrest.CoreMatchers.is;
import static org.projectusus.core.filerelations.TestServiceManager.asSet;

import java.util.Collections;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsSetOfMatcher<T> extends TypeSafeMatcher<Set<T>> {
	
	private Matcher<? super Set<T>> matcher;

	public IsSetOfMatcher(T... items) {
		this(asSet(items));
	}
	
	public IsSetOfMatcher(Set<T> set) {
		matcher = is(set);
	}

	public boolean matchesSafely(Set<T> set) {
		return matcher.matches(set);
	}

	public void describeTo(Description description) {
		matcher.describeTo(description);
	}
	
	public static <T> IsSetOfMatcher<T> isSetOf(T... items) {
		return new IsSetOfMatcher<T>(items);
	}

	public static <T> IsSetOfMatcher<T> isEmptySetOf(Class<T> clazz) {
		Set<T> emptySet = Collections.emptySet();
		return new IsSetOfMatcher<T>(emptySet);
	}

}
