package spbetu.prim.logger;

public interface ILogger {

    <T> void info(T message);

    void clear();
}
