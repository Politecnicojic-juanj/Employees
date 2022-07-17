package co.edu.poli.ces3.employees.entities;

public class Game {

    protected String id;

    private String image;

    private String name;

    private String description;

    private String author;

    private String calification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCalification() {
        return calification;
    }

    public void setCalification(String calification) {
        this.calification = calification;
    }

    public Game(String id, String image, String name, String description, String author, String calification) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.author = author;
        this.calification = calification;
    }


}
