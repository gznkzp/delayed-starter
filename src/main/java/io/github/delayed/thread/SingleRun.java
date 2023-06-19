package io.github.delayed.thread;

import io.github.delayed.bean.RunObject;

/**
 * @author 苦瓜不苦
 * @date 2023/6/19 0:45
 **/
public class SingleRun<T> extends AbstractRun<T> {


    public SingleRun(RunObject<T> runObject) {
        super(runObject);
    }


}

