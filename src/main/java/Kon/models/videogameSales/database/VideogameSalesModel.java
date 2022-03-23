package Kon.models.videogameSales.database;

import javax.persistence.*;

@Entity
@Table(name = "videogame_sales")
public class VideogameSalesModel {
    @Id
    @GeneratedValue
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private String platform;
    @Column
    private int releaseYear;
    @Column
    private String Genre;
    @Column
    private String publisher;
    @Column
    private double globalSales;
    @Column
    private double critScore;

    public VideogameSalesModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getGlobalSales() {
        return globalSales;
    }

    public void setGlobalSales(double globalSales) {
        this.globalSales = globalSales;
    }

    public double getCritScore() {
        return critScore;
    }

    public void setCritScore(double critScore) {
        this.critScore = critScore;
    }
}
