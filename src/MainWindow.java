import Utils.ImageScaller;
import Utils.UIUtility;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main window of FuelIt Application
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class MainWindow extends JFrame {
    //panel that contains sub panels
    private JPanel pnlMain;

    //sub panels
    private JPanel pnlCenter;
    private JPanel pnlBottom;

    private JMenuBar menuBar;

    private CarSelectDialog carSelectDialog;

    public MainWindow() {
        super(Application.NAME);
        createMainPanels();

        pnlCenter.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pnlBottom.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        //add components

        //add left down buttons
        JPanel leftDownPanel = new JPanel(new BorderLayout());
        JPanel btnHolderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnAboutApplication = createAboutApplicationButton();

        JButton btnAboutAuthor = createAboutAuthorButton();

        btnHolderPanel.add(btnAboutApplication);
        btnHolderPanel.add(btnAboutAuthor);

        leftDownPanel.add(btnHolderPanel);

        //add right down exit button
        JPanel rightDownPanel = new JPanel(new BorderLayout());
        JPanel pnlExitHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnExit = new JButton("Exit");
        btnExit.setPreferredSize(new Dimension(60, 50));
        pnlExitHolder.add(btnExit);
        rightDownPanel.add(pnlExitHolder, BorderLayout.EAST);

        pnlBottom.add(leftDownPanel, BorderLayout.WEST);
        pnlBottom.add(rightDownPanel, BorderLayout.EAST);

        //add panels to main panel
        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlMain.add(pnlBottom, BorderLayout.SOUTH);

        setupFrame(pnlMain);
    }

    private void setupFrame(JPanel mainPanel) {
        setLayout(new BorderLayout());
        add(mainPanel);
        addMenuBar();
        setSize(new Dimension(Application.WINDOW_SIZE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMainPanels() {
        //create necessary panels
        pnlMain = new JPanel(new BorderLayout(0, 10));
        pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setPreferredSize(new Dimension(Application.WINDOW_SIZE.width, 50));
        pnlBottom = new JPanel(new BorderLayout(10, 10));
    }

    private JButton createAboutAuthorButton() {
        JButton btn = new JButton("About author");
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(120, 50));
        return btn;
    }

    private JButton createAboutApplicationButton() {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(50, 50));

        String iconPath = Application.getAppPath() +
                "\\src\\Resources\\Images\\question-button-v2.png";
        UIUtility.addLabelIcon(btn, iconPath);

        return btn;
    }



    private void addMenuBar(){
        menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);
    }

    private JMenu createFileMenu(){
        JMenu fileMenu = new JMenu("File");

        //add information item to menu
        JMenuItem itemInfo = new JMenuItem("О программе");
        itemInfo.addActionListener(new AboutApplicationButtonListener());
        fileMenu.add(itemInfo);

        //add exit item to menu
        JMenuItem itemExit = new JMenuItem("Выход");
        itemExit.addActionListener(new ExitButtonListener());
        fileMenu.add(itemExit);

        return fileMenu;
    }

    class AboutApplicationButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //TODO: Implement about application open dialog
        }
    }

    class AboutAuthorButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //TODO: Implement about author open dialog
        }
    }

    class ExitButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class PerformOperationButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //TODO: Implement operations frame open
        }
    }
}
