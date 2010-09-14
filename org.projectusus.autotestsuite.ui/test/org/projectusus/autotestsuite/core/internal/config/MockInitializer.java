package org.projectusus.autotestsuite.core.internal.config;

public interface MockInitializer<T, V> {

    Class<T> classToMock();

    void prepare( T mock, V value );
}
