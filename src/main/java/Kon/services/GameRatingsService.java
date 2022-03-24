package Kon.services;

import Kon.converter.GameRatingsConverter;
import Kon.models.gameRatings.client.GameRatings;
import Kon.models.gameRatings.client.GameRatingsRequest;
import Kon.models.gameRatings.database.GameRatingsModel;
import Kon.repositories.GameRatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class GameRatingsService {

    @Autowired
    GameRatingsConverter gameRatingsConverter;

    @Autowired
    GameRatingsRepository gameRatingsRepository;

    public GameRatings save(final GameRatingsRequest gameRatingsRequest){

        GameRatingsModel gameRatingsModel = gameRatingsConverter.requestToModel(gameRatingsRequest);
        return gameRatingsConverter.modelToResponse(gameRatingsRepository.save(gameRatingsModel));
    }

    public Collection<GameRatings> getAll() {
        return gameRatingsConverter.modelToResponse(gameRatingsRepository.findAll());
    }

    public GameRatings update(final Integer id, final GameRatingsRequest gameRatingsRequest) {
        GameRatingsModel fromRequest = gameRatingsConverter.requestToModel(gameRatingsRequest);
        GameRatingsModel toSave = gameRatingsRepository.getById(id);
        toSave.setName(fromRequest.getName());
        toSave.setPlatform(fromRequest.getPlatform());
        toSave.setReleaseYear(fromRequest.getReleaseYear());
        toSave.setScore(fromRequest.getScore());
        toSave.setUserScore(fromRequest.getUserScore());
        toSave.setDeveloper(fromRequest.getDeveloper());
        toSave.setGenre(fromRequest.getGenre());
        return gameRatingsConverter.modelToResponse(gameRatingsRepository.save(toSave));
    }

    public void delete(final Integer id) {
        gameRatingsRepository.deleteById(id);
    }
}
