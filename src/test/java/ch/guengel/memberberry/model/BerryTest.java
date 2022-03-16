package ch.guengel.memberberry.model;


import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;


class BerryTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldNotFailOnValidBerry() {
        var berry = createValidBerry();
        var constraintViolations = validator.validate(berry);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    void shouldFailOnEmptyBerry() {
        var berry = new Berry();

        var constraintViolations = validator.validate(berry);
        var constraintViolationStrings = ConstraintViolationUtils.toStringList(constraintViolations);
        assertThat(constraintViolationStrings)
                .containsExactlyInAnyOrder("state:NotNull", "title:NotNull");
    }

    @Test
    void shouldFailOnBlankTitle() {
        var berry = createValidBerry();
        berry.title("");

        var constraintViolations = validator.validate(berry);
        var constraintViolationStrings = ConstraintViolationUtils.toStringList(constraintViolations);
        assertThat(constraintViolationStrings)
                .containsExactlyInAnyOrder("title:Size");
    }

    @Test
    void shouldFailOnTooLongTitle() {
        var berry = createValidBerry();
        berry.title(berry.getTitle() + "1");

        var constraintViolations = validator.validate(berry);
        var constraintViolationStrings = ConstraintViolationUtils.toStringList(constraintViolations);
        assertThat(constraintViolationStrings)
                .containsExactlyInAnyOrder("title:Size");
    }

    private Berry createValidBerry() {
        var berry = new Berry();
        berry.title(new String(new char[255]).replace("\0", "a"));
        berry.state(BerryState.OPEN);
        berry.description(null);
        return berry;
    }


}
