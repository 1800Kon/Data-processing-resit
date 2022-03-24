package Kon.models.videogameSales.database;

import javax.persistence.*;

@Entity
@Table(name = "videogame_sales_5")
public class VideogameSalesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private String platform;
    @Column
    private Integer releaseYear;
    @Column
    private String genre;
    @Column
    private String publisher;
    @Column
    private Float globalSales;
    @Column
    private Float critScore;

    public VideogameSalesModel() {

    }

    public Integer getId() {
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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Float getGlobalSales() {
        return globalSales;
    }

    public void setGlobalSales(Float globalSales) {
        this.globalSales = globalSales;
    }

    public Float getCritScore() {
        return critScore;
    }

    public void setCritScore(Float critScore) {
        this.critScore = critScore;
    }
}
