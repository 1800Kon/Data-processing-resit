package Kon.services;

import Kon.converter.VideogameSalesConverter;
import Kon.models.consoleSales.client.ConsoleSales;
import Kon.models.consoleSales.client.ConsoleSalesRequest;
import Kon.models.consoleSales.database.ConsoleSalesModel;
import Kon.models.videogameSales.client.VideogameSales;
import Kon.models.videogameSales.client.VideogameSalesRequest;
import Kon.models.videogameSales.database.VideogameSalesModel;
import Kon.repositories.VideogameSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class VideogameSalesService {

    @Autowired
    VideogameSalesConverter videogameSalesConverter;

    @Autowired
    VideogameSalesRepository videogameSalesRepository;

    public VideogameSales save(final VideogameSalesRequest videogameSalesRequest) {
        VideogameSalesModel videogameSalesModel = videogameSalesConverter.requestToModel(videogameSalesRequest);
        return videogameSalesConverter.modelToResponse(videogameSalesRepository.save(videogameSalesModel));
    }

    public Collection<VideogameSales> getAll() {
        return videogameSalesConverter.modelToResponse(videogameSalesRepository.findAll());
    }

    public VideogameSales update(final Integer id, final VideogameSalesRequest videogameSalesRequest) {
        VideogameSalesModel fromRequest = videogameSalesConverter.requestToModel(videogameSalesRequest);
        VideogameSalesModel toSave = videogameSalesRepository.getById(id);
        toSave.setName(fromRequest.getName());
        toSave.setPlatform(fromRequest.getPlatform());
        toSave.setReleaseYear(fromRequest.getReleaseYear());
        toSave.setGenre(fromRequest.getGenre());
        toSave.setPublisher(fromRequest.getPublisher());
        toSave.setGlobalSales(fromRequest.getGlobalSales());
        toSave.setCritScore(fromRequest.getCritScore());
        return videogameSalesConverter.modelToResponse(videogameSalesRepository.save(toSave));
    }

    public void delete(final Integer id) {
        videogameSalesRepository.deleteById(id);
    }
}
