package ch.guengel.memberberry.model;

import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessageTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldFailOnEmptyErrorMessage() {
        var errorMessage = new ErrorMessage();

        var constraintViolations = validator.validate(errorMessage);
        var constraintViolationStrings = ConstraintViolationUtils.toStringList(constraintViolations);
        assertThat(constraintViolationStrings)
                .containsExactlyInAnyOrder("reason:NotNull");
    }

    @Test
    void shouldNotFailOnValidErrorMessage() {
        var errorMessage = new ErrorMessage();
        errorMessage.reason("a reason");

        var constraintViolations = validator.validate(errorMessage);
        assertThat(constraintViolations).isEmpty();
    }
}
