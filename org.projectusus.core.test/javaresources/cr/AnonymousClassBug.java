package cr;

public class AnonymousClassBug {

    Runnable a = new Runnable() {
        public void run() {
            methodInsideAnonymousClass();
        }

        private void methodInsideAnonymousClass() {
        }
    };
}
