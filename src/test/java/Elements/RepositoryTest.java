package Elements;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    void ajoutordone() throws InterruptedException {
        Build b = new Build("105");
        Build g = new Build("200");
        b.setDate(new Date(2020,8,15));
        g.setDate(new Date(2022,5,5));
        Repository rep = new Repository();
        rep.add_new_build(g);
        rep.add_new_build(b);

        assertEquals("105",rep.get_All_builds().first().getLocal_reference());
    }

    @Test
    void samedateadd() throws InterruptedException {
        Build b = new Build("105");
        Build g = new Build("200");
        b.setDate(new Date(2020,8,15));
        g.setDate(new Date(2020,8,15));
        Repository rep = new Repository();
        rep.add_new_build(g);
        rep.add_new_build(b);

        assertEquals("105",rep.get_All_builds().first().getLocal_reference());
    }

}