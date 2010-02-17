package org.projectusus.core.filerelations;

import static org.projectusus.core.filerelations.TestServiceManager.createDescriptor;
import static org.projectusus.core.filerelations.TestServiceManager.createFileMock;

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

    public static final FileRelation sourceToTarget = new FileRelation( sourceDescriptor, targetDescriptor );
    public static final FileRelation sourceToAnotherTarget = new FileRelation( sourceDescriptor, anotherTargetDescriptor );
    public static final FileRelation targetToAnotherTarget = new FileRelation( targetDescriptor, anotherTargetDescriptor );
    public static final FileRelation anotherTargetToTarget = new FileRelation( anotherTargetDescriptor, targetDescriptor );
    public static final FileRelation anotherTargetToSource = new FileRelation( anotherTargetDescriptor, sourceDescriptor );

}
