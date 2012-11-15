package org.projectusus.core.filerelations.model;

import java.util.Set;

import org.eclipse.jdt.core.IJavaElement;
import org.projectusus.c4j.C4JFileWriter;
import org.projectusus.c4j.UsusContractBase;

public class PackagenameContract extends UsusContractBase<Packagename> {

    private int numberOfClasses;
    private boolean packageContainsClass;

    private String targetString() {
        return " Target: " + m_target;
    }

    public PackagenameContract( Packagename target ) {
        super( target );
    }

    public void classInvariant() {
        assertThat( Packagename.getAll().contains( m_target ), "CONTRADICTION?! Element is contained in list of all:" + targetString() );
    }

    public static void pre_clear() {
        // TODO no pre-condition identified yet
    }

    public static void post_clear() {
        C4JFileWriter.assertStatic( Packagename.getAll().isEmpty(), "Clear removes all Packagename objects." );
    }

    public static void pre_of( String name, IJavaElement javaElement ) {
        // TODO Auto-generated pre-condition
        C4JFileWriter.assertStatic( name != null, "name_not_null" );
        C4JFileWriter.assertStatic( javaElement != null, "javaElement_not_null" );
    }

    public static void post_of( String name, IJavaElement javaElement ) {
        Packagename returnValue = (Packagename)getReturnValue();
        C4JFileWriter.assertStatic( Packagename.getAll().contains( returnValue ), "List of all contains Packagename " + returnValue );
    }

    public static void pre_getAll() {
        // TODO no pre-condition identified yet
    }

    public static void post_getAll() {
        Set<Packagename> returnValue = (Set<Packagename>)getReturnValue();
        // TODO no post-condition identified yet
    }

    public void pre_addClass( ClassDescriptor clazz ) {
        assertThat( clazz != null, "clazz_not_null" );
        numberOfClasses = m_target.numberOfClasses();
        packageContainsClass = m_target.containsClass( clazz );
    }

    public void post_addClass( ClassDescriptor clazz ) {
        if( packageContainsClass ) {
            assertThat( numberOfClasses == m_target.numberOfClasses(), "Adding an already contained class does not change the class count for " + targetString() );
        } else {
            assertThat( numberOfClasses + 1 == m_target.numberOfClasses(), "Adding an unknown class increases the class count by 1 for " + targetString() );
        }

        assertThat( m_target.containsClass( clazz ), "Added class is now contained in package " + targetString() );
    }

    public void pre_removeClass( ClassDescriptor clazz ) {
        assertThat( clazz != null, "clazz_not_null" );
        numberOfClasses = m_target.numberOfClasses();
    }

    public void post_removeClass( ClassDescriptor clazz ) {
        assertThat( numberOfClasses - 1 == m_target.numberOfClasses(), "Removing a class reduces the class count by 1 for " + targetString() );
        assertThat( !m_target.containsClass( clazz ), "Removed class is no longer contained in package" + targetString() );

        if( m_target.numberOfClasses() == 0 ) {
            assertThat( !Packagename.getAll().contains( m_target ), "CONTRADICTION?! Packages w/o classes are removed from list" + targetString() );
        }
    }

    public void pre_containsClass( ClassDescriptor clazz ) {
        // TODO Auto-generated pre-condition
        assertThat( clazz != null, "clazz_not_null" );
    }

    public void post_containsClass( ClassDescriptor clazz ) {
        boolean returnValue = ((Boolean)getReturnValue()).booleanValue();
        // TODO no post-condition identified yet
    }

    public void pre_numberOfClasses() {
        // TODO no pre-condition identified yet
    }

    public void post_numberOfClasses() {
        int returnValue = ((Integer)getReturnValue()).intValue();
        // TODO no post-condition identified yet
    }

    public void pre_getJavaElement() {
        // TODO no pre-condition identified yet
    }

    public void post_getJavaElement() {
        IJavaElement returnValue = (IJavaElement)getReturnValue();
        // TODO no post-condition identified yet
    }

    public void pre_getRelationTo( Packagename target ) {
        // TODO Auto-generated pre-condition
        assertThat( target != null, "target_not_null" );
    }

    public void post_getRelationTo( Packagename target ) {
        Relation<Packagename> returnValue = (Relation<Packagename>)getReturnValue();
        // TODO no post-condition identified yet
    }

}
