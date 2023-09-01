package Connect;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Url_validatorTest {

    String valid = "https://coveralls.io/";
    String Invalid = "https://boudini" ;

    @Test
    void check_rul() throws IOException {
        var f = new Url_validator();
        f.setUrl(valid);
        assertTrue(f.isOk());
    }

    @Test
    void check_bad_url() throws IOException {
        var f = new Url_validator();
        f.setUrl(Invalid);
        assertFalse(f.isOk());
    }

}