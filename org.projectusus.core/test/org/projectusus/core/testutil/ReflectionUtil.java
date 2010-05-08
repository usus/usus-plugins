package org.projectusus.core.testutil;

import java.lang.reflect.Field;

public class ReflectionUtil {

    private ReflectionUtil() {
        // no instantiation
    }

    private static final boolean DISABLE_SECURITY = true;

    @SuppressWarnings( { "unchecked" } )
    private static Field getField( final Class clazz, final String fieldName ) throws SecurityException, NoSuchFieldException {
        final Field declaredField = clazz.getDeclaredField( fieldName );
        return declaredField;
    }

    public static Object getValue( final Object object, final String fieldName ) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
        return getValue( object.getClass(), object, fieldName );
    }

    @SuppressWarnings( { "unchecked" } )
    public static Object getValue( final Class clazz, final Object object, final String fieldName ) throws IllegalArgumentException, IllegalAccessException, SecurityException,
            NoSuchFieldException {
        final Field field = ReflectionUtil.getField( clazz, fieldName );
        boolean oldAccessible = field.isAccessible();
        field.setAccessible( DISABLE_SECURITY );
        Object result = field.get( object );
        field.setAccessible( oldAccessible );
        return result;
    }

    public static void setValue( final Object target, final Object value, final String fieldName ) throws IllegalArgumentException, IllegalAccessException, SecurityException,
            NoSuchFieldException {
        setValue( target.getClass(), target, value, fieldName );
    }

    @SuppressWarnings( { "unchecked" } )
    public static void setValue( final Class clazz, final Object target, final Object value, final String fieldName ) throws IllegalArgumentException, IllegalAccessException,
            SecurityException, NoSuchFieldException {
        final Field field = ReflectionUtil.getField( clazz, fieldName );
        boolean oldAccessible = field.isAccessible();
        field.setAccessible( DISABLE_SECURITY );
        field.set( target, value );
        field.setAccessible( oldAccessible );
    }

    @SuppressWarnings( { "unchecked" } )
    public static void setIntValue( final Class clazz, final Object target, final int value, final String fieldName ) throws IllegalArgumentException, IllegalAccessException,
            SecurityException, NoSuchFieldException {
        final Field field = ReflectionUtil.getField( clazz, fieldName );
        boolean oldAccessible = field.isAccessible();
        field.setAccessible( DISABLE_SECURITY );
        field.setInt( target, value );
        field.setAccessible( oldAccessible );
    }
}
