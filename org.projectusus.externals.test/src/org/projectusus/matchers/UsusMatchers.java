package org.projectusus.matchers;

// Generated source.
@SuppressWarnings("all")
public class UsusMatchers {

  public static <T> org.hamcrest.Matcher<java.util.Set<? extends T>> setOf(T... items) {
    return org.projectusus.matchers.IsSetOfMatcher.setOf(items);
  }

  public static <T> org.hamcrest.Matcher<java.util.Set<? extends T>> emptySet() {
    return org.projectusus.matchers.IsSetOfMatcher.emptySet();
  }

}
