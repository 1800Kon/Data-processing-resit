package Kon.models.videogameSales.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VideogameSales {
    private String name;
    private String platform;
    private int releaseYear;
    private String Genre;
    private String publisher;
    private double globalSales;
    private double critScore;

    public VideogameSales() {

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
