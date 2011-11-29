package org.projectusus.matchers;

// Generated source.
@SuppressWarnings("all")
public class UsusMatchers {

  public static <T> org.hamcrest.Matcher<T> is(org.hamcrest.Matcher<T> p0) {
    return org.hamcrest.core.Is.is(p0);
  }

  public static <T> org.hamcrest.Matcher<T> is(T param1) {
    return org.hamcrest.core.Is.is(param1);
  }

  public static org.hamcrest.Matcher<java.lang.Object> is(java.lang.Class<?> p0) {
    return org.hamcrest.core.Is.is(p0);
  }

  public static <T> org.hamcrest.Matcher<T> not(org.hamcrest.Matcher<T> p0) {
    return org.hamcrest.core.IsNot.not(p0);
  }

  public static <T> org.hamcrest.Matcher<T> not(T param1) {
    return org.hamcrest.core.IsNot.not(param1);
  }

  public static <T> org.hamcrest.Matcher<T> equalTo(T param1) {
    return org.hamcrest.core.IsEqual.equalTo(param1);
  }

  public static org.hamcrest.Matcher<java.lang.Object> instanceOf(java.lang.Class<?> p0) {
    return org.hamcrest.core.IsInstanceOf.instanceOf(p0);
  }

  public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<? extends T>... param1) {
    return org.hamcrest.core.AllOf.allOf(param1);
  }

  public static <T> org.hamcrest.Matcher<T> allOf(java.lang.Iterable<org.hamcrest.Matcher<? extends T>> p0) {
    return org.hamcrest.core.AllOf.allOf(p0);
  }

  public static <T> org.hamcrest.Matcher<T> anyOf(org.hamcrest.Matcher<? extends T>... param1) {
    return org.hamcrest.core.AnyOf.anyOf(param1);
  }

  public static <T> org.hamcrest.Matcher<T> anyOf(java.lang.Iterable<org.hamcrest.Matcher<? extends T>> p0) {
    return org.hamcrest.core.AnyOf.anyOf(p0);
  }

  public static <T> org.hamcrest.Matcher<T> sameInstance(T param1) {
    return org.hamcrest.core.IsSame.sameInstance(param1);
  }

  public static <T> org.hamcrest.Matcher<T> anything() {
    return org.hamcrest.core.IsAnything.anything();
  }

  public static <T> org.hamcrest.Matcher<T> anything(java.lang.String p0) {
    return org.hamcrest.core.IsAnything.anything(p0);
  }

  public static <T> org.hamcrest.Matcher<T> any(java.lang.Class<T> p0) {
    return org.hamcrest.core.IsAnything.any(p0);
  }

  public static <T> org.hamcrest.Matcher<T> nullValue() {
    return org.hamcrest.core.IsNull.nullValue();
  }

  public static <T> org.hamcrest.Matcher<T> nullValue(java.lang.Class<T> p0) {
    return org.hamcrest.core.IsNull.nullValue(p0);
  }

  public static <T> org.hamcrest.Matcher<T> notNullValue() {
    return org.hamcrest.core.IsNull.notNullValue();
  }

  public static <T> org.hamcrest.Matcher<T> notNullValue(java.lang.Class<T> p0) {
    return org.hamcrest.core.IsNull.notNullValue(p0);
  }

  public static <T> org.hamcrest.Matcher<T> describedAs(java.lang.String param1, org.hamcrest.Matcher<T> param2, java.lang.Object... param3) {
    return org.hamcrest.core.DescribedAs.describedAs(param1, param2, param3);
  }

  public static <T> org.hamcrest.Matcher<T[]> hasItemInArray(org.hamcrest.Matcher<T> p0) {
    return org.hamcrest.collection.IsArrayContaining.hasItemInArray(p0);
  }

  public static <T> org.hamcrest.Matcher<T[]> hasItemInArray(T param1) {
    return org.hamcrest.collection.IsArrayContaining.hasItemInArray(param1);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItem(T param1) {
    return org.hamcrest.collection.IsCollectionContaining.hasItem(param1);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItem(org.hamcrest.Matcher<? extends T> p0) {
    return org.hamcrest.collection.IsCollectionContaining.hasItem(p0);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(org.hamcrest.Matcher<? extends T>... param1) {
    return org.hamcrest.collection.IsCollectionContaining.hasItems(param1);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(T... param1) {
    return org.hamcrest.collection.IsCollectionContaining.hasItems(param1);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasEntry(org.hamcrest.Matcher<K> p0, org.hamcrest.Matcher<V> p1) {
    return org.hamcrest.collection.IsMapContaining.hasEntry(p0, p1);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasEntry(K param1, V param2) {
    return org.hamcrest.collection.IsMapContaining.hasEntry(param1, param2);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasKey(org.hamcrest.Matcher<K> p0) {
    return org.hamcrest.collection.IsMapContaining.hasKey(p0);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasKey(K param1) {
    return org.hamcrest.collection.IsMapContaining.hasKey(param1);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasValue(org.hamcrest.Matcher<V> p0) {
    return org.hamcrest.collection.IsMapContaining.hasValue(p0);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasValue(V param1) {
    return org.hamcrest.collection.IsMapContaining.hasValue(param1);
  }

  public static <T> org.hamcrest.Matcher<T> isIn(java.util.Collection<T> p0) {
    return org.hamcrest.collection.IsIn.isIn(p0);
  }

  public static <T> org.hamcrest.Matcher<T> isIn(T[] param1) {
    return org.hamcrest.collection.IsIn.isIn(param1);
  }

  public static <T> org.hamcrest.Matcher<T> isOneOf(T... param1) {
    return org.hamcrest.collection.IsIn.isOneOf(param1);
  }

  public static org.hamcrest.Matcher<java.lang.Double> closeTo(double p0, double p1) {
    return org.hamcrest.number.IsCloseTo.closeTo(p0, p1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThan(T param1) {
    return org.hamcrest.number.OrderingComparisons.greaterThan(param1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThanOrEqualTo(T param1) {
    return org.hamcrest.number.OrderingComparisons.greaterThanOrEqualTo(param1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThan(T param1) {
    return org.hamcrest.number.OrderingComparisons.lessThan(param1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThanOrEqualTo(T param1) {
    return org.hamcrest.number.OrderingComparisons.lessThanOrEqualTo(param1);
  }

  public static org.hamcrest.Matcher<java.lang.String> equalToIgnoringCase(java.lang.String p0) {
    return org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase(p0);
  }

  public static org.hamcrest.Matcher<java.lang.String> equalToIgnoringWhiteSpace(java.lang.String p0) {
    return org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(p0);
  }

  public static org.hamcrest.Matcher<java.lang.String> containsString(java.lang.String p0) {
    return org.hamcrest.text.StringContains.containsString(p0);
  }

  public static org.hamcrest.Matcher<java.lang.String> endsWith(java.lang.String p0) {
    return org.hamcrest.text.StringEndsWith.endsWith(p0);
  }

  public static org.hamcrest.Matcher<java.lang.String> startsWith(java.lang.String p0) {
    return org.hamcrest.text.StringStartsWith.startsWith(p0);
  }

  public static <T> org.hamcrest.Matcher<T> hasToString(org.hamcrest.Matcher<java.lang.String> p0) {
    return org.hamcrest.object.HasToString.hasToString(p0);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Class<?>> typeCompatibleWith(java.lang.Class<T> p0) {
    return org.hamcrest.object.IsCompatibleType.typeCompatibleWith(p0);
  }

  public static org.hamcrest.Matcher<java.util.EventObject> eventFrom(java.lang.Class<? extends java.util.EventObject> p0, java.lang.Object p1) {
    return org.hamcrest.object.IsEventFrom.eventFrom(p0, p1);
  }

  public static org.hamcrest.Matcher<java.util.EventObject> eventFrom(java.lang.Object p0) {
    return org.hamcrest.object.IsEventFrom.eventFrom(p0);
  }

  public static <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String p0) {
    return org.hamcrest.beans.HasProperty.hasProperty(p0);
  }

  public static <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String p0, org.hamcrest.Matcher p1) {
    return org.hamcrest.beans.HasPropertyWithValue.hasProperty(p0, p1);
  }

  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String p0, org.hamcrest.Matcher<java.lang.String> p1) {
    return org.hamcrest.xml.HasXPath.hasXPath(p0, p1);
  }

  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String p0) {
    return org.hamcrest.xml.HasXPath.hasXPath(p0);
  }

  public static <T> org.hamcrest.Matcher<java.util.Set<? extends T>> setOf(T... items) {
    return org.projectusus.matchers.IsSetOfMatcher.setOf(items);
  }

  public static <T> org.hamcrest.Matcher<java.util.Set<? extends T>> emptySet() {
    return org.projectusus.matchers.IsSetOfMatcher.emptySet();
  }

}
