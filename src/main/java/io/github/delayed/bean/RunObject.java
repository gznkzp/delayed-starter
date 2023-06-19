package io.github.delayed.bean;

import io.github.delayed.thread.AbstractConsumer;
import io.github.delayed.thread.pool.BranchDelayedThreadPool;
import io.github.delayed.queue.factory.DelayedQueueFactory;

import java.io.Serializable;

/**
 * 多线程实体
 *
 * @author 苦瓜不苦
 * @date 2023/6/19 1:18
 **/
public class RunObject<T> implements Serializable {
    /**
     * 消费者
     */
    private AbstractConsumer<T> abstractConsumer;
    /**
     * 延迟队列工厂
     */
    private DelayedQueueFactory<T> delayedQueueFactory;
    /**
     * 线程池
     */
    private BranchDelayedThreadPool<T> branchDelayedThreadPool;

    public RunObject() {
    }


    public RunObject(AbstractConsumer<T> abstractConsumer, DelayedQueueFactory<T> delayedQueueFactory) {
        this.abstractConsumer = abstractConsumer;
        this.delayedQueueFactory = delayedQueueFactory;
    }

    public RunObject(AbstractConsumer<T> abstractConsumer, DelayedQueueFactory<T> delayedQueueFactory, BranchDelayedThreadPool<T> branchDelayedThreadPool) {
        this.abstractConsumer = abstractConsumer;
        this.delayedQueueFactory = delayedQueueFactory;
        this.branchDelayedThreadPool = branchDelayedThreadPool;
    }

    public AbstractConsumer<T> getAbstractConsumer() {
        return abstractConsumer;
    }

    public void setAbstractConsumer(AbstractConsumer<T> abstractConsumer) {
        this.abstractConsumer = abstractConsumer;
    }

    public DelayedQueueFactory<T> getDelayedQueueFactory() {
        return delayedQueueFactory;
    }

    public void setDelayedQueueFactory(DelayedQueueFactory<T> delayedQueueFactory) {
        this.delayedQueueFactory = delayedQueueFactory;
    }

    public BranchDelayedThreadPool<T> getBranchDelayedThreadPool() {
        return branchDelayedThreadPool;
    }

    public void setBranchDelayedThreadPool(BranchDelayedThreadPool<T> branchDelayedThreadPool) {
        this.branchDelayedThreadPool = branchDelayedThreadPool;
    }
}
