package Kon.models.consoleSales.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConsoleSales {
    private Integer id;
    private String consoleId;
    private String consoleName;
    private String manufacturer;
    private int releaseYear;
    private double sales;

    public ConsoleSales() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(String consoleId) {
        this.consoleId = consoleId;
    }

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }
}
