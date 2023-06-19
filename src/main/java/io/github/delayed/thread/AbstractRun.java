package io.github.delayed.thread;

import io.github.delayed.bean.DelayedImpl;
import io.github.delayed.bean.RunObject;

/**
 * @author 苦瓜不苦
 * @date 2023/6/19 1:11
 **/
public abstract class AbstractRun<T> implements Runnable {

    private final AbstractConsumer<T> abstractConsumer;

    public AbstractRun(RunObject<T> runObject) {
        this.abstractConsumer = runObject.getAbstractConsumer();
        abstractConsumer.setQueue(runObject.getDelayedQueueFactory().getDelayedQueue(abstractConsumer));
    }

    @Override
    public void run() {
        do {
            operation(take());
        } while (true);
    }

    private DelayedImpl<T> take() {
        try {
            return abstractConsumer.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    protected void operation(DelayedImpl<T> delayed) {
        try {
            abstractConsumer.before(delayed);
            abstractConsumer.execute(delayed);
            abstractConsumer.success(delayed);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                abstractConsumer.failure(delayed, e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
