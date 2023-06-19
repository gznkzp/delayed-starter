package io.github.delayed.properties;

import io.github.delayed.exception.DelayedException;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.io.Serializable;

/**
 * 延迟队列配置
 *
 * @author 苦瓜不苦
 * @date 2023/6/13 22:43
 **/
@ConfigurationProperties(
        prefix = "delay"
)
public class DelayProperties implements Serializable {

    /**
     * 持久化配置
     */
    private Cache cache;
    /**
     * 多线程配置
     */
    private Thread thread;


    public static class Cache implements Serializable {
        /**
         * 是否开启持久化
         */
        private boolean open = false;

        /**
         * 持久化目录
         */
        private String store;

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
            if (this.open) {
                File file = new File(this.store);
                if (!file.isDirectory()) {
                    throw new DelayedException("could not find the cache root directory");
                }
            }
        }

        @Override
        public String toString() {
            return "Cache{" +
                    "open=" + open +
                    ", store='" + store + '\'' +
                    '}';
        }
    }

    public static class Thread implements Serializable {
        /**
         * 是否开启多线程
         */
        private boolean open = false;
        /**
         * 核心线程数
         */
        private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        /**
         * 最大线程数
         */
        private int maxPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 10;
        /**
         * 空闲时间,单位(秒)
         */
        private int keepAliveSeconds = 60;
        /**
         * 缓冲队列
         */
        private int queueCapacity = 2147483647;
        /**
         * 等待所有任务都完成再继续销毁
         */
        private boolean waitForTasksToCompleteOnShutdown = false;

        /**
         * 线程池中任务的等待时间,单位(秒)
         */
        private int awaitTerminationSeconds = 0;


        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }


        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getKeepAliveSeconds() {
            return keepAliveSeconds;
        }

        public void setKeepAliveSeconds(int keepAliveSeconds) {
            this.keepAliveSeconds = keepAliveSeconds;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }

        public boolean isWaitForTasksToCompleteOnShutdown() {
            return waitForTasksToCompleteOnShutdown;
        }

        public void setWaitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
            this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
        }


        public int getAwaitTerminationSeconds() {
            return awaitTerminationSeconds;
        }

        public void setAwaitTerminationSeconds(int awaitTerminationSeconds) {
            this.awaitTerminationSeconds = awaitTerminationSeconds;
        }

        @Override
        public String toString() {
            return "Thread{" +
                    "open=" + open +
                    ", corePoolSize=" + corePoolSize +
                    ", maxPoolSize=" + maxPoolSize +
                    ", keepAliveSeconds=" + keepAliveSeconds +
                    ", queueCapacity=" + queueCapacity +
                    ", waitForTasksToCompleteOnShutdown=" + waitForTasksToCompleteOnShutdown +
                    ", awaitTerminationSeconds=" + awaitTerminationSeconds +
                    '}';
        }
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public String toString() {
        return "DelayProperties{" +
                "cache=" + cache +
                ", thread=" + thread +
                '}';
    }
}
