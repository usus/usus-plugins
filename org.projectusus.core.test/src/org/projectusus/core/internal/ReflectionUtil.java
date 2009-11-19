package org.projectusus.core.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtil {

    private ReflectionUtil() {
        // no instantiation
    }

    private static final boolean DISABLE_SECURITY = true;

    @SuppressWarnings( "unchecked" )
    private static Field getField( final Class clazz, final String fieldName, final boolean disableSecurity ) throws SecurityException, NoSuchFieldException {
        final Field declaredField = clazz.getDeclaredField( fieldName );
        declaredField.setAccessible( disableSecurity );
        return declaredField;
    }

    private static Method getMethod( final Class<? extends Object> clazz, final String methodName, final Class<?>... classes ) throws NoSuchMethodException {
        final Method method = clazz.getMethod( methodName, classes );
        method.setAccessible( DISABLE_SECURITY );
        return method;
    }

    private static Method getDeclaredMethod( final Class<? extends Object> clazz, final String methodName, final Class<?>... classes ) throws NoSuchMethodException {
        final Method method = clazz.getDeclaredMethod( methodName, classes );
        method.setAccessible( DISABLE_SECURITY );
        return method;
    }

    public static Object getValue( final Object object, final String fieldName ) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
        return getValue( object.getClass(), object, fieldName, DISABLE_SECURITY );
    }

    public static Object getValue( final Object object, final String fieldName, final boolean disableSecurity ) throws IllegalArgumentException, IllegalAccessException,
            SecurityException, NoSuchFieldException {
        return getValue( object.getClass(), object, fieldName, disableSecurity );
    }

    @SuppressWarnings( "unchecked" )
    public static Object getValue( final Class clazz, final Object object, final String fieldName ) throws IllegalArgumentException, IllegalAccessException, SecurityException,
            NoSuchFieldException {
        final Field field = ReflectionUtil.getField( clazz, fieldName, DISABLE_SECURITY );
        return field.get( object );
    }

    public static String toString( Object any ) throws IllegalArgumentException, SecurityException, IllegalAccessException {
        StringBuffer result = new StringBuffer();
        result.append( any.getClass().getName() + ": " );
        List<Field> fields = getFieldListIncludingSuperclasses( any );
        for( Field field : fields ) {
            field.setAccessible( true );
            result.append( field.getName() + " " + field.get( any ) + " | " );
        }
        result.append( "\n\r" );
        return result.toString();
    }

    @SuppressWarnings( "unchecked" )
    private static List<Field> getFieldListIncludingSuperclasses( Object any ) {
        if( any == null ) {
            return null;
        }
        List<Field> fields = new ArrayList<Field>();
        Class anyClass = any.getClass();
        do {
            fields.addAll( getFieldList( anyClass ) );
            anyClass = anyClass.getSuperclass();
        } while( anyClass != null );
        return fields;
    }

    @SuppressWarnings( "unchecked" )
    private static List<Field> getFieldList( Class anyClass ) {
        return Arrays.asList( anyClass.getDeclaredFields() );
    }

    @SuppressWarnings( "unchecked" )
    public static Object getValue( final Class clazz, final Object object, final String fieldName, final boolean disableSecurity ) throws IllegalArgumentException,
            IllegalAccessException, SecurityException, NoSuchFieldException {
        final Field field = ReflectionUtil.getField( clazz, fieldName, disableSecurity );
        return field.get( object );
    }

    public static void setValue( final Object target, final Object value, final String fieldName ) throws IllegalArgumentException, IllegalAccessException, SecurityException,
            NoSuchFieldException {
        setValue( target.getClass(), target, value, fieldName, DISABLE_SECURITY );
    }

    public static void setValue( final Object target, final Object value, final String fieldName, final boolean disableSecurity ) throws IllegalArgumentException,
            IllegalAccessException, SecurityException, NoSuchFieldException {
        setValue( target.getClass(), target, value, fieldName, disableSecurity );
    }

    @SuppressWarnings( "unchecked" )
    public static void setValue( final Class clazz, final Object target, final Object value, final String fieldName ) throws IllegalArgumentException, IllegalAccessException,
            SecurityException, NoSuchFieldException {
        final Field field = ReflectionUtil.getField( clazz, fieldName, DISABLE_SECURITY );
        field.set( target, value );
    }

    @SuppressWarnings( "unchecked" )
    public static void setIntValue( final Class clazz, final Object target, final int value, final String fieldName ) throws IllegalArgumentException, IllegalAccessException,
            SecurityException, NoSuchFieldException {
        final Field field = ReflectionUtil.getField( clazz, fieldName, DISABLE_SECURITY );
        field.setInt( target, value );
    }

    @SuppressWarnings( "unchecked" )
    public static void setValue( final Class clazz, final Object target, final Object value, final String fieldName, final boolean disableSecurity )
            throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
        final Field field = ReflectionUtil.getField( clazz, fieldName, disableSecurity );
        field.set( target, value );
    }

    @SuppressWarnings( "unchecked" )
    public static Object invokeMethod( final Object object, final Class clazz, final String methodName, final Class[] classes, final Object[] arguments ) {
        return invoke( object, methodName, classes, arguments, clazz, false );
    }

    @SuppressWarnings( "unchecked" )
    public static Object invokeMethod( final Object object, final String methodName, final Class[] classes, final Object[] arguments ) {
        final Class clazz = object.getClass();
        return invokeMethod( object, clazz, methodName, classes, arguments );
    }

    @SuppressWarnings( "unchecked" )
    public static Object invokeDeclaredMethod( final Object object, final Class clazz, final String methodName, final Class[] classes, final Object[] arguments ) {
        return invoke( object, methodName, classes, arguments, clazz, true );
    }

    @SuppressWarnings( "unchecked" )
    public static Object invokeDeclaredMethod( final Object object, final String methodName, final Class[] classes, final Object[] arguments ) {
        final Class clazz = object.getClass();
        return invokeDeclaredMethod( object, clazz, methodName, classes, arguments );
    }

    public static Object invokeMethod( final Object object, final String methodName ) {
        return invokeMethod( object, methodName, new Class[0], new Object[0] );
    }

    public static Object invokeDeclaredMethod( final Object object, final String methodName ) {
        return invokeDeclaredMethod( object, methodName, new Class[0], new Object[0] );
    }

    @SuppressWarnings( "unchecked" )
    public static Object invokeDeclaredMethod( final Object object, final Class clazz, final String methodName ) {
        return invokeDeclaredMethod( object, clazz, methodName, new Class[0], new Object[0] );
    }

    private static Object invoke( final Object object, final String methodName, final Class<?>[] classes, final Object[] arguments, final Class<?> clazz, final boolean onlyDeclared ) {
        try {
            final Method method = onlyDeclared ? getDeclaredMethod( clazz, methodName, classes ) : getMethod( clazz, methodName, classes );
            return method.invoke( object, arguments );
        } catch( final Throwable t ) {
            throw new RuntimeException( t );
        }
    }

    public static Object getInstance( final String clazz ) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass( clazz ).newInstance();
        } catch( final Throwable t ) {
            throw new RuntimeException( t );
        }
    }

    public static <T> T getInstance( final Class<T> clazz ) {
        try {
            return clazz.newInstance();
        } catch( final Throwable t ) {
            throw new RuntimeException( t );
        }
    }

    public static <T> T getInstanceByConstructor( final Class<T> clazz, final Object... constructorArgs ) {
        return getInstanceByConstructorInternal( false, clazz, constructorArgs );
    }

    public static <T> T getInstanceByDeclaredConstructor( final Class<T> clazz, final Object... constructorArgs ) {
        return getInstanceByConstructorInternal( true, clazz, constructorArgs );
    }

    private static <T> T getInstanceByConstructorInternal( final boolean onlyDeclared, final Class<T> clazz, final Object... constructorArgs ) {
        try {
            final List<Class<?>> constructorClasses = new ArrayList<Class<?>>();
            for( final Object object : constructorArgs ) {
                constructorClasses.add( object.getClass() );
            }
            final Constructor<T> constructor = onlyDeclared ? getDeclaredConstructor( clazz, constructorClasses ) : getConstructor( clazz, constructorClasses );
            constructor.setAccessible( DISABLE_SECURITY );
            return constructor.newInstance( constructorArgs );
        } catch( final Throwable t ) {
            throw new RuntimeException( t );
        }
    }

    private static <T> Constructor<T> getConstructor( final Class<T> clazz, final List<Class<?>> constructorClasses ) throws NoSuchMethodException {
        return clazz.getConstructor( constructorClasses.toArray( new Class[constructorClasses.size()] ) );
    }

    private static <T> Constructor<T> getDeclaredConstructor( final Class<T> clazz, final List<Class<?>> constructorClasses ) throws NoSuchMethodException {
        return clazz.getDeclaredConstructor( constructorClasses.toArray( new Class[constructorClasses.size()] ) );
    }

    public static Class<?> getFieldClass( final Object object, final String fieldName ) throws SecurityException, NoSuchFieldException {
        return getField( object.getClass(), fieldName, true ).getType();
    }

    public static Class<?> getNoArgMethodReturnClass( final Object object, final String methodName ) throws NoSuchMethodException {
        return getMethod( object.getClass(), methodName ).getReturnType();
    }

}
