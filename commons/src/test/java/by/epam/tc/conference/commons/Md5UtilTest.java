package by.epam.tc.conference.commons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class Md5UtilTest {

    @Test
    public void shouldBeMd5HashWhenStringGiven() {
        String actual = Md5Util.encode("password");
        assertThat(actual, is("5f4dcc3b5aa765d61d8327deb882cf99"));
    }
}