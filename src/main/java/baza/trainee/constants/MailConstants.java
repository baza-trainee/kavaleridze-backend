package baza.trainee.constants;


public final class MailConstants {
    private MailConstants() {
    }

    public static final String MUSEUM_SUBJECT = "Лист зворотного зв'язку";
    public static final String FAIL_SEND_MSG = "failed to send email";
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 30;
    public static final int MIN_MSG_LENGTH = 10;
    public static final int MAX_MSG_LENGTH = 300;
    public static final String FIELD_NAME_ERROR_MSG =
            "size must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH;
    public static final String FIELD_EMAIL_ERROR_MSG = "must be a well-formed email address";
    public static final String FIELD_MESSAGE_ERROR_MSG =
            "size must be between " + MIN_MSG_LENGTH + " and " + MAX_MSG_LENGTH;
    public static final String NULL_FIELD_ERROR_MSG = "must not be null";
}
