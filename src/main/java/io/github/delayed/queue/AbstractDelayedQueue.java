package io.github.delayed.queue;

import io.github.delayed.bean.DelayedImpl;
import io.github.delayed.bean.QueueObject;

import java.util.concurrent.DelayQueue;

/**
 * @author 苦瓜不苦
 * @date 2023/6/18 23:29
 **/
public abstract class AbstractDelayedQueue<T> extends DelayQueue<DelayedImpl<T>> {

    private final QueueObject queueObject;


    public AbstractDelayedQueue(QueueObject queueObject) {
        super();
        this.queueObject = queueObject;
    }


    public QueueObject getQueueObject() {
        return queueObject;
    }

}
