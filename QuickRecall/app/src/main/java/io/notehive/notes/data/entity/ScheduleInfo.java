package io.notehive.notes.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "ScheduleInfo")
public class ScheduleInfo {


    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "title")
    private String title;


    @Property(nameInDb = "start_date")
    private String startDate;


    @Property(nameInDb = "start_time")
    private String startTime;


    @Property(nameInDb = "end_date")
    private String endDate;


    @Property(nameInDb = "end_time")
    private String endTime;

    @Property(nameInDb = "category")
    private String category;

    @Property(nameInDb = "isCalendar")
    private int isCalendar;


    @Property(nameInDb = "remark")
    private String remark;


    @Generated(hash = 1689625053)
    public ScheduleInfo(Long id, String title, String startDate, String startTime,
            String endDate, String endTime, String category, int isCalendar,
            String remark) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.category = category;
        this.isCalendar = isCalendar;
        this.remark = remark;
    }


    @Generated(hash = 57358373)
    public ScheduleInfo() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getStartDate() {
        return this.startDate;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getStartTime() {
        return this.startTime;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getEndDate() {
        return this.endDate;
    }


    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getEndTime() {
        return this.endTime;
    }


    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getCategory() {
        return this.category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public int getIsCalendar() {
        return this.isCalendar;
    }


    public void setIsCalendar(int isCalendar) {
        this.isCalendar = isCalendar;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }



}
