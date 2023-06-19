package io.github.delayed.queue;

import io.github.delayed.bean.QueueObject;

/**
 * 默认延迟队列
 *
 * @author 苦瓜不苦
 * @date 2023/6/18 23:30
 **/
public class DefaultDelayedQueue<T> extends AbstractDelayedQueue<T> {


    public DefaultDelayedQueue(QueueObject queueObject) {
        super(queueObject);
    }

}
