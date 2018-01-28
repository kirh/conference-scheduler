package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.web.controller.Languages;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static by.epam.tc.conference.web.controller.command.impl.CommandTestHelper.assertThatBadRequest;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChangeLocaleCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private Command command = new ChangeLocaleCommand();

    @Test
    public void shouldSpecifyLocaleAndRedirectToPreviousPageWhenValidLanguageGiven() throws CommandException {
        String validLanguage = Languages.ENGLISH.getCode();
        when(request.getParameter("lang")).thenReturn(validLanguage);

        String dispatcherQuery = command.execute(request, response);

        Locale newLocale = new Locale(validLanguage);
        verify(request.getSession()).setAttribute(SessionAttribute.LOCALE, newLocale);
        assertThat(dispatcherQuery, is("redirect:"));
    }

    @Test(expected = CommandException.class)
    public void shouldBeExceptionWhenInvalidLanguageGiven() throws CommandException {
        when(request.getParameter("lang")).thenReturn("invalid language");

        command.execute(request, response);
    }
}