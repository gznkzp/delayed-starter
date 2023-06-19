package io.github.delayed.exception;

/**
 * 延迟队列异常类
 *
 * @author 苦瓜不苦
 * @date 2023/6/18 18:30
 **/
public class DelayedException extends RuntimeException {

    public DelayedException(String message) {
        super(message);
    }

    public DelayedException(Throwable cause) {
        super(cause);
    }

    public DelayedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DelayedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
