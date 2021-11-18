import FuelIt.Utils.ImageScaller;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    //panel that contains sub panels
    private JPanel pnlMain;

    //sub panels
    private JPanel pnlTop;
    private JPanel pnlMiddle;
    private JPanel pnlBottom;

    private JButton btnShowVehicles;
    private JButton btnPerformOperation;
    //more?

    private JButton btnAboutAuthor;
    private JButton btnAboutApplication;

    private JPanel pnlSelectedVehicle;
    private JLabel selectedVehicleFuelConsumptionValue;
    private JLabel selectedVehicleFuelConsumptionTitle;

    private CarSelectDialog carSelectDialog;

    public MainWindow() {
        super(Application.NAME);
        createMainPanels();

        pnlTop.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pnlBottom.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        //add components to panels
        addAboutAuthorButton(pnlTop);
        addAboutApplicationButton(pnlTop);
        addShowVehiclesButton(pnlMiddle);
        addPerformOperationButton(pnlMiddle);
        setupSelectedVehicleInfoPanel(pnlBottom);

        //add panels to main panel
        pnlMain.add(pnlTop, BorderLayout.NORTH);
        pnlMain.add(pnlMiddle, BorderLayout.CENTER);
        pnlMain.add(pnlBottom, BorderLayout.SOUTH);

        setupFrame(pnlMain);
    }

    private void setupFrame(JPanel mainPanel) {
        setLayout(new BorderLayout());
        add(mainPanel);
        setSize(new Dimension(Application.WINDOW_SIZE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMainPanels() {
        //create necessary panels
        pnlMain = new JPanel(new BorderLayout(0, 10));
        pnlTop = new JPanel(new BorderLayout(10, 10));
        pnlTop.setPreferredSize(new Dimension(Application.WINDOW_SIZE.width, 50));
        pnlMiddle = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlMiddle.setBackground(new Color(201, 202, 255));
        pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    }

    private void addAboutAuthorButton(JPanel parentPanel) {
        btnAboutAuthor = new JButton("About author");
        btnAboutAuthor.setFont(new Font("Arial", Font.BOLD, 12));
        btnAboutAuthor.setPreferredSize(new Dimension(120, parentPanel.getHeight()));
        parentPanel.add(btnAboutAuthor, BorderLayout.EAST);
    }

    class AboutAuthorButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //TODO: Implement about author open dialog
        }
    }

    private void addAboutApplicationButton(JPanel parentPanel) {
        btnAboutApplication = new JButton();
        btnAboutApplication.setPreferredSize(new Dimension(50, parentPanel.getHeight()));

        ImageIcon questionIcon = new ImageIcon(ImageScaller.scaleImage(
                Application.getAppPath() + "\\src\\FuelIt\\Resources\\Images\\question-button-v2.png",
                new Dimension(50, 50),
                Image.SCALE_SMOOTH));
        btnAboutApplication.setIcon(questionIcon);

        parentPanel.add(btnAboutApplication, BorderLayout.WEST);
    }

    class AboutApplicationButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //TODO: Implement about application open dialog
        }
    }

    private void addShowVehiclesButton(JPanel parentPanel) {
        //show vehicles button
        btnShowVehicles = new JButton("Show Vehicles");
        btnShowVehicles.setFont(new Font("Arial", Font.BOLD, 20));
        btnShowVehicles.setPreferredSize(new Dimension(350, 80));

        btnShowVehicles.addActionListener(new ShowVehiclesButtonListener(this));

        parentPanel.add(btnShowVehicles);
    }

    class ShowVehiclesButtonListener implements ActionListener {
        private MainWindow mainWindow;

        public ShowVehiclesButtonListener(MainWindow mainWindow) {
            this.mainWindow = mainWindow;
        }

        public void actionPerformed(ActionEvent e) {
            if (carSelectDialog == null) {
                carSelectDialog = new CarSelectDialog(mainWindow);
            }
            carSelectDialog.enableDialog(true);
        }
    }

    private void addPerformOperationButton(JPanel parentPanel) {
        btnPerformOperation = new JButton("Perform Operation");
        btnPerformOperation.setFont(new Font("Arial", Font.BOLD, 20));
        btnPerformOperation.setPreferredSize(new Dimension(350, 80));
        parentPanel.add(btnPerformOperation);
    }

    class PerformOperationButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //TODO: Implement operations frame open
        }
    }

    private void setupSelectedVehicleInfoPanel(JPanel parentPanel) {
        pnlSelectedVehicle = new JPanel(new GridLayout(3, 1, 10, 15));
        pnlSelectedVehicle.setPreferredSize(new Dimension(250, 175));

        JLabel panelTitle = new JLabel("selected vehicle fuel info:", SwingConstants.CENTER);
        panelTitle.setFont(new Font("Times New Roman", Font.BOLD, 16));

        selectedVehicleFuelConsumptionTitle = new JLabel("Average fuel consumption", SwingConstants.CENTER);
        selectedVehicleFuelConsumptionTitle.setFont(new Font("Arial", Font.BOLD, 16));

        selectedVehicleFuelConsumptionValue = new JLabel("10000 liters", SwingConstants.CENTER);
        selectedVehicleFuelConsumptionValue.setFont(new Font("Arial", Font.BOLD, 25));

        pnlSelectedVehicle.add(panelTitle);
        pnlSelectedVehicle.add(selectedVehicleFuelConsumptionTitle);
        pnlSelectedVehicle.add(selectedVehicleFuelConsumptionValue);
        pnlSelectedVehicle.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        parentPanel.add(pnlSelectedVehicle);
    }
}
