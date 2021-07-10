package spbetu.prim.logger;

public interface ILogger {

    <T> void info(T message);

    <T> void append(T message);

    <T> void update();

    void clear();
}
