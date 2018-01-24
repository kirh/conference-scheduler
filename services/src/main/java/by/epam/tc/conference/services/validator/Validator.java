package by.epam.tc.conference.services.validator;

public interface Validator<T> {
    public boolean validate(T object);
}
