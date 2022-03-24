package Kon.converter;

import Kon.models.consoleSales.client.ConsoleSales;
import Kon.models.consoleSales.client.ConsoleSalesRequest;
import Kon.models.consoleSales.database.ConsoleSalesModel;
import org.springframework.stereotype.Component;

@Component
public class ConsoleSalesConverter implements ModelConverter<ConsoleSalesRequest, ConsoleSalesModel, ConsoleSales> {

    @Override
    public ConsoleSalesModel requestToModel(ConsoleSalesRequest request) {
        ConsoleSalesModel consoleSalesModel = new ConsoleSalesModel();
        consoleSalesModel.setConsoleId(request.getConsoleId());
        consoleSalesModel.setConsoleName(request.getConsoleName());
        consoleSalesModel.setManufacturer(request.getManufacturer());
        consoleSalesModel.setReleaseYear(request.getReleaseYear());
        consoleSalesModel.setSales(request.getSales());
        return consoleSalesModel;
    }

    @Override
    public ConsoleSales modelToResponse(ConsoleSalesModel model) {
        ConsoleSales consoleSales = new ConsoleSales();
        consoleSales.setConsoleId(model.getConsoleId());
        consoleSales.setConsoleName(model.getConsoleName());
        consoleSales.setManufacturer(model.getManufacturer());
        consoleSales.setReleaseYear(model.getReleaseYear());
        consoleSales.setSales(model.getSales());
        return consoleSales;
    }
}
