package io.github.delayed.queue.factory;

import io.github.delayed.thread.AbstractConsumer;
import io.github.delayed.properties.DelayProperties;
import io.github.delayed.bean.QueueObject;
import io.github.delayed.queue.AbstractDelayedQueue;
import io.github.delayed.queue.CacheDelayedQueue;
import io.github.delayed.queue.DefaultDelayedQueue;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.File;

/**
 * @author 苦瓜不苦
 * @date 2023/6/19 0:07
 **/
@EnableConfigurationProperties(DelayProperties.class)
public class DelayedQueueFactory<T> {

    private final DelayProperties delayProperties;

    public DelayedQueueFactory(DelayProperties delayProperties) {
        this.delayProperties = delayProperties;
    }


    public AbstractDelayedQueue<T> getDelayedQueue(AbstractConsumer<T> abstractConsumer) {
        DelayProperties.Cache cache = delayProperties.getCache();
        if (cache.isOpen()) {
            String simpleName = abstractConsumer.getClass().getSimpleName();
            return new CacheDelayedQueue<>(new QueueObject(new File(cache.getStore(), (simpleName + "Save")), new File(cache.getStore(), (simpleName + "Del"))));
        }
        return new DefaultDelayedQueue<>(new QueueObject());
    }

}
