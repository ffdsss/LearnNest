package org.spendwise.coinkeep.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "accountInfo")
public class AccountInfo {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "money")
    private String money;

    @Property(nameInDb = "category")
    private String category;
    
    @Property(nameInDb = "is_income")
    private String isIncome;

    @Property(nameInDb = "remark")
    private String remark;


    @Property(nameInDb = "date")
    private String date;

    @Property(nameInDb = "time")
    private String time;

    @Generated(hash = 318704065)
    public AccountInfo(Long id, String money, String category, String isIncome,
            String remark, String date, String time) {
        this.id = id;
        this.money = money;
        this.category = category;
        this.isIncome = isIncome;
        this.remark = remark;
        this.date = date;
        this.time = time;
    }

    @Generated(hash = 1230968834)
    public AccountInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsIncome() {
        return this.isIncome;
    }

    public void setIsIncome(String isIncome) {
        this.isIncome = isIncome;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "id=" + id +
                ", money='" + money + '\'' +
                ", category='" + category + '\'' +
                ", isIncome='" + isIncome + '\'' +
                ", remark='" + remark + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
