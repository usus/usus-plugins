// Generated source.
package org.projectusus.matchers;

public class UsusMatchers {

  public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<? super T> p0, org.hamcrest.Matcher<? super T> p1) {
    return org.hamcrest.core.AllOf.<T>allOf(p0, p1);
  }

  public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<? super T> p0, org.hamcrest.Matcher<? super T> p1, org.hamcrest.Matcher<? super T> p2) {
    return org.hamcrest.core.AllOf.<T>allOf(p0, p1, p2);
  }

  public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<? super T> p0, org.hamcrest.Matcher<? super T> p1, org.hamcrest.Matcher<? super T> p2, org.hamcrest.Matcher<? super T> p3) {
    return org.hamcrest.core.AllOf.<T>allOf(p0, p1, p2, p3);
  }

  public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<? super T> p0, org.hamcrest.Matcher<? super T> p1, org.hamcrest.Matcher<? super T> p2, org.hamcrest.Matcher<? super T> p3, org.hamcrest.Matcher<? super T> p4) {
    return org.hamcrest.core.AllOf.<T>allOf(p0, p1, p2, p3, p4);
  }

  public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<? super T> p0, org.hamcrest.Matcher<? super T> p1, org.hamcrest.Matcher<? super T> p2, org.hamcrest.Matcher<? super T> p3, org.hamcrest.Matcher<? super T> p4, org.hamcrest.Matcher<? super T> p5) {
    return org.hamcrest.core.AllOf.<T>allOf(p0, p1, p2, p3, p4, p5);
  }

  public static <T> org.hamcrest.Matcher<T> allOf(java.lang.Iterable<org.hamcrest.Matcher<? super T>> p0) {
    return org.hamcrest.core.AllOf.<T>allOf(p0);
  }

  public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<? super T>... param1) {
    return org.hamcrest.core.AllOf.<T>allOf(param1);
  }

  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<T> p0, org.hamcrest.Matcher<? super T> p1, org.hamcrest.Matcher<? super T> p2, org.hamcrest.Matcher<? super T> p3) {
    return org.hamcrest.core.AnyOf.<T>anyOf(p0, p1, p2, p3);
  }

  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<T> p0, org.hamcrest.Matcher<? super T> p1) {
    return org.hamcrest.core.AnyOf.<T>anyOf(p0, p1);
  }

  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<T> p0, org.hamcrest.Matcher<? super T> p1, org.hamcrest.Matcher<? super T> p2) {
    return org.hamcrest.core.AnyOf.<T>anyOf(p0, p1, p2);
  }

  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<? super T>... param1) {
    return org.hamcrest.core.AnyOf.<T>anyOf(param1);
  }

  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<T> p0, org.hamcrest.Matcher<? super T> p1, org.hamcrest.Matcher<? super T> p2, org.hamcrest.Matcher<? super T> p3, org.hamcrest.Matcher<? super T> p4) {
    return org.hamcrest.core.AnyOf.<T>anyOf(p0, p1, p2, p3, p4);
  }

  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<T> p0, org.hamcrest.Matcher<? super T> p1, org.hamcrest.Matcher<? super T> p2, org.hamcrest.Matcher<? super T> p3, org.hamcrest.Matcher<? super T> p4, org.hamcrest.Matcher<? super T> p5) {
    return org.hamcrest.core.AnyOf.<T>anyOf(p0, p1, p2, p3, p4, p5);
  }

  public static <T> org.hamcrest.core.AnyOf<T> anyOf(java.lang.Iterable<org.hamcrest.Matcher<? super T>> p0) {
    return org.hamcrest.core.AnyOf.<T>anyOf(p0);
  }

  public static <LHS> org.hamcrest.core.CombinableMatcher.CombinableBothMatcher<LHS> both(org.hamcrest.Matcher<? super LHS> p0) {
    return org.hamcrest.core.CombinableMatcher.<LHS>both(p0);
  }

  public static <LHS> org.hamcrest.core.CombinableMatcher.CombinableEitherMatcher<LHS> either(org.hamcrest.Matcher<? super LHS> p0) {
    return org.hamcrest.core.CombinableMatcher.<LHS>either(p0);
  }

  public static <T> org.hamcrest.Matcher<T> describedAs(java.lang.String param1, org.hamcrest.Matcher<T> param2, java.lang.Object... param3) {
    return org.hamcrest.core.DescribedAs.<T>describedAs(param1, param2, param3);
  }

  public static <U> org.hamcrest.Matcher<java.lang.Iterable<U>> everyItem(org.hamcrest.Matcher<U> p0) {
    return org.hamcrest.core.Every.<U>everyItem(p0);
  }

  public static <T> org.hamcrest.Matcher<T> is(org.hamcrest.Matcher<T> p0) {
    return org.hamcrest.core.Is.<T>is(p0);
  }

  public static <T> org.hamcrest.Matcher<T> is(T param1) {
    return org.hamcrest.core.Is.<T>is(param1);
  }

  public static <T> org.hamcrest.Matcher<T> is(java.lang.Class<T> p0) {
    return org.hamcrest.core.Is.<T>is(p0);
  }

  public static <T> org.hamcrest.Matcher<T> isA(java.lang.Class<T> p0) {
    return org.hamcrest.core.Is.<T>isA(p0);
  }

  public static org.hamcrest.Matcher<java.lang.Object> anything() {
    return org.hamcrest.core.IsAnything.anything();
  }

  public static org.hamcrest.Matcher<java.lang.Object> anything(java.lang.String p0) {
    return org.hamcrest.core.IsAnything.anything(p0);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<? super T>> hasItem(T param1) {
    return org.hamcrest.core.IsCollectionContaining.<T>hasItem(param1);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<? super T>> hasItem(org.hamcrest.Matcher<? super T> p0) {
    return org.hamcrest.core.IsCollectionContaining.<T>hasItem(p0);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(org.hamcrest.Matcher<? super T>... param1) {
    return org.hamcrest.core.IsCollectionContaining.<T>hasItems(param1);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(T... param1) {
    return org.hamcrest.core.IsCollectionContaining.<T>hasItems(param1);
  }

  public static <T> org.hamcrest.Matcher<T> equalTo(T param1) {
    return org.hamcrest.core.IsEqual.<T>equalTo(param1);
  }

  public static <T> org.hamcrest.Matcher<T> any(java.lang.Class<T> p0) {
    return org.hamcrest.core.IsInstanceOf.<T>any(p0);
  }

  public static <T> org.hamcrest.Matcher<T> instanceOf(java.lang.Class<?> p0) {
    return org.hamcrest.core.IsInstanceOf.<T>instanceOf(p0);
  }

  public static <T> org.hamcrest.Matcher<T> not(org.hamcrest.Matcher<T> p0) {
    return org.hamcrest.core.IsNot.<T>not(p0);
  }

  public static <T> org.hamcrest.Matcher<T> not(T param1) {
    return org.hamcrest.core.IsNot.<T>not(param1);
  }

  public static org.hamcrest.Matcher<java.lang.Object> nullValue() {
    return org.hamcrest.core.IsNull.nullValue();
  }

  public static <T> org.hamcrest.Matcher<T> nullValue(java.lang.Class<T> p0) {
    return org.hamcrest.core.IsNull.<T>nullValue(p0);
  }

  public static org.hamcrest.Matcher<java.lang.Object> notNullValue() {
    return org.hamcrest.core.IsNull.notNullValue();
  }

  public static <T> org.hamcrest.Matcher<T> notNullValue(java.lang.Class<T> p0) {
    return org.hamcrest.core.IsNull.<T>notNullValue(p0);
  }

  public static <T> org.hamcrest.Matcher<T> sameInstance(T param1) {
    return org.hamcrest.core.IsSame.<T>sameInstance(param1);
  }

  public static <T> org.hamcrest.Matcher<T> theInstance(T param1) {
    return org.hamcrest.core.IsSame.<T>theInstance(param1);
  }

  public static org.hamcrest.Matcher<java.lang.String> containsString(java.lang.String p0) {
    return org.hamcrest.core.StringContains.containsString(p0);
  }

  public static org.hamcrest.Matcher<java.lang.String> endsWith(java.lang.String p0) {
    return org.hamcrest.core.StringEndsWith.endsWith(p0);
  }

  public static org.hamcrest.Matcher<java.lang.String> startsWith(java.lang.String p0) {
    return org.hamcrest.core.StringStartsWith.startsWith(p0);
  }

  public static <T> org.hamcrest.collection.IsArray<T> array(org.hamcrest.Matcher<? super T>... param1) {
    return org.hamcrest.collection.IsArray.<T>array(param1);
  }

  public static <T> org.hamcrest.Matcher<T[]> hasItemInArray(org.hamcrest.Matcher<? super T> p0) {
    return org.hamcrest.collection.IsArrayContaining.<T>hasItemInArray(p0);
  }

  public static <T> org.hamcrest.Matcher<T[]> hasItemInArray(T param1) {
    return org.hamcrest.collection.IsArrayContaining.<T>hasItemInArray(param1);
  }

  public static <E> org.hamcrest.Matcher<E[]> arrayContainingInAnyOrder(org.hamcrest.Matcher<? super E>... param1) {
    return org.hamcrest.collection.IsArrayContainingInAnyOrder.<E>arrayContainingInAnyOrder(param1);
  }

  public static <E> org.hamcrest.Matcher<E[]> arrayContainingInAnyOrder(java.util.Collection<org.hamcrest.Matcher<? super E>> p0) {
    return org.hamcrest.collection.IsArrayContainingInAnyOrder.<E>arrayContainingInAnyOrder(p0);
  }

  public static <E> org.hamcrest.Matcher<E[]> arrayContainingInAnyOrder(E... param1) {
    return org.hamcrest.collection.IsArrayContainingInAnyOrder.<E>arrayContainingInAnyOrder(param1);
  }

  public static <E> org.hamcrest.Matcher<E[]> arrayContaining(E... param1) {
    return org.hamcrest.collection.IsArrayContainingInOrder.<E>arrayContaining(param1);
  }

  public static <E> org.hamcrest.Matcher<E[]> arrayContaining(org.hamcrest.Matcher<? super E>... param1) {
    return org.hamcrest.collection.IsArrayContainingInOrder.<E>arrayContaining(param1);
  }

  public static <E> org.hamcrest.Matcher<E[]> arrayContaining(java.util.List<org.hamcrest.Matcher<? super E>> p0) {
    return org.hamcrest.collection.IsArrayContainingInOrder.<E>arrayContaining(p0);
  }

  public static <E> org.hamcrest.Matcher<E[]> arrayWithSize(org.hamcrest.Matcher<? super java.lang.Integer> p0) {
    return org.hamcrest.collection.IsArrayWithSize.<E>arrayWithSize(p0);
  }

  public static <E> org.hamcrest.Matcher<E[]> arrayWithSize(int p0) {
    return org.hamcrest.collection.IsArrayWithSize.<E>arrayWithSize(p0);
  }

  public static <E> org.hamcrest.Matcher<E[]> emptyArray() {
    return org.hamcrest.collection.IsArrayWithSize.<E>emptyArray();
  }

  public static <E> org.hamcrest.Matcher<java.util.Collection<? extends E>> hasSize(org.hamcrest.Matcher<? super java.lang.Integer> p0) {
    return org.hamcrest.collection.IsCollectionWithSize.<E>hasSize(p0);
  }

  public static <E> org.hamcrest.Matcher<java.util.Collection<? extends E>> hasSize(int p0) {
    return org.hamcrest.collection.IsCollectionWithSize.<E>hasSize(p0);
  }

  public static <E> org.hamcrest.Matcher<java.util.Collection<? extends E>> empty() {
    return org.hamcrest.collection.IsEmptyCollection.<E>empty();
  }

  public static <E> org.hamcrest.Matcher<java.util.Collection<E>> emptyCollectionOf(java.lang.Class<E> p0) {
    return org.hamcrest.collection.IsEmptyCollection.<E>emptyCollectionOf(p0);
  }

  public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> emptyIterable() {
    return org.hamcrest.collection.IsEmptyIterable.<E>emptyIterable();
  }

  public static <E> org.hamcrest.Matcher<java.lang.Iterable<E>> emptyIterableOf(java.lang.Class<E> p0) {
    return org.hamcrest.collection.IsEmptyIterable.<E>emptyIterableOf(p0);
  }

  public static <T> org.hamcrest.Matcher<T> isIn(java.util.Collection<T> p0) {
    return org.hamcrest.collection.IsIn.<T>isIn(p0);
  }

  public static <T> org.hamcrest.Matcher<T> isIn(T[] param1) {
    return org.hamcrest.collection.IsIn.<T>isIn(param1);
  }

  public static <T> org.hamcrest.Matcher<T> isOneOf(T... param1) {
    return org.hamcrest.collection.IsIn.<T>isOneOf(param1);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<? extends T>> containsInAnyOrder(org.hamcrest.Matcher<? super T>... param1) {
    return org.hamcrest.collection.IsIterableContainingInAnyOrder.<T>containsInAnyOrder(param1);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<? extends T>> containsInAnyOrder(T... param1) {
    return org.hamcrest.collection.IsIterableContainingInAnyOrder.<T>containsInAnyOrder(param1);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Iterable<? extends T>> containsInAnyOrder(java.util.Collection<org.hamcrest.Matcher<? super T>> p0) {
    return org.hamcrest.collection.IsIterableContainingInAnyOrder.<T>containsInAnyOrder(p0);
  }

  public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> containsInAnyOrder(org.hamcrest.Matcher<? super E> p0) {
    return org.hamcrest.collection.IsIterableContainingInAnyOrder.<E>containsInAnyOrder(p0);
  }

  public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> contains(org.hamcrest.Matcher<? super E> p0) {
    return org.hamcrest.collection.IsIterableContainingInOrder.<E>contains(p0);
  }

  public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> contains(E... param1) {
    return org.hamcrest.collection.IsIterableContainingInOrder.<E>contains(param1);
  }

  public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> contains(org.hamcrest.Matcher<? super E>... param1) {
    return org.hamcrest.collection.IsIterableContainingInOrder.<E>contains(param1);
  }

  public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> contains(java.util.List<org.hamcrest.Matcher<? super E>> p0) {
    return org.hamcrest.collection.IsIterableContainingInOrder.<E>contains(p0);
  }

  public static <E> org.hamcrest.Matcher<java.lang.Iterable<E>> iterableWithSize(org.hamcrest.Matcher<? super java.lang.Integer> p0) {
    return org.hamcrest.collection.IsIterableWithSize.<E>iterableWithSize(p0);
  }

  public static <E> org.hamcrest.Matcher<java.lang.Iterable<E>> iterableWithSize(int p0) {
    return org.hamcrest.collection.IsIterableWithSize.<E>iterableWithSize(p0);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<? extends K, ? extends V>> hasEntry(org.hamcrest.Matcher<? super K> p0, org.hamcrest.Matcher<? super V> p1) {
    return org.hamcrest.collection.IsMapContaining.<K,V>hasEntry(p0, p1);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<? extends K, ? extends V>> hasEntry(K param1, V param2) {
    return org.hamcrest.collection.IsMapContaining.<K,V>hasEntry(param1, param2);
  }

  public static <K> org.hamcrest.Matcher<java.util.Map<? extends K, ?>> hasKey(K param1) {
    return org.hamcrest.collection.IsMapContaining.<K>hasKey(param1);
  }

  public static <K> org.hamcrest.Matcher<java.util.Map<? extends K, ?>> hasKey(org.hamcrest.Matcher<? super K> p0) {
    return org.hamcrest.collection.IsMapContaining.<K>hasKey(p0);
  }

  public static <V> org.hamcrest.Matcher<java.util.Map<?, ? extends V>> hasValue(org.hamcrest.Matcher<? super V> p0) {
    return org.hamcrest.collection.IsMapContaining.<V>hasValue(p0);
  }

  public static <V> org.hamcrest.Matcher<java.util.Map<?, ? extends V>> hasValue(V param1) {
    return org.hamcrest.collection.IsMapContaining.<V>hasValue(param1);
  }

  public static org.hamcrest.Matcher<java.lang.Double> closeTo(double p0, double p1) {
    return org.hamcrest.number.IsCloseTo.closeTo(p0, p1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> comparesEqualTo(T param1) {
    return org.hamcrest.number.OrderingComparison.<T>comparesEqualTo(param1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThan(T param1) {
    return org.hamcrest.number.OrderingComparison.<T>greaterThan(param1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThanOrEqualTo(T param1) {
    return org.hamcrest.number.OrderingComparison.<T>greaterThanOrEqualTo(param1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThan(T param1) {
    return org.hamcrest.number.OrderingComparison.<T>lessThan(param1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThanOrEqualTo(T param1) {
    return org.hamcrest.number.OrderingComparison.<T>lessThanOrEqualTo(param1);
  }

  public static org.hamcrest.Matcher<java.lang.String> isEmptyString() {
    return org.hamcrest.text.IsEmptyString.isEmptyString();
  }

  public static org.hamcrest.Matcher<java.lang.String> isEmptyOrNullString() {
    return org.hamcrest.text.IsEmptyString.isEmptyOrNullString();
  }

  public static org.hamcrest.Matcher<java.lang.String> equalToIgnoringCase(java.lang.String p0) {
    return org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase(p0);
  }

  public static org.hamcrest.Matcher<java.lang.String> equalToIgnoringWhiteSpace(java.lang.String p0) {
    return org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(p0);
  }

  public static org.hamcrest.Matcher<java.lang.String> stringContainsInOrder(java.lang.Iterable<java.lang.String> p0) {
    return org.hamcrest.text.StringContainsInOrder.stringContainsInOrder(p0);
  }

  public static <T> org.hamcrest.Matcher<T> hasToString(org.hamcrest.Matcher<? super java.lang.String> p0) {
    return org.hamcrest.object.HasToString.<T>hasToString(p0);
  }

  public static <T> org.hamcrest.Matcher<T> hasToString(java.lang.String p0) {
    return org.hamcrest.object.HasToString.<T>hasToString(p0);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Class<?>> typeCompatibleWith(java.lang.Class<T> p0) {
    return org.hamcrest.object.IsCompatibleType.<T>typeCompatibleWith(p0);
  }

  public static org.hamcrest.Matcher<java.util.EventObject> eventFrom(java.lang.Class<? extends java.util.EventObject> p0, java.lang.Object p1) {
    return org.hamcrest.object.IsEventFrom.eventFrom(p0, p1);
  }

  public static org.hamcrest.Matcher<java.util.EventObject> eventFrom(java.lang.Object p0) {
    return org.hamcrest.object.IsEventFrom.eventFrom(p0);
  }

  public static <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String p0) {
    return org.hamcrest.beans.HasProperty.<T>hasProperty(p0);
  }

  public static <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String p0, org.hamcrest.Matcher<?> p1) {
    return org.hamcrest.beans.HasPropertyWithValue.<T>hasProperty(p0, p1);
  }

  public static <T> org.hamcrest.Matcher<T> samePropertyValuesAs(T param1) {
    return org.hamcrest.beans.SamePropertyValuesAs.<T>samePropertyValuesAs(param1);
  }

  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String p0, javax.xml.namespace.NamespaceContext p1) {
    return org.hamcrest.xml.HasXPath.hasXPath(p0, p1);
  }

  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String p0) {
    return org.hamcrest.xml.HasXPath.hasXPath(p0);
  }

  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String p0, org.hamcrest.Matcher<java.lang.String> p1) {
    return org.hamcrest.xml.HasXPath.hasXPath(p0, p1);
  }

  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String p0, javax.xml.namespace.NamespaceContext p1, org.hamcrest.Matcher<java.lang.String> p2) {
    return org.hamcrest.xml.HasXPath.hasXPath(p0, p1, p2);
  }

  public static <T> org.hamcrest.Matcher<java.util.Set<? extends T>> emptySet() {
    return org.projectusus.matchers.IsSetOfMatcher.<T>emptySet();
  }

  public static <T> org.hamcrest.Matcher<java.util.Set<? extends T>> setOf(T... items) {
    return org.projectusus.matchers.IsSetOfMatcher.<T>setOf(items);
  }

}
