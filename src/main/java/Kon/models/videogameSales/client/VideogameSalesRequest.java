package Kon.models.videogameSales.client;

public class VideogameSalesRequest {
    private String name;
    private String platform;
    private Integer releaseYear;
    private String Genre;
    private String publisher;
    private Float globalSales;
    private Float critScore;

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
