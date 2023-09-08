package baza.trainee.domain.model;

import baza.trainee.domain.enums.BlockType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContentBlockTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testContentBlockWithValidData() {
        final Integer columns = 4;
        ContentBlock contentBlock = new ContentBlock();
        contentBlock.setId("12");
        contentBlock.setBlockType(BlockType.PICTURE_TEXT_BLOCK);
        contentBlock.setOrder(2);
        contentBlock.setColumns(columns);
        contentBlock.setPictureLink("https://example.com/banner.jpg");
        contentBlock.setTextContent("text content");

        assertTrue(validator.validate(contentBlock).isEmpty());
    }

    @Test
    public void testEventWithInvalidData() {
        ContentBlock contentBlock = new ContentBlock();
        assertFalse(validator.validate(contentBlock).isEmpty());
    }
}
