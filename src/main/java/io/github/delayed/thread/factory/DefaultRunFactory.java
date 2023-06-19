package io.github.delayed.thread.factory;

import io.github.delayed.thread.AbstractConsumer;
import io.github.delayed.thread.pool.BranchDelayedThreadPool;
import io.github.delayed.properties.DelayProperties;
import io.github.delayed.bean.RunObject;
import io.github.delayed.queue.factory.DelayedQueueFactory;
import io.github.delayed.thread.AbstractRun;
import io.github.delayed.thread.MultipleRun;
import io.github.delayed.thread.SingleRun;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author 苦瓜不苦
 * @date 2023/6/19 1:22
 **/
@EnableConfigurationProperties(DelayProperties.class)
public class DefaultRunFactory<T> {

    private final DelayProperties delayProperties;

    private final DelayedQueueFactory<T> delayedQueueFactory;
    private final BranchDelayedThreadPool<T> branchDelayedThreadPool;


    public DefaultRunFactory(DelayProperties delayProperties, DelayedQueueFactory<T> delayedQueueFactory, BranchDelayedThreadPool<T> branchDelayedThreadPool) {
        this.delayProperties = delayProperties;
        this.delayedQueueFactory = delayedQueueFactory;
        this.branchDelayedThreadPool = branchDelayedThreadPool;
    }


    public AbstractRun<T> getRun(AbstractConsumer<T> abstractConsumer) {
        DelayProperties.Thread thread = delayProperties.getThread();
        if (thread.isOpen()) {
            return new MultipleRun<>(new RunObject<>(abstractConsumer, delayedQueueFactory, branchDelayedThreadPool));
        }
        return new SingleRun<>(new RunObject<>(abstractConsumer, delayedQueueFactory));
    }
}
