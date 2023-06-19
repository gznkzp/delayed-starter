package io.github.delayed.thread.pool;

import io.github.delayed.properties.DelayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author 苦瓜不苦
 * @date 2023/6/19 1:04
 **/
@EnableConfigurationProperties(DelayProperties.class)
public class BranchDelayedThreadPool<T> extends ThreadPoolTaskExecutor {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;


    public BranchDelayedThreadPool(DelayProperties delayProperties) {
        this.threadPoolTaskExecutor = branchDelayedThreadPool(delayProperties);
    }


    @Override
    public void execute(Runnable task) {
        this.threadPoolTaskExecutor.execute(task);
    }


    private ThreadPoolTaskExecutor branchDelayedThreadPool(DelayProperties delayProperties) {
        DelayProperties.Thread thread = delayProperties.getThread();
        if (!thread.isOpen()) {
            return null;
        }
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数-线程池创建时候初始化的线程数
        executor.setCorePoolSize(thread.getCorePoolSize());
        // 最大线程数-线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(thread.getMaxPoolSize());
        // 缓冲队列-用来缓冲执行任务的队列
        executor.setQueueCapacity(thread.getQueueCapacity());
        // 允许线程的空闲时间-当超过了核心线程数之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(thread.getKeepAliveSeconds());
        // 线程池名的前缀-设置好了之后可以方便定位处理任务所在的线程池
        executor.setThreadNamePrefix("branch-pool-");
        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(thread.isWaitForTasksToCompleteOnShutdown());
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        executor.setAwaitTerminationSeconds(thread.getAwaitTerminationSeconds());
        // 初始化
        executor.initialize();
        return executor;
    }

}
