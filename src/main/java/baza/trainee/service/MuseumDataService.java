package baza.trainee.service;

import baza.trainee.domain.model.MuseumData;

public interface MuseumDataService {
    MuseumData update(MuseumData museumData);
    MuseumData add(MuseumData museumData);
    MuseumData getData();
}
