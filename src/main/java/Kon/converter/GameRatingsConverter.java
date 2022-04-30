package Kon.converter;

import Kon.models.gameRatings.client.GameRatings;
import Kon.models.gameRatings.client.GameRatingsRequest;
import Kon.models.gameRatings.database.GameRatingsModel;
import org.springframework.stereotype.Component;

@Component
public class GameRatingsConverter implements ModelConverter<GameRatingsRequest, GameRatingsModel, GameRatings>{
    @Override
    public GameRatingsModel requestToModel(GameRatingsRequest request) {
        GameRatingsModel gameRatingsModel = new GameRatingsModel();
        gameRatingsModel.setName(request.getName());
        gameRatingsModel.setPlatform(request.getPlatform());
        gameRatingsModel.setReleaseYear(request.getReleaseYear());
        gameRatingsModel.setScore(request.getScore());
        gameRatingsModel.setUserScore(request.getUserScore());
        gameRatingsModel.setDeveloper(request.getDeveloper());
        gameRatingsModel.setGenre(request.getGenre());
        return gameRatingsModel;
    }

    @Override
    public GameRatings modelToResponse(GameRatingsModel model) {
        GameRatings gameRatings = new GameRatings();
        gameRatings.setId(model.getId());
        gameRatings.setName(model.getName());
        gameRatings.setPlatform(model.getPlatform());
        gameRatings.setReleaseYear(model.getReleaseYear());
        gameRatings.setScore(model.getScore());
        gameRatings.setUserScore(model.getUserScore());
        gameRatings.setDeveloper(model.getDeveloper());
        gameRatings.setGenre(model.getGenre());
        return gameRatings;
    }
}
