package com.pacepal.walk.data.vo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "EventInfo")
public class EventInfo {
    @Id(autoincrement = true)
    private Long id;

    //  new SimpleDateFormat("yyyy/MM/dd HH:mm")
    @Property(nameInDb = "date")
    private String date;

    @Property(nameInDb = "steps")
    private int steps;

    @Property(nameInDb = "event")
    private String event;

    @Generated(hash = 1776004963)
    public EventInfo(Long id, String date, int steps, String event) {
        this.id = id;
        this.date = date;
        this.steps = steps;
        this.event = event;
    }

    @Generated(hash = 1265350165)
    public EventInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
