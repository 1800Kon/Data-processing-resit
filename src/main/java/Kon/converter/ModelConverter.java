package Kon.converter;

public interface ModelConverter<Q, M, S> {
    M requestToModel(Q request);
    S modelToResponse(M model);
}
