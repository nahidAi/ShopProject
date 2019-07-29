package myshop.sky.com.shop.Activity.Activity.Model;



public class ModelOnly {
    int id;
    String image;
    String title;
    String visit;
    String price;


    public ModelOnly(int id, String image, String title, String visit, String price) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.visit = visit;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
