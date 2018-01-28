package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.UserDao;
import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.exception.AlreadyExistsException;
import by.epam.tc.conference.services.exception.AuthenticationException;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private Validator<User> validator;

    @Mock
    private Function<String, String> encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldBeUserPrincipalWhenUsernameAndPasswordMatchOnAuthenticate() throws DaoException, ServiceException {
        when(encoder.apply("password")).thenReturn("encodedPassword");
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("encodedPassword");
        user.setAdmin(true);
        when(userDao.findByUsername("user")).thenReturn(Optional.of(user));

        UserPrincipal actual = userService.authenticateUser("user", "password");

        assertThat(actual, is(new UserPrincipal(1L, "user", true)));
    }

    @Test(expected = AuthenticationException.class)
    public void shouldBeExceptionWhenOnAuthenticateWhenPasswordMismatch() throws DaoException, ServiceException {
        User user = new User();
        user.setPassword("encodedPassword");

        when(encoder.apply("password")).thenReturn("wrongEncodedPassword");
        when(userDao.findByUsername("user")).thenReturn(Optional.of(user));

        userService.authenticateUser("user", "password");
    }

    @Test(expected = AuthenticationException.class)
    public void shouldBeExceptionOnAuthenticateWhenUserNotFound() throws DaoException, ServiceException {
        when(userDao.findByUsername("user")).thenReturn(Optional.empty());

        userService.authenticateUser("user", "password");
    }

    @Test(expected = InvalidDataException.class)
    public void shouldBeExceptionOnRegisterWhenInvalidUserGiven() throws ServiceException {
        User user = new User();
        when(validator.validate(user)).thenReturn(false);

        userService.registerUser(user);
    }

    @Test(expected = AlreadyExistsException.class)
    public void shouldBeExceptionOnRegisterWhenUsernameAlreadyExists() throws DaoException, ServiceException {
        User user = new User();
        user.setUsername("user");
        when(validator.validate(user)).thenReturn(true);
        when(userDao.findByUsername("user")).thenReturn(Optional.of(user));

        userService.registerUser(user);
    }

    @Test
    public void shouldBeUserPrincipalWhenSuccessful() throws DaoException, ServiceException {
        User user = new User();
        user.setUsername("user");
        user.setAdmin(true);
        when(validator.validate(user)).thenReturn(true);
        when(userDao.findByUsername("user")).thenReturn(Optional.empty());
        doAnswer((invocation -> {
            user.setId(1L);
            return 0;
        })).when(userDao).save(user);

        UserPrincipal actual = userService.registerUser(user);

        assertThat(actual, is(new UserPrincipal(1L, "user", true)));
    }
}