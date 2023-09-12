package baza.trainee.repository;

import baza.trainee.domain.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, String> {

}
