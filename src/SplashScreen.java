import Utils.ImageScaller;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Splash screen window of application
 * User needs to press button to use application
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class SplashScreen extends JFrame {
    //panel that contains sub panels
    private final JPanel pnlMain;

    private final JPanel pnlInfo;
    private final JPanel pnlButtons;
    private JButton btnContinue;
    private JButton btnExit;

    private Timer autoSelfCloseTimer;

    /**
     * Constructor to create splash screen window
     */
    public SplashScreen() {
        super("????? ?????????? ? FuelIt!");

        pnlMain = new JPanel(new BorderLayout());
        pnlInfo = new JPanel(new BorderLayout());
        pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        addUniversityInfo(pnlInfo);
        addProjectInfo(pnlInfo);
        addCreatorsInfo(pnlInfo);
        addUserButtons(pnlButtons);

        pnlMain.add(pnlInfo, BorderLayout.CENTER);
        pnlMain.add(pnlButtons, BorderLayout.SOUTH);

        setupFrame(pnlMain);
        RunSelfCloseTimer();
    }

    /**
     * Runs self close application timer
     */
    private void RunSelfCloseTimer() {
        int delay = (int) Application.SPLASH_SCREEN_AUTO_CLOSE_TIME * 1000;
        autoSelfCloseTimer = new Timer(delay, new SplashScreenTimerEndListener(this));
        autoSelfCloseTimer.start();
    }

    /**
     * Sets main settings to Splash screen window
     */
    private void setupFrame(JPanel pnlMain) {
        setLayout(new BorderLayout());
        add(pnlMain);
        setSize(new Dimension(800, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Adds necessary UI elements for university information
     */
    private void addUniversityInfo(JPanel holder) {
        JPanel pnlUniversityInfo = new JPanel(new GridLayout(5, 1));
        JPanel emptySpace = new JPanel();

        JLabel lblUniversity = new JLabel("??????????? ???????????? " +
                "??????????? ???????????", SwingConstants.CENTER);
        lblUniversity.setFont(new Font("Arial", Font.BOLD, 14));
        pnlUniversityInfo.add(lblUniversity);
        pnlUniversityInfo.add(emptySpace);

        JLabel lblFaculty = new JLabel("????????? ?????????????? " +
                "?????????? ? ????????????? ", SwingConstants.CENTER);
        lblFaculty.setFont(new Font("Arial", Font.BOLD, 14));
        pnlUniversityInfo.add(lblFaculty);
        pnlUniversityInfo.add(emptySpace);

        JLabel lblCathedra = new JLabel("??????? ???????????? ??????????? " +
                "?????????????? ?????? ? ?????????? ", SwingConstants.CENTER);
        lblCathedra.setFont(new Font("Arial", Font.BOLD, 14));
        pnlUniversityInfo.add(lblCathedra);

        holder.add(pnlUniversityInfo, BorderLayout.NORTH);
    }

    /**
     * Adds necessary UI elements for project information
     */
    private void addProjectInfo(JPanel holder) {
        JPanel pnlProjectInfo = new JPanel(new GridBagLayout());

        JPanel pnlText = new JPanel(new GridLayout(3, 1, 5, 5));

        JLabel lblProjectType = new JLabel("???????? ??????", SwingConstants.CENTER);
        lblProjectType.setFont(new Font("Arial", Font.BOLD, 18));
        pnlText.add(lblProjectType);

        JLabel lblDiscipline = new JLabel("?? ?????????? " +
                "\"???????????????? ?? ????? Java\"", SwingConstants.CENTER);
        lblDiscipline.setFont(new Font("Arial", Font.BOLD, 14));
        pnlText.add(lblDiscipline);

        JLabel lblProjectName = new JLabel("?????????? \"???? ??????? ???????\"", SwingConstants.CENTER);
        lblProjectName.setFont(new Font("Arial", Font.BOLD, 22));
        pnlText.add(lblProjectName);

        //set constraints of GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        pnlProjectInfo.add(pnlText, gbc);
        holder.add(pnlProjectInfo, BorderLayout.CENTER);
    }

    /**
     * Adds necessary UI elements for creators information
     */
    private void addCreatorsInfo(JPanel holder) {
        JPanel pnlAll = new JPanel(new BorderLayout(10, 30));

        JPanel pnlIconAndCreators = new JPanel(new GridLayout(1, 2, 20, 20));
        JPanel pnlCreators = new JPanel(new BorderLayout());

        //create creator info label
        JLabel lblCreator = new JLabel("<html>????????: ??????? ?????? 10702119<br>" +
                "?????? ????????? ???????</html>", SwingConstants.LEFT);
        lblCreator.setFont(new Font("Arial", Font.BOLD, 16));
        pnlCreators.add(lblCreator, BorderLayout.NORTH);

        //create teacher info label
        JLabel lblTeacher = new JLabel("<html>?????????????: ?-?.-?.?., ???.<br>" +
                "??????? ??????? ????????????</html>", SwingConstants.LEFT);
        lblTeacher.setFont(new Font("Arial", Font.BOLD, 16));
        pnlCreators.add(lblTeacher, BorderLayout.SOUTH);

        //add app icon
        Image imgScaledAppIcon = ImageScaller.scaleImage(
                "/resources/images/AppIcon_v2.png",
                new Dimension(250, 250), Image.SCALE_SMOOTH);
        ImageIcon imgAppIcon = new ImageIcon(imgScaledAppIcon);


        pnlIconAndCreators.add(new JLabel(imgAppIcon, SwingConstants.RIGHT));
        pnlIconAndCreators.add(pnlCreators);

        //add icon and creators info
        pnlAll.add(pnlIconAndCreators, BorderLayout.CENTER);

        //add city & year info
        JLabel lblCityAndYear = new JLabel("?????, 2021", SwingConstants.CENTER);
        lblCityAndYear.setFont(new Font("Arial", Font.BOLD, 16));
        pnlAll.add(lblCityAndYear, BorderLayout.SOUTH);

        holder.add(pnlAll, BorderLayout.SOUTH);
    }

    /**
     * Adds necessary user buttons
     * Continue and close buttons
     */
    private void addUserButtons(JPanel holder) {
        btnContinue = createUserButton("?????", new ContinueButtonListener());
        holder.add(btnContinue);

        btnExit = createUserButton("?????", new ExitButtonListener());
        holder.add(btnExit);
    }

    /**
     * Creates JButton instance for SplashScreen
     * @param text text on the button
     * @param listener action listener
     * @return created JButton instance
     */
    private JButton createUserButton(String text, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setForeground(Color.black);
        btn.setPreferredSize(new Dimension(180, 70));
        btn.addActionListener(listener);
        btn.setBackground(Application.MAIN_COLOR);
        btn.setBorder(new BevelBorder(BevelBorder.RAISED));
        return btn;
    }

    /**
     * Splash screen self close timer listener class
     */
    class SplashScreenTimerEndListener implements ActionListener {
        private final Window splashScreen;

        public SplashScreenTimerEndListener(Window splashScreen) {
            this.splashScreen = splashScreen;
        }

        public void actionPerformed(ActionEvent e) {
            dispatchEvent(new WindowEvent(splashScreen, WindowEvent.WINDOW_CLOSING));
        }
    }

    /**
     * Continue button click listener class
     */
    class ContinueButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //stop auto close timer
            if (autoSelfCloseTimer.isRunning()) {
                autoSelfCloseTimer.stop();
            }
            //run main window
            new MainWindow();
            //close splashScreen by disposing memory
            dispose();
        }
    }

    /**
     * Exit button click listener class
     */
    class ExitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //stop auto close timer
            if (autoSelfCloseTimer.isRunning()) {
                autoSelfCloseTimer.stop();
            }
            //exit application
            System.exit(0);
        }
    }
}