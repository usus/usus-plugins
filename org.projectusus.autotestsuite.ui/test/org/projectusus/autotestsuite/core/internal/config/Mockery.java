package org.projectusus.autotestsuite.core.internal.config;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Collection;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class Mockery implements MethodRule {

    private final Multimap<Class<?>, MockInitializer<?, ?>> initializers = LinkedListMultimap.create();

    public Statement apply( final Statement base, FrameworkMethod method, final Object target ) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                initMocks( target );
                base.evaluate();
            }
        };
    }

    public Mockery with( MockInitializer<?, ?> initializer ) {
        initializers.put( initializer.classToMock(), initializer );
        return this;
    }

    public <T, V> T mock( Class<T> clazz, V value ) {
        T mock = mock( clazz );
        Collection<MockInitializer<?, ?>> classInitializers = initializers.get( clazz );
        if( classInitializers != null ) {
            for( MockInitializer<?, ?> initializer : classInitializers ) {
                cast( initializer ).prepare( mock, value );
            }
        }
        return mock;
    }

    public <T> T mock( Class<T> clazz ) {
        return Mockito.mock( clazz );
    }

    @SuppressWarnings( "unchecked" )
    private <T, V> MockInitializer<T, V> cast( MockInitializer<?, ?> initializer ) {
        return ((MockInitializer<T, V>)initializer);
    }
}
