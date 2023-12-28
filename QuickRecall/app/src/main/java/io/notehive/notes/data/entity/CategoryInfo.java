package io.notehive.notes.data.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "CategoryInfo")
public class CategoryInfo {


    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "category")
    private String category;

    @Generated(hash = 744525692)
    public CategoryInfo(Long id, String category) {
        this.id = id;
        this.category = category;
    }

    @Generated(hash = 576313791)
    public CategoryInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
