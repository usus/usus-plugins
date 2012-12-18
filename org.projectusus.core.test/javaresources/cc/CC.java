package cc;

import java.util.List;

public class CC {

    public void empty() {
    }

    public void oneWhile() {
        while( true ) {
        }
    }

    public void threeCase( int i ) {
        switch( i ) {
        case 0:
        case 1:
        default:
        }
    }

    public void twoLogical() {
        boolean y = true & false | true;
    }

    public void twoBitwise() {
        int x = 1 & 0 | 1;
    }

    public void twoIfOneElse() {
        if( true ) {
        }
        if( false ) {
        } else {
        }
    }

    public void oneForeach( List l ) {
        for( Object c : l )
            ;
    }

    public void oneFor() {
        for( ;; )
            ;
    }

    public void oneDo() {
        do {
        } while( true );
    }

    public void oneConditional() {
        int x = true ? 0 : 1;
    }

    public void conditionalAnd_conditionalOr() {
        boolean y = true && false || true;
    }

    public void threeConditionalAnd() {
        boolean y = true && false && true && false;
    }

    public void oneTryCatch() {
        try {
        } catch( Exception e ) {
        }
    }

    public void outerMethod() {
        new Object() {
            public void innerMethod() {
            }
        };
    }

    {
        int x = true ? 0 : 1;
    }
}
