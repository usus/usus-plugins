package org.projectusus.autotestsuite.launch;

public interface MockInitializer<T, V> {

    Class<T> classToMock();

    void prepare( T mock, V value );

}
