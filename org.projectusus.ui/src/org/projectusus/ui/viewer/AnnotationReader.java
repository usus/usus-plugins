package org.projectusus.ui.viewer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.projectusus.ui.internal.UsusUIPlugin;

class AnnotationReader {
    private final IColumnDesc<?> columnDescEnumValue;

    AnnotationReader( IColumnDesc<?> enumValue ) {
        this.columnDescEnumValue = enumValue;
    }

    UsusTreeColumn compute() {
        UsusTreeColumn result = null;
        try {
            Field field = loadField();
            for( Annotation annotation : field.getAnnotations() ) {
                if( annotation instanceof UsusTreeColumn ) {
                    result = (UsusTreeColumn)annotation;
                }
            }
        } catch( Exception ex ) {
            UsusUIPlugin.getDefault().log( ex );
        }
        return result;
    }

    private Field loadField() throws NoSuchFieldException {
        Class<?> enumClass = columnDescEnumValue.getClass();
        if( enumClass.isAnonymousClass() ) {
            enumClass = enumClass.getEnclosingClass();
        }
        return enumClass.getDeclaredField( columnDescEnumValue.toString() );
    }
}
