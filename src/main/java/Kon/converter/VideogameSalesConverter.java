package Kon.converter;

import Kon.models.videogameSales.client.VideogameSales;
import Kon.models.videogameSales.client.VideogameSalesRequest;
import Kon.models.videogameSales.database.VideogameSalesModel;
import org.springframework.stereotype.Component;

@Component
public class VideogameSalesConverter implements ModelConverter<VideogameSalesRequest, VideogameSalesModel, VideogameSales>{

    @Override
    public VideogameSalesModel requestToModel(VideogameSalesRequest request) {
        VideogameSalesModel videogameSalesModel = new VideogameSalesModel();
        videogameSalesModel.setName(request.getName());
        videogameSalesModel.setPlatform(request.getPlatform());
        videogameSalesModel.setReleaseYear(request.getReleaseYear());
        videogameSalesModel.setGenre(request.getGenre());
        videogameSalesModel.setPublisher(request.getPublisher());
        videogameSalesModel.setGlobalSales(request.getGlobalSales());
        videogameSalesModel.setCritScore(request.getCritScore());
        return videogameSalesModel;
    }

    @Override
    public VideogameSales modelToResponse(VideogameSalesModel videogameSalesModel) {
        VideogameSales videogameSales = new VideogameSales();
        videogameSales.setName(videogameSalesModel.getName());
        videogameSales.setPlatform(videogameSalesModel.getPlatform());
        videogameSales.setReleaseYear(videogameSalesModel.getReleaseYear());
        videogameSales.setGenre(videogameSalesModel.getGenre());
        videogameSales.setPublisher(videogameSalesModel.getPublisher());
        videogameSales.setGlobalSales(videogameSalesModel.getGlobalSales());
        videogameSales.setCritScore(videogameSalesModel.getCritScore());
        return videogameSales;
    }
}
