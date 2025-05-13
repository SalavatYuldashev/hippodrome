import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


class MainTest {

    @Disabled
    @Test
    @Timeout(20)
    @DisplayName("Should Fail After Twenty Second")
    void shouldFailAfterTwentySecond() throws Exception {
        Main.main(new String[0]);
    }
}