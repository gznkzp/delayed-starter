package io.github.delayed.thread;

import io.github.delayed.thread.pool.BranchDelayedThreadPool;
import io.github.delayed.bean.DelayedImpl;
import io.github.delayed.bean.RunObject;

/**
 * @author 苦瓜不苦
 * @date 2023/6/19 1:03
 **/
public class MultipleRun<T> extends AbstractRun<T> {

    private final BranchDelayedThreadPool<T> branchDelayedThreadPool;

    public MultipleRun(RunObject<T> runObject) {
        super(runObject);
        this.branchDelayedThreadPool = runObject.getBranchDelayedThreadPool();
    }


    @Override
    protected void operation(DelayedImpl<T> delayed) {
        branchDelayedThreadPool.execute(() -> super.operation(delayed));
    }
}
