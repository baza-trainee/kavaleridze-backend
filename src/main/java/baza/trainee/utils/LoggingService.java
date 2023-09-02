package baza.trainee.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoggingService {

    public void logError(String title, String message) {
        int equalsLength = 40;
        String equalsLine = "=".repeat(equalsLength);
        String bottomLine =
                "=".repeat(title.length() + 2) + equalsLine.repeat(2);

        log.error(
                "\n\033[0;31m{} {} {}\033[0m\n\033[0;95mMessage: {}\033[0m\n\033[0;31m{}\033[0m",
                equalsLine, title, equalsLine, message, bottomLine);
    }
}
