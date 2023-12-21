package com.pacepal.walk.data.vo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "StepInfo")
public class StepInfo {

    @Id(autoincrement = true)
    private Long id;

    //  new SimpleDateFormat("yyyy/MM/dd ")
    @Property(nameInDb = "date")
    private String date;

    @Property(nameInDb = "steps")
    private int steps;

    @Generated(hash = 888444120)
    public StepInfo(Long id, String date, int steps) {
        this.id = id;
        this.date = date;
        this.steps = steps;
    }

    @Generated(hash = 1153084582)
    public StepInfo() {
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



    


}
