import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class Project3Test{
    @Test
    public void test123(){
        int x = 1;
        int y = 1;
        assertFalse(x == y);
    }
}