package org.projectusus.autotestsuite.launch;

import static com.google.common.collect.Lists.transform;
import static java.util.Arrays.asList;

import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.mockito.Mockito;

import com.google.common.base.Function;

public class JavaProjectNamer implements MockInitializer<IJavaProject, String> {

    public Class<IJavaProject> classToMock() {
        return IJavaProject.class;
    }

    public void prepare( IJavaProject mock, String value ) {
        Mockito.when( mock.getElementName() ).thenReturn( value );
    }

    public static List<String> collectNames( IJavaProject... projects ) {
        return transform( asList( projects ), new Function<IJavaProject, String>() {
            public String apply( IJavaProject project ) {
                return project.getElementName();
            }
        } );
    }

}
