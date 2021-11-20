import Entities.Car;
import Utils.UIUtility;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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

    private JPanel pnlAverageConsumptionOperation;
    private JPanel pnlFuelFromDistanceOperation;
    private JPanel pnlDistanceFromFuelOperation;
    private ArrayList<JPanel> operationPanelsList;

    private JComboBox comboBoxCars;
    private JComboBox comboBoxOperations;
    private JTextArea txtAreaCarInfo;

    private Car selectedCar = null;

    private AboutAuthorDialog aboutAuthorDialog;
    private AboutApplicationDialog aboutApplicationDialog;

    private JMenuBar menuBar;

    //region UI components setup
    public MainWindow() {
        super(Application.NAME);
        createMainPanels();

//        selectedCar = new Car("Moskvich 2140", 89000, 10, 1986);

        pnlCenter.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pnlBottom.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pnlBottom.setBackground(Application.MAIN_COLOR);

        //create components
        createCenterUpComponents();
        createCenterLeftComponents();
        createCenterRightComponents();
        createBottomComponents();

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

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        //add information item to menu
        JMenuItem itemInfo = new JMenuItem("О программе");
        itemInfo.addActionListener(new AboutApplicationButtonListener(this));
        fileMenu.add(itemInfo);

        //add exit item to menu
        JMenuItem itemExit = new JMenuItem("Выход");
        itemExit.addActionListener(new ExitButtonListener());
        fileMenu.add(itemExit);

        return fileMenu;
    }

    private void createCenterUpComponents() {
        //add center components
        JPanel pnlUpCenter = new JPanel(new BorderLayout());
        pnlUpCenter.setBackground(Application.MAIN_COLOR);

        JPanel pnlCarsAddRemove = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCarsAddRemove.setBackground(Application.MAIN_COLOR);

        //add left middle cars info and buttons
        comboBoxCars = new JComboBox(new String[]{"Ничего"});
        comboBoxCars.addActionListener(new ComboBoxSelectCarListener());
        comboBoxCars.setPreferredSize(new Dimension(150, 50));
        comboBoxCars.setToolTipText("Выбрать автомобиль.");
        pnlCarsAddRemove.add(comboBoxCars);

        JButton btnAddCar = new JButton("+");
        btnAddCar.setToolTipText("Открыть окно добавления автомобиля.");
        btnAddCar.setPreferredSize(new Dimension(50, 50));
        //TODO: add action listener here
//        btnAddCar.addActionListener();

        JButton btnAddRemove = new JButton("-");
        btnAddRemove.setToolTipText("Удалить выбранный автомобиль.");
        btnAddRemove.setPreferredSize(new Dimension(50, 50));
        //TODO: add action listener here

        pnlCarsAddRemove.add(btnAddCar);
        pnlCarsAddRemove.add(btnAddRemove);

        pnlUpCenter.add(pnlCarsAddRemove, BorderLayout.WEST);

        //add operations combo box
        JPanel pnlOperationTypes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlOperationTypes.setBackground(Application.MAIN_COLOR);
        comboBoxOperations = new JComboBox(new String[]{"Ничего"});
        comboBoxOperations.setToolTipText("Выбрать операцию над автомобилем.");
        comboBoxOperations.setPreferredSize(new Dimension(225, 50));
        pnlOperationTypes.add(comboBoxOperations);

        pnlUpCenter.add(pnlOperationTypes, BorderLayout.EAST);
        pnlCenter.add(pnlUpCenter, BorderLayout.NORTH);
    }

    private void createCenterLeftComponents() {
        JPanel pnlMiddleCenter = new JPanel(new BorderLayout());

        //report and car info
        JPanel pnlCarReportAndInfo = new JPanel(new BorderLayout());

        JPanel pnlSeeReport = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSeeReport.setPreferredSize(new Dimension(270, 70));
        JButton btnSeeCarReport = new JButton("report");
        btnSeeCarReport.setPreferredSize(new Dimension(150, 50));
        pnlSeeReport.add(btnSeeCarReport);
        pnlCarReportAndInfo.add(pnlSeeReport, BorderLayout.NORTH);

        txtAreaCarInfo = new JTextArea(20, 1);
        txtAreaCarInfo.setPreferredSize(new Dimension(150, 300));
        txtAreaCarInfo.setEditable(true);
        txtAreaCarInfo.setLineWrap(true);
        txtAreaCarInfo.setFont(new Font("Arial", Font.BOLD, 14));
        txtAreaCarInfo.setForeground(Color.black);
        updateCarInfoText(selectedCar);
        txtAreaCarInfo.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        JScrollPane txtAreaScroll = new JScrollPane(txtAreaCarInfo);
        txtAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        pnlCarReportAndInfo.add(txtAreaScroll, BorderLayout.CENTER);
        pnlMiddleCenter.add(pnlCarReportAndInfo, BorderLayout.WEST);
        pnlCenter.add(pnlMiddleCenter, BorderLayout.WEST);
    }

    private void createCenterRightComponents() {
        JPanel pnlOperation = new JPanel(new GridBagLayout());

        operationPanelsList = new ArrayList<JPanel>();

        pnlFuelFromDistanceOperation = createFuelFromDistancePanel();
        comboBoxOperations.insertItemAt("Расчет топлива от пробега", comboBoxOperations.getItemCount());

        pnlDistanceFromFuelOperation = createDistanceFromFuelPanel();
        comboBoxOperations.insertItemAt("Расчет пробега от топлива", comboBoxOperations.getItemCount());

        pnlAverageConsumptionOperation = createCalculateAverageConsumptionPanel();
        comboBoxOperations.insertItemAt("Расчет среднего расхода топлива", comboBoxOperations.getItemCount());

        comboBoxOperations.addActionListener(new ComboBoxSelectOperationListener());

        operationPanelsList.add(pnlFuelFromDistanceOperation);
        operationPanelsList.add(pnlDistanceFromFuelOperation);
        operationPanelsList.add(pnlAverageConsumptionOperation);

        //set constraints of GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        pnlOperation.add(pnlFuelFromDistanceOperation, gbc);
        pnlOperation.add(pnlDistanceFromFuelOperation, gbc);
        pnlOperation.add(pnlAverageConsumptionOperation, gbc);

        hideAllOperationPanels();
        pnlCenter.add(pnlOperation, BorderLayout.CENTER);
    }

    private void createBottomComponents() {
        //add left down buttons
        JPanel leftDownPanel = new JPanel(new BorderLayout());
        JPanel btnHolderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        leftDownPanel.setBackground(Application.MAIN_COLOR);
        btnHolderPanel.setBackground(Application.MAIN_COLOR);

        JButton btnAboutApplication = createAboutApplicationButton();
        JButton btnAboutAuthor = createAboutAuthorButton();

        btnHolderPanel.add(btnAboutApplication);
        btnHolderPanel.add(btnAboutAuthor);

        leftDownPanel.add(btnHolderPanel);

        //add right down exit button
        JPanel rightDownPanel = new JPanel(new BorderLayout());
        JPanel pnlExitHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnExit = createExitButton();
        pnlExitHolder.add(btnExit);
        rightDownPanel.add(pnlExitHolder, BorderLayout.EAST);

        rightDownPanel.setBackground(Application.MAIN_COLOR);
        pnlExitHolder.setBackground(Application.MAIN_COLOR);

        pnlBottom.add(leftDownPanel, BorderLayout.WEST);
        pnlBottom.add(rightDownPanel, BorderLayout.EAST);
    }

    private JPanel createFuelFromDistancePanel() {
        JPanel basePanel = new JPanel(new FlowLayout());
        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel pnlButton = new JPanel();

        //create calculate button
        JButton btnCalculate = new JButton("Расчитать");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalculate.setPreferredSize(new Dimension(300, 50));
        //TODO: button action event here
        pnlButton.add(btnCalculate);

        //create labels with input fields
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1));
        JLabel inputLabel = new JLabel("Введите расстояние (км):");
        inputLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        JTextField inputField = new JTextField("");
        fieldsPanel.add(inputLabel, Component.LEFT_ALIGNMENT);
        fieldsPanel.add(inputField);

        formPanel.add(pnlButton, BorderLayout.SOUTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.setPreferredSize(new Dimension(300, 100));

        basePanel.add(formPanel);
        basePanel.setBorder(BorderFactory.createEtchedBorder());
        return basePanel;
    }

    private JPanel createDistanceFromFuelPanel() {
        JPanel basePanel = new JPanel(new FlowLayout());
        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel pnlButton = new JPanel();

        //create labels with input fields
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1));
        JLabel inputLabel = new JLabel("Введите кол-во топлива (л):");
        inputLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        JTextField inputField = new JTextField("");
        fieldsPanel.add(inputLabel, Component.LEFT_ALIGNMENT);
        fieldsPanel.add(inputField);

        //create calculate button
        JButton btnCalculate = new JButton("Расчитать");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalculate.setPreferredSize(new Dimension(300, 50));
        //TODO: button action event here
        pnlButton.add(btnCalculate);

        formPanel.add(pnlButton, BorderLayout.SOUTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.setPreferredSize(new Dimension(300, 100));

        basePanel.add(formPanel);
        basePanel.setBorder(BorderFactory.createEtchedBorder());
        return basePanel;
    }

    private JPanel createCalculateAverageConsumptionPanel() {
        JPanel basePanel = new JPanel(new FlowLayout());
        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel pnlButton = new JPanel();

        //create labels with input fields
        String[] labelsText = new String[]{"Введите кол-во потраченного топлива (л):",
                "Введите пройденное расстояние (км):"};
        JTextField[] inputFields = new JTextField[labelsText.length];
        JPanel fieldsPanel = new JPanel(new GridLayout(inputFields.length * 2, 1));

        for (int i = 0; i < inputFields.length; i++) {
            JLabel inputLabel = new JLabel(labelsText[i]);
            inputLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            inputFields[i] = new JTextField("");
            fieldsPanel.add(inputLabel, Component.LEFT_ALIGNMENT);
            fieldsPanel.add(inputFields[i]);
        }

        //create calculate button
        JButton btnCalculate = new JButton("Расчитать");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalculate.setPreferredSize(new Dimension(300, 50));
        //TODO: button action event here
        pnlButton.add(btnCalculate);

        formPanel.add(pnlButton, BorderLayout.SOUTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.setPreferredSize(new Dimension(350, 150));

        basePanel.add(formPanel);
        basePanel.setBorder(BorderFactory.createEtchedBorder());
        return basePanel;
    }

    private JButton createAboutAuthorButton() {
        JButton btn = new JButton("Об Авторе");
        btn.addActionListener(new AboutAuthorButtonListener(this));
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(120, 50));
        return btn;
    }

    private JButton createExitButton() {
        JButton btn = new JButton("Выход");
        btn.addActionListener(new ExitButtonListener());
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(120, 50));
        return btn;
    }

    private JButton createAboutApplicationButton() {
        JButton btn = new JButton();
        btn.addActionListener(new AboutApplicationButtonListener(this));
        btn.setPreferredSize(new Dimension(50, 50));

        //remove background color, we have an Icon instead
        btn.setContentAreaFilled(false);

        String iconPath = Application.getAppPath() +
                "\\src\\Resources\\Images\\question-button-v2.png";
        UIUtility.addIconToButton(btn, iconPath);

        return btn;
    }

    private void addMenuBar() {
        menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);
    }

    //endregion

    //region operations show/hide logic
    private void setActiveOperationPanel(int operationIndex, boolean single) {
        if (single) {
            hideAllOperationPanels();
        }
        operationPanelsList.get(operationIndex).setVisible(true);
    }

    private void hideAllOperationPanels() {
        for (JPanel panel : operationPanelsList) {
            panel.setVisible(false);
        }
    }
    //endregion

    //region car display logic
    private void updateCarInfoText(Car selectedCar) {
        if (selectedCar == null) {
            txtAreaCarInfo.setText("Выберите машину, чтобы \n" +
                    "увидеть информацию о ней.");
            return;
        }
        txtAreaCarInfo.setText("Информация об автомобиле:\n" +
                "\nМодель:\n-" + selectedCar.getModel() + "\n" +
                "\nГод выпуска:\n-" + selectedCar.getFactoryReleaseYear() + "г.\n" +
                "\nСредний расход топлива (на 100км):\n-" +
                selectedCar.getAverageFuelConsumption() + "л.\n" +
                "\nПробег:\n-" + selectedCar.getTotalPassedDistance() + "км");
    }
    //endregion

    //region listeners logic
    class AboutApplicationButtonListener implements ActionListener {
        private MainWindow owner;

        public AboutApplicationButtonListener(MainWindow owner) {
            this.owner = owner;
        }

        public void actionPerformed(ActionEvent e) {
            if (aboutApplicationDialog == null) {
                aboutApplicationDialog = new AboutApplicationDialog(owner);
            }
            aboutApplicationDialog.enableFrame(true);
        }
    }

    class AboutAuthorButtonListener implements ActionListener {
        private MainWindow owner;

        public AboutAuthorButtonListener(MainWindow owner) {
            this.owner = owner;
        }

        public void actionPerformed(ActionEvent e) {
            if (aboutAuthorDialog == null) {
                aboutAuthorDialog = new AboutAuthorDialog(owner);
            }
            aboutAuthorDialog.enableFrame(true);
        }
    }

    class ExitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class ComboBoxSelectOperationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = comboBoxOperations.getSelectedIndex();
            if (selectedIndex == 0) {
                hideAllOperationPanels();
                return;
            }
            setActiveOperationPanel(selectedIndex - 1, true);
        }
    }

    class ComboBoxSelectCarListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = comboBoxCars.getSelectedIndex();
            if (selectedIndex == 0) {
                hideAllOperationPanels();
                return;
            }
            setActiveOperationPanel(selectedIndex - 1, true);
        }
    }
    //endregion
}
