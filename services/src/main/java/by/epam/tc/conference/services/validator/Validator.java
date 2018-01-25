package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Identifiable;

/**
 * Validates given {@link Identifiable} object
 * @param <T>
 */
public interface Validator<T extends Identifiable> {

    /**
     * Validates given {@link Identifiable} object
     * id == null is valid state
     * @param object to validate
     * @return true when given object is valid
     */
    boolean validate(T object);
}
