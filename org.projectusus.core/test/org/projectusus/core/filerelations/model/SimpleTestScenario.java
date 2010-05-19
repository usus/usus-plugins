package org.projectusus.core.filerelations.model;

import static org.projectusus.core.filerelations.model.TestServiceManager.createDescriptor;
import static org.projectusus.core.filerelations.model.TestServiceManager.createFileMock;

import org.eclipse.core.resources.IFile;

public class SimpleTestScenario {

    public static final IFile source = createFileMock();
    public static final Classname sourceClass = new Classname( "sourceClass" ); //$NON-NLS-1$ 
    public static final ClassDescriptor sourceDescriptor = createDescriptor( source, sourceClass );

    public static final IFile target = createFileMock();
    public static final Classname targetClass = new Classname( "targetClass" ); //$NON-NLS-1$
    public static final ClassDescriptor targetDescriptor = createDescriptor( target, targetClass );

    public static final IFile anotherTarget = createFileMock();
    public static final Classname anotherTargetClass = new Classname( "anotherTargetClass" ); //$NON-NLS-1$
    public static final ClassDescriptor anotherTargetDescriptor = createDescriptor( anotherTarget, anotherTargetClass );

    public static final FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
    public static final FileRelation sourceToAnotherTarget = FileRelation.of( sourceDescriptor, anotherTargetDescriptor );
    public static final FileRelation targetToAnotherTarget = FileRelation.of( targetDescriptor, anotherTargetDescriptor );
    public static final FileRelation anotherTargetToTarget = FileRelation.of( anotherTargetDescriptor, targetDescriptor );
    public static final FileRelation anotherTargetToSource = FileRelation.of( anotherTargetDescriptor, sourceDescriptor );

}
