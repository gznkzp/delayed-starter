package io.github.delayed.thread;

import io.github.delayed.bean.DelayedImpl;
import io.github.delayed.queue.AbstractDelayedQueue;

/**
 * @author 苦瓜不苦
 * @date 2023/6/18 23:45
 **/
public abstract class AbstractConsumer<T> {

    private AbstractDelayedQueue<T> abstractDelayedQueue;

    /**
     * 初始化延迟队列
     *
     * @param abstractDelayedQueue 延迟队列
     */
    void setQueue(AbstractDelayedQueue<T> abstractDelayedQueue) {
        this.abstractDelayedQueue = abstractDelayedQueue;
    }


    /**
     * 获取延迟任务
     *
     * @return 延迟数据
     * @throws InterruptedException
     */
    DelayedImpl<T> take() throws InterruptedException {
        return abstractDelayedQueue.take();
    }


    /**
     * 推送延迟任务
     *
     * @param active 延迟时间,单位:S
     * @param data   业务数据
     * @return 任务标识
     */
    public String put(long active, T data) {
        DelayedImpl<T> delayed = new DelayedImpl<>(active, data);
        abstractDelayedQueue.put(delayed);
        return delayed.getId();
    }


    /**
     * 消费任务
     *
     * @param delayed 延迟数据
     */
    protected abstract void execute(DelayedImpl<T> delayed);

    /**
     * 失败回调
     *
     * @param delayed   延迟数据
     * @param exception 异常对象
     */
    protected void failure(DelayedImpl<T> delayed, Exception exception) {

    }

    /**
     * 成功回调
     *
     * @param delayed 延迟数据
     */
    protected void success(DelayedImpl<T> delayed) {

    }

    /**
     * 前置通知
     *
     * @param delayed 延迟数据
     */
    protected void before(DelayedImpl<T> delayed) {

    }


}
