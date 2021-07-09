package spbetu.prim.logger;

public class ConsoleLogger implements ILogger {

    public <T> void info(T message) {
        System.out.println(message);
    }

    public void clear() {
    }
}
