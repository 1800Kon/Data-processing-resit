package Kon.services;

import Kon.models.consoleSales.client.ConsoleSales;
import Kon.models.consoleSales.client.ConsoleSalesRequest;
import Kon.models.consoleSales.database.ConsoleSalesModel;
import Kon.repositories.ConsoleSalesRepository;
import Kon.converter.ConsoleSalesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class ConsoleSalesService {

    @Autowired
    ConsoleSalesConverter consoleSalesConverter;

    @Autowired
    ConsoleSalesRepository consoleSalesRepository;

    public ConsoleSales save(final ConsoleSalesRequest consoleSalesRequest){
        ConsoleSalesModel consoleSalesModel = consoleSalesConverter.requestToModel(consoleSalesRequest);
        return consoleSalesConverter.modelToResponse(consoleSalesRepository.save(consoleSalesModel));
    }

    public Collection<ConsoleSales> getAll() {
        return consoleSalesConverter.modelToResponse(consoleSalesRepository.findAll());
    }
}