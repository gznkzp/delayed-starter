package io.github.delayed;

import io.github.delayed.properties.DelayProperties;
import io.github.delayed.thread.AbstractConsumer;
import io.github.delayed.thread.factory.DefaultRunFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;
import java.util.Objects;

/**
 * @author 苦瓜不苦
 * @date 2023/6/18 23:49
 **/
@EnableConfigurationProperties(DelayProperties.class)
public class InitializeDelayedQueue<T> implements InitializingBean {

    private final List<AbstractConsumer<T>> abstractConsumerList;

    private final DefaultRunFactory<T> defaultRunFactory;

    private final MainDelayedThreadPool<T> mainDelayedThreadPool;


    public InitializeDelayedQueue(List<AbstractConsumer<T>> abstractConsumerList,
                                  DefaultRunFactory<T> defaultRunFactory,
                                  MainDelayedThreadPool<T> mainDelayedThreadPool) {
        this.abstractConsumerList = abstractConsumerList;
        this.defaultRunFactory = defaultRunFactory;
        this.mainDelayedThreadPool = mainDelayedThreadPool;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (Objects.nonNull(abstractConsumerList)) {
            abstractConsumerList.forEach(abstractConsumer -> mainDelayedThreadPool.execute(defaultRunFactory.getRun(abstractConsumer)));
        }
    }


}
