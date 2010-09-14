package org.projectusus.autotestsuite.core.internal.config;

import org.eclipse.jdt.internal.junit.launcher.ITestKind;
import org.mockito.Mockito;

@SuppressWarnings( "restriction" )
final class TestKindIdInitializer implements MockInitializer<ITestKind, String> {
    public Class<ITestKind> classToMock() {
        return ITestKind.class;
    }

    public void prepare( ITestKind mock, String value ) {
        Mockito.when( mock.getId() ).thenReturn( value );
    }
}