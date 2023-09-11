package baza.trainee.repository;

import org.springframework.data.repository.CrudRepository;

import baza.trainee.domain.model.Image;

public interface ImageRepository extends CrudRepository<Image, String> {
    
}
