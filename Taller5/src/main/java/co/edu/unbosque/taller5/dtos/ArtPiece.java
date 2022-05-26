package co.edu.unbosque.taller5.dtos;

import com.opencsv.bean.CsvBindByName;

public class ArtPiece {

    @CsvBindByName
    private String usernamecreator;

    @CsvBindByName
    private String title;

    @CsvBindByName
    private String price;

    @CsvBindByName
    private String filename;

    @CsvBindByName
    private String collection;

    public String getUsernamecreator() {
        return usernamecreator;
    }

    public void setUsernamecreator(String usernamecreator) {
        this.usernamecreator = usernamecreator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
