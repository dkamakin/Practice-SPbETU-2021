package spbetu.prim.logger;

public class ConsoleLogger implements ILogger {

    private StringBuilder message;

    public ConsoleLogger() {
        this.message = new StringBuilder();
    }

    @Override
    public <T> void info(T message) {
        System.out.println(message);
    }

    @Override
    public <T> void append(T message) {
        this.message.append(message);
    }

    @Override
    public <T> void update() {
        info(message);
        this.message = new StringBuilder();
    }

    @Override
    public void clear() {
        this.message = new StringBuilder();
    }
}
