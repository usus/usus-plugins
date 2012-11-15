package org.projectusus.c4j;

import java.io.PrintWriter;

public class C4JFileWriter {

    private static PrintWriter writer;

    public static void assertStatic( boolean condition, String message ) {
        if( !condition && writer != null ) {
            writer.println( message );
            writer.flush();
        }
    }

    public static void setWriter( PrintWriter printWriter ) {
        C4JFileWriter.writer = printWriter;

    }

    public static void closeWriter() {
        writer.close();
    }

}
