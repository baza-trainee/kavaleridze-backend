package baza.trainee.api.impl;

import baza.trainee.api.FeedbackApiDelegate;
import baza.trainee.dto.MailDto;
import baza.trainee.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static baza.trainee.constants.MailConstants.MUSEUM_SUBJECT;

/**
 * Spring MVC REST controller for handling feedback submission.
 *
 * @author Anatolii Omelchenko
 */
@Service
@RequiredArgsConstructor
public class FeedbackApiDelegateImpl implements FeedbackApiDelegate {

    @Value("${mail.museum.email}")
    private String museumEmail;

    private final MailService mailService;

    /**
     * Handles the submission of a contact form. Sends emails to the user and the museum.
     *
     * @param mailDto       An object containing data from the feedback form,
     *                      including first name, last name, email, message.
     * @return A ResponseEntity with a status of 200 (OK) if the email sending is successful.
     */
    @Override
    public ResponseEntity<Object> submitContactForm(MailDto mailDto) {
        String msgForUser = mailService.buildMsgForUser();
        mailService.sendEmail(mailDto.getEmail(), msgForUser, MUSEUM_SUBJECT);

        String msgForMuseum = mailService.buildMsgForMuseum(mailDto.getFirstName(), mailDto.getLastName(),
                mailDto.getEmail(), mailDto.getMessage());
        mailService.sendEmail(museumEmail, msgForMuseum, MUSEUM_SUBJECT);

        return ResponseEntity.ok().build();
    }

}
