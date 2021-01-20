package debug;
import org.routes.HelloResource;

public class JUnitTest {
    public static void main(String[] args)
    {
        HelloResource t = new HelloResource();
        t.testPost();
    }
}
