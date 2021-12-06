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
    /**
     * Window size
     */
    public final static Dimension WINDOW_SIZE = new Dimension(950, 830);

    /**
     * Application name
     */
    public final static String NAME = "FuelIt";

    /**
     * Application main color
     */
    public final static Color MAIN_COLOR = new Color(201, 202, 255);

    /**
     * Application version
     */
    public final static String VERSION = "0.1.2";

    private static String appPath;

    public static String getAppPath() {
        return appPath;
    }

    /**
     * Application splash screen timeout
     */
    public static final float SPLASH_SCREEN_AUTO_CLOSE_TIME = 60f;

    /*
    Enter point to application
     */
    public static void main(String[] args) throws IOException {
        appPath = System.getProperty("user.dir");

        new SplashScreen();
    }
}
