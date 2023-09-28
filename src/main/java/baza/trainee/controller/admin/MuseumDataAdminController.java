package baza.trainee.controller.admin;

import baza.trainee.domain.model.MuseumData;
import baza.trainee.service.MuseumDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/admin/museum_data")
@RequiredArgsConstructor
public class MuseumDataAdminController {

    private final MuseumDataService museumDataService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MuseumData addData(@RequestBody MuseumData museumData) {
        return museumDataService.add(museumData);
    }

    @PutMapping
    public MuseumData updateData(@RequestBody MuseumData museumData) {
        return museumDataService.update(museumData);
    }
}
