import java.awt.*;
import java.io.IOException;

/**
 * Application class
 * Start point of application
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class Application {
    public final static Dimension WINDOW_SIZE = new Dimension(900, 750);
    public final static String NAME = "FuelIt";
    public final static Color MAIN_COLOR = new Color(201, 202, 255);
    public final static String VERSION = "0.1.0";

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
