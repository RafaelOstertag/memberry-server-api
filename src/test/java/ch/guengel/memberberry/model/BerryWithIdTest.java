package ch.guengel.memberberry.model;

import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BerryWithIdTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldNotFailOnValidBerryWithId() {
        var berryWithId = createValidBerryWithId();
        var constraintViolations = validator.validate(berryWithId);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    void shouldFailOnEmptyBerryWithId() {
        var berryWithId = new BerryWithId();

        var constraintViolations = validator.validate(berryWithId);
        var constraintViolationStrings = ConstraintViolationUtils.toStringList(constraintViolations);
        assertThat(constraintViolationStrings)
                .containsExactlyInAnyOrder("state:NotNull", "title:NotNull", "created:NotNull");
    }

    @Test
    void shouldFailOnBlankTitle() {
        var validBerryWithId = createValidBerryWithId();
        validBerryWithId.title("");

        var constraintViolations = validator.validate(validBerryWithId);
        var constraintViolationStrings = ConstraintViolationUtils.toStringList(constraintViolations);
        assertThat(constraintViolationStrings)
                .containsExactlyInAnyOrder("title:Size");
    }

    @Test
    void shouldFailOnTooLongTitle() {
        var berryWithId = createValidBerryWithId();
        berryWithId.title(berryWithId.getTitle() + "1");

        var constraintViolations = validator.validate(berryWithId);
        var constraintViolationStrings = ConstraintViolationUtils.toStringList(constraintViolations);
        assertThat(constraintViolationStrings)
                .containsExactlyInAnyOrder("title:Size");
    }

    private BerryWithId createValidBerryWithId() {
        var berryWithId = new BerryWithId();
        berryWithId.title(new String(new char[255]).replace("\0", "a"));
        berryWithId.state(BerryState.OPEN);
        berryWithId.created(OffsetDateTime.now());
        berryWithId.description(null);
        berryWithId.updated(null);
        berryWithId.setTags(null);
        return berryWithId;
    }

}
