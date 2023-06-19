package io.github.delayed.bean;

import java.io.File;
import java.io.Serializable;

/**
 * 队列实体
 *
 * @author 苦瓜不苦
 * @date 2023/6/18 23:36
 **/
public class QueueObject implements Serializable {


    /**
     * 缓存文件
     */
    private File saveFile;

    /**
     * 删除文件
     */
    private File delFile;

    public QueueObject() {
    }

    public QueueObject(File saveFile, File delFile) {
        this.saveFile = saveFile;
        this.delFile = delFile;
    }

    public File getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(File saveFile) {
        this.saveFile = saveFile;
    }

    public File getDelFile() {
        return delFile;
    }

    public void setDelFile(File delFile) {
        this.delFile = delFile;
    }


}
