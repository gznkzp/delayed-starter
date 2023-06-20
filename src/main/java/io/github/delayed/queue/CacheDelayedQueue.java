package io.github.delayed.queue;

import io.github.delayed.bean.DelayedImpl;
import io.github.delayed.bean.QueueObject;
import io.github.delayed.exception.DelayedException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * 持久化延迟队列
 *
 * @author 苦瓜不苦
 * @date 2023/6/18 23:33
 **/
public class CacheDelayedQueue<T> extends AbstractDelayedQueue<T> {

    private final static Lock WRITE_LOCK = new ReentrantReadWriteLock().writeLock();


    public CacheDelayedQueue(QueueObject queueObject) {
        super(queueObject);
        loadCacheFile();
    }


    @Override
    public void put(DelayedImpl<T> delayed) {
        QueueObject queueObject = getQueueObject();
        writeObject(queueObject.getSaveFile(), delayed);
        super.put(delayed);
    }


    @Override
    public DelayedImpl<T> take() throws InterruptedException {
        DelayedImpl<T> delayed = super.take();
        QueueObject queueObject = getQueueObject();
        writeObject(queueObject.getDelFile(), delayed);
        return delayed;
    }


    private void writeObject(File file, DelayedImpl<T> delayed) {
        WRITE_LOCK.lock();
        try (FileOutputStream fos = new FileOutputStream(file, true);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            FileChannel channel = fos.getChannel();
            channel.truncate(channel.position() - 4);
            oos.writeObject(delayed);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            WRITE_LOCK.unlock();
        }
    }


    private void loadCacheFile() {
        QueueObject queueObject = getQueueObject();
        spannedFile(queueObject.getSaveFile());
        spannedFile(queueObject.getDelFile());
        List<DelayedImpl<T>> delDelayedList = readObject(queueObject.getDelFile());
        List<DelayedImpl<T>> saveDelayedList = readObject(queueObject.getSaveFile());
        if (delDelayedList.isEmpty() && saveDelayedList.isEmpty()) {
            return;
        }
        if (!queueObject.getSaveFile().delete()) {
            throw new DelayedException("failed to delete cache file");
        }
        if (!queueObject.getDelFile().delete()) {
            throw new DelayedException("failed to delete cache file");
        }
        spannedFile(queueObject.getSaveFile());
        spannedFile(queueObject.getDelFile());
        List<String> idList = delDelayedList.stream().map(DelayedImpl::getId).collect(Collectors.toList());
        saveDelayedList.stream().filter(delayed -> !idList.contains(delayed.getId())).forEach(this::put);
    }


    @SuppressWarnings("unchecked")
    private List<DelayedImpl<T>> readObject(File file) {
        List<DelayedImpl<T>> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (fis.available() > 0) {
                try {
                    Object o = ois.readObject();
                    if (o instanceof DelayedImpl) {
                        list.add((DelayedImpl<T>) o);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new DelayedException("failed to load cache file", e);
        }
        return list;
    }


    private void spannedFile(File file) {
        if (file.exists()) {
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
        } catch (IOException e) {
            throw new DelayedException("cache file creation failed", e);
        }
    }


}
