import java.awt.*;
import java.io.IOException;

public class Application {
    public final static Dimension WINDOW_SIZE = new Dimension(900, 750);
    public final static String NAME = "FuelIt";

    private static String appPath;

    public static String getAppPath() {
        return appPath;
    }

    public static final float SPLASH_SCREEN_AUTO_CLOSE_TIME = 60f;

    public static void main(String[] args) throws IOException {
        appPath = System.getProperty("user.dir");
        new SplashScreen();
    }
}
