package myshop.sky.com.shop.Activity.Activity.Model;



public class ModelCategory {
    //چون میخواییم از جیسان استفاده کنیم حتما این فیلدها همنام ستون های جدول در دیتابیسمون باشه
    int id;
    String titlecategory;
    String image;

    public ModelCategory(int id, String titlecategory, String image) {
        this.id = id;
        this.titlecategory = titlecategory;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitlecategory() {
        return titlecategory;
    }

    public void setTitlecategory(String titlecategory) {
        this.titlecategory = titlecategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
