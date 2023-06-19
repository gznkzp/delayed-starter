package io.github.delayed.bean;

import io.github.delayed.exception.DelayedException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟实体
 *
 * @author 苦瓜不苦
 * @date 2023/6/13 16:31
 **/
public class DelayedImpl<T> implements Delayed, Serializable {

    /**
     * 延迟时间,单位:S
     */
    private final long active;

    /**
     * 任务标识
     */
    private final String id;

    /**
     * 业务数据
     */
    private final T data;


    public DelayedImpl(long active, String id, T data) {
        if (active < 0) {
            throw new DelayedException("the delay time cannot be less than 0");
        }
        if (Objects.isNull(data)) {
            throw new DelayedException("business parameters cannot be empty");
        }
        this.active = TimeUnit.NANOSECONDS.convert(active, TimeUnit.SECONDS) + System.nanoTime();
        this.id = (Objects.isNull(id) || id.length() <= 0) ? UUID.randomUUID().toString().replace("-", "") : id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public T getData() {
        return data;
    }


    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert((active - System.nanoTime()), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (d == 0) ? 0 : ((d > 0) ? 1 : -1);
    }


}
