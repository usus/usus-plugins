package pde;

import java.util.List;

public class ML {

    static int i;

    static {
        i = 0;
    }

    int j;

    {
        j = 0;
    }

    public void empty() {
    }

    public boolean ite_block() {
        if( true ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ite_single() {
        if( true )
            return true;
        else
            return false;
    }

    public boolean for_block() {
        for( ;; ) {
            return true;
        }
    }

    public boolean for_single() {
        for( ;; )
            return true;
    }

    public void foreach_single( List l ) {
        for( Object c : l )
            ;
    }

    public void foreach_block( List l ) {
        for( Object c : l ) {
            ;
        }
    }

    public boolean do_block() {
        do {
            return false;
        } while( true );
    }

    public boolean do_single() {
        do
            return false;
        while( true );
    }

    public boolean while_block() {
        while( true ) {
            return false;
        }
    }

    public boolean while_single() {
        while( true )
            return false;
    }

    public void switchCase_block( int i ) {
        switch( i ) {
        case 0: {
            int j = 1;
            break;
        }
        case 1: {
            int k = 1;
            k = 2;
            break;
        }
        default: {
            int l = 3;
            l = 4;
            l = 5;
            break;
        }
        }
    }

    public void switchCase_single( int i ) {
        switch( i ) {
        case 0:
            int j = 1;
            break;
        case 1:
            int k = 1;
            k = 2;
            break;
        default:
            int l = 3;
            l = 4;
            l = 5;
            break;
        }
    }

    public void tryCatch() {
        try {
            int a = 0;
        } catch( Exception e ) {
            int b = 1;
        }
    }

    public void assign() {
        int i = 0;
    }

    public void anon() {
        Object o = new Object() {
        };
    }

    public void anonWithMeth() {
        Object o = new Object() {
            public void anonWithMeth_1() {
            }
        };
    }

    public void anonWith3VarsBefore() {
        int i = 1;
        i = 2;
        i = 3;

        Object o = new Object() {
        };
    }

    public void anonWithMethAnd3VarsBefore() {
        int i = 1;
        if( true ) {
            i = 2;
        } else {
            i = 3;
        }

        Object o = new Object() {
            public void anonWithMethAnd3VarsBefore_1() {
            }
        };
    }

    public void anonWith3VarsAfter() {
        Object o = new Object() {
        };

        int i = 1;
        i = 2;
        i = 3;
    }

    public void anonWithMethAnd3VarsAfter() {
        Object o = new Object() {
            public void anonWithMethAnd3VarsAfter_1() {
            }
        };

        int i = 1;
        if( true ) {
            i = 2;
        } else {
            i = 3;
        }
    }

    public void anonWith4Meth() {
        int i = 0;

        Object o = new Object() {
            public void anonWith4Meth_1() {
                int i = 0;
            }

            public void anonWith4Meth_2() {
                int i = 0;
            }

            public void anonWith4Meth_3() {
                int i = 0;
            }

            public void anonWith4Meth_4() {
                int i = 0;
            }
        };

        int j = 0;
    }
}

class ML2 {

    public void assignInML2() {
        int i = 0;
    }
}

interface MLInterface {
    public void m1();

    public void m2();
}
