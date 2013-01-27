package ml;

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

    public void ite() {
        if( true ) {
        } else {
        }
    }

    public boolean ite_single() {
        if( true )
            return true;
        else
            return false;
    }

    public void assign() {
        int i = 0;
    }

    public void iteassign() {
        if( true ) {
            int i = 0;
        } else {
            int j = 1;
        }
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
