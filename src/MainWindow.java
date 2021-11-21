import Entities.Car;
import Utils.UIUtility;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    public Car getSelectedCar() {
        return selectedCar;
    }

    private AboutAuthorDialog aboutAuthorDialog;
    private AboutApplicationDialog aboutApplicationDialog;
    private AddCarDialog addCarDialog;

    private JMenuBar menuBar;

    //region UI components setup
    public MainWindow() {
        super(Application.NAME);
        createMainPanels();

        pnlCenter.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pnlBottom.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pnlBottom.setBackground(Application.MAIN_COLOR);

        //create components
        createCenterUpComponents();
        createCenterLeftComponents();
        createCenterRightComponents();
        createBottomComponents();

        //load data
        loadCarsData();

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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //override on closing operation for more control
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                doActionsAndCloseWindow();
            }
        });
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
        JLabel lblSelectCar = new JLabel("Автомобиль:");
        lblSelectCar.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSelectCar.setForeground(Color.black);

        comboBoxCars = new JComboBox(new String[]{"Ничего"});
        comboBoxCars.addActionListener(new ComboBoxSelectCarListener());
        comboBoxCars.setPreferredSize(new Dimension(150, 50));
        comboBoxCars.setToolTipText("Выбрать автомобиль.");

        JButton btnAddCar = new JButton("Добавить");
        btnAddCar.setToolTipText("Открыть окно добавления автомобиля.");
        btnAddCar.setPreferredSize(new Dimension(90, 50));
        btnAddCar.addActionListener(new ButtonAddCarListener(this));

        JButton btnAddRemove = new JButton("Удалить");
        btnAddRemove.setToolTipText("Удалить выбранный автомобиль.");
        btnAddRemove.setPreferredSize(new Dimension(90, 50));
        btnAddRemove.addActionListener(new ButtonRemoveCarListener());

        pnlCarsAddRemove.add(lblSelectCar);
        pnlCarsAddRemove.add(comboBoxCars);
        pnlCarsAddRemove.add(btnAddCar);
        pnlCarsAddRemove.add(btnAddRemove);

        pnlUpCenter.add(pnlCarsAddRemove, BorderLayout.WEST);

        //add operations combo box
        JPanel pnlOperationTypes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlOperationTypes.setBackground(Application.MAIN_COLOR);

        JLabel lblSelectOperation = new JLabel("Выберите тип операции:", SwingConstants.RIGHT);
        lblSelectOperation.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSelectOperation.setForeground(Color.black);

        comboBoxOperations = new JComboBox(new String[]{"Ничего"});
        comboBoxOperations.setToolTipText("Выбрать операцию над автомобилем.");
        comboBoxOperations.setPreferredSize(new Dimension(225, 50));

        pnlOperationTypes.add(lblSelectOperation);
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
        JButton btnSeeCarReport = new JButton("<html>Показать отчет<br>по автомобилю<html>");
        btnSeeCarReport.setPreferredSize(new Dimension(150, 50));
        pnlSeeReport.add(btnSeeCarReport);
        pnlCarReportAndInfo.add(pnlSeeReport, BorderLayout.NORTH);

        txtAreaCarInfo = new JTextArea(0, 1);
        txtAreaCarInfo.setPreferredSize(new Dimension(150, 300));
        txtAreaCarInfo.setEditable(false);
        txtAreaCarInfo.setLineWrap(true);
        txtAreaCarInfo.setFont(new Font("Arial", Font.BOLD, 14));
        txtAreaCarInfo.setForeground(Color.black);
        txtAreaCarInfo.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        updateCarInfoText(selectedCar);

        JScrollPane txtAreaScroll = new JScrollPane(txtAreaCarInfo);
//        txtAreaScroll.setPreferredSize(new Dimension(270, 70));
        txtAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        txtAreaScroll.getViewport().setOpaque(false);
        txtAreaScroll.setOpaque(false);

        pnlCarReportAndInfo.add(txtAreaScroll, BorderLayout.CENTER);
        pnlMiddleCenter.add(pnlCarReportAndInfo, BorderLayout.WEST);
        pnlCenter.add(pnlMiddleCenter, BorderLayout.WEST);
    }

    private void createCenterRightComponents() {
        JPanel pnlOperation = new JPanel(new GridBagLayout());

        operationPanelsList = new ArrayList<JPanel>();

        pnlFuelFromDistanceOperation = new FuelFromDistancePanel(this);
        comboBoxOperations.insertItemAt("Расчет топлива от пробега", comboBoxOperations.getItemCount());

        pnlDistanceFromFuelOperation = new DistanceFromFuelPanel(this);
        comboBoxOperations.insertItemAt("Расчет пробега от топлива", comboBoxOperations.getItemCount());

        pnlAverageConsumptionOperation = new AverageFuelConsumptionPanel(this);
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

    private void doActionsAndCloseWindow(){
        CarManager.saveCarsToFile();
        dispose();
    }

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

    //region cars display logic

    /**
     * Updates car's info text
     * @param selectedCar
     */
    public void updateCarInfoText(Car selectedCar) {
        if (selectedCar == null) {
            txtAreaCarInfo.setText("Выберите машину, чтобы \n" +
                    "увидеть информацию о ней.");
            return;
        }
        txtAreaCarInfo.setText("Информация об автомобиле:\n" +
                "\nМодель:\n-" + selectedCar.getModel() + "\n" +
                "\nГод выпуска:\n-" + selectedCar.getFactoryReleaseYear() + "г.\n" +
                "\nПробег:\n-" + selectedCar.getTotalPassedDistance() + "км\n" +
                "\nСредний расход топлива (на 100км):\n-" +
                selectedCar.getAverageFuelConsumption() + "л.");
    }

    private void loadCarsData(){
        CarManager.loadCarsFromFile();
        for (Car car : CarManager.cars){
            addCarToComboBox(car);
        }
    }

    /**
     * Adds car to combo box (bottom)
     * @param car instance
     */
    public void addCarToComboBox(Car car){
        comboBoxCars.insertItemAt(car.getModel(), comboBoxCars.getItemCount());
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
            doActionsAndCloseWindow();
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
            //-1 because "Ничего" is 0 index always
            int selectedCarIndex = comboBoxCars.getSelectedIndex() - 1;

            selectedCar = selectedCarIndex == -1 ? null : CarManager.cars.get(selectedCarIndex);
            updateCarInfoText(selectedCar);
        }
    }

    class ButtonAddCarListener implements ActionListener{
        private MainWindow owner;

        public ButtonAddCarListener(MainWindow owner) {
            this.owner = owner;
        }

        public void actionPerformed(ActionEvent e) {
            if (addCarDialog == null) {
                addCarDialog = new AddCarDialog(owner);
            }
            addCarDialog.enableFrame(true);
        }
    }

    class ButtonRemoveCarListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (selectedCar == null) {
                JOptionPane.showMessageDialog(null, "Сначала выберите машину," +
                        "\nкоторую хотите удалить", "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CarManager.cars.remove(selectedCar);
            comboBoxCars.removeItemAt(comboBoxCars.getSelectedIndex());
//
//            //-2 because we don't include default item
//            int carIndex = comboBoxCars.getItemCount() - 2;
//
//            selectedCar = carIndex == -1 ? null : CarManager.cars.get(carIndex);
//            comboBoxCars.setSelectedIndex(comboBoxCars.getItemCount() - 1);
            selectedCar = null;
            comboBoxCars.setSelectedIndex(0);
        }
    }
    //endregion
}
