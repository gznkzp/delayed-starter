package io.github.delayed;

import io.github.delayed.thread.AbstractConsumer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

/**
 * @author 苦瓜不苦
 * @date 2023/6/19 0:28
 **/

public class MainDelayedThreadPool<T> extends ThreadPoolTaskExecutor {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public MainDelayedThreadPool(List<AbstractConsumer<T>> consumerList) {
        this.threadPoolTaskExecutor = mainDelayedThreadPool(consumerList);
    }

    @Override
    public void execute(Runnable task) {
        this.threadPoolTaskExecutor.execute(task);
    }

    public ThreadPoolTaskExecutor mainDelayedThreadPool(List<AbstractConsumer<T>> consumerList) {
        int size = consumerList.size();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(size);
        executor.setMaxPoolSize(size);
        executor.setThreadNamePrefix("main-pool-");
        executor.initialize();
        return executor;

    }

}
