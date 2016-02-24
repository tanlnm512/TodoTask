package vn.homemade.todotask.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by tanlnm on 2/23/2016.
 */
public class TaskModel implements Serializable{
    private String id;
    private String name;
    private String desc;
    private long time;
    private int priority;

    public TaskModel() {
        this.id = UUID.randomUUID().toString();
    }

    public TaskModel(String name, String desc, long time, int priority) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.desc = desc;
        this.time = time;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    @Override
    public String toString() {
        return "TaskModel{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", time=" + time +
                ", priority=" + priority +
                '}';
    }
}
