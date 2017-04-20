package nioserver.runners;

public abstract class Runner implements Runnable{

    protected volatile boolean running = true;

    public void stop() {
        running = false;
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
