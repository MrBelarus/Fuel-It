import Entities.Car;
import Utils.ImageScaller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

/**
 * JDialog class
 * Used to input and add car information
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class AddCarDialog extends JDialog {
    private JPanel pnlMain;

    private JPanel pnlTop;
    private JPanel pnlMiddle;
    private JPanel pnlBottom;

    private JTextField txtFieldCarModel = new JTextField("");
    private JTextField txtFieldCarReleaseYear = new JTextField("");
    private JTextField txtFieldCarAverageFuelConsumption = new JTextField("");
    private JTextField txtFieldCarMileage = new JTextField("");
    private JTextField[] inputFields;

    private MainWindow mainWindow;

    private Dimension dialogSize = new Dimension(360, 340);

    public AddCarDialog(MainWindow mainWindow){
        super(mainWindow, "Добавить автомобиль");
        this.mainWindow = mainWindow;

        pnlMain = new JPanel(new BorderLayout(10, 10));

        pnlMain.add(createTopPanel(), BorderLayout.NORTH);
        pnlMain.add(createMiddlePanel(), BorderLayout.CENTER);
        pnlMain.add(createBottomPanel(), BorderLayout.SOUTH);

        setupDialog();
    }

    /**
     * sets up necessary settings for car add dialog
     */
    private void setupDialog() {
        setLayout(new BorderLayout());
        setSize(dialogSize);
        setModalityType(ModalityType.APPLICATION_MODAL);
        add(pnlMain);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //override on closing operation for more control
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                enableFrame(false);
            }
        });
    }

    /**
     * enables or disable JDialog instance
     * @param active
     */
    public void enableFrame(boolean active) {
        if (active) {
            setSize(dialogSize);
            setLocationRelativeTo(getOwner());
        }
        else {
            setFieldsEmpty();
        }

        setVisible(active);
    }

    /**
     * creates necessary UI objects for top panel
     * @return created JPanel instance
     */
    private JPanel createTopPanel(){
        pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblProjectName = new JLabel("Введите информацию об автомобиле");
        lblProjectName.setFont(new Font("Arial", Font.BOLD, 16));
        pnlTop.add(lblProjectName);
        return pnlTop;
    }

    /**
     * creates necessary UI objects for middle panel
     * @return created JPanel instance
     */
    private JPanel createMiddlePanel() {
        pnlMiddle = new JPanel(new BorderLayout(20, 10));

        //create labels with input fields
        String[] labelsText = new String[]{
                "Название модели:",
                "Год выпуска:",
                "Пробег(км):",
                "Средний расход топлива (литров/100км):"};

        inputFields = new JTextField[] {
                txtFieldCarModel,
                txtFieldCarReleaseYear,
                txtFieldCarMileage,
                txtFieldCarAverageFuelConsumption
        };

        JPanel pnlFieldsHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel fieldsPanel = new JPanel(new GridLayout(inputFields.length * 2, 1));

        for (int i = 0; i < inputFields.length; i++) {
            JLabel inputLabel = new JLabel(labelsText[i]);
            inputLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            inputFields[i].setFont(new Font("Arial", Font.BOLD, 16));
            fieldsPanel.add(inputLabel, Component.LEFT_ALIGNMENT);
            fieldsPanel.add(inputFields[i]);
        }

        pnlFieldsHolder.add(fieldsPanel);
        pnlMiddle.add(pnlFieldsHolder, BorderLayout.CENTER);

        return pnlMiddle;
    }

    /**
     * creates necessary UI objects for bottom panel
     * @return created JPanel instance
     */
    private JPanel createBottomPanel(){
        pnlBottom = new JPanel(new BorderLayout());

        Dimension btnPrefferedSize = new Dimension(110, 50);

        JPanel pnlAddCar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAddCar = new JButton("Добавить");
        btnAddCar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAddCar.setBackground(Application.MAIN_COLOR);
        btnAddCar.setPreferredSize(btnPrefferedSize);
        btnAddCar.addActionListener(new AddCarClickListener(this));
        pnlAddCar.add(btnAddCar);

        JPanel pnlExit = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnExit = new JButton("Выход");
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));
        btnExit.setBackground(new Color(255, 68, 68));
        btnExit.setPreferredSize(btnPrefferedSize);
        btnExit.addActionListener(new ExitDialogListener(this));
        pnlExit.add(btnExit);

        pnlBottom.add(pnlExit, BorderLayout.EAST);
        pnlBottom.add(pnlAddCar, BorderLayout.WEST);
        return pnlBottom;
    }

    /**
     * sets input fields empty
     */
    private void setFieldsEmpty(){
        for (JTextField field : inputFields) {
            field.setText("");
        }
    }

    /**
     * Add car button listener
     */
    class AddCarClickListener implements ActionListener{
        private JDialog dialog;

        public AddCarClickListener(JDialog dialog){
            this.dialog = dialog;
        }

        public void actionPerformed(ActionEvent e) {
            //check input
            for (JTextField field : inputFields) {
                if (Objects.equals(field.getText(), "")){
                    JOptionPane.showMessageDialog(null,
                            "Заполните данные!",
                            "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String model;
            float averageFuelConsumption;
            float mileage;
            int releaseYear;
            try {
                averageFuelConsumption = Float.parseFloat(txtFieldCarAverageFuelConsumption.getText());
                mileage = Float.parseFloat(txtFieldCarMileage.getText());
                releaseYear = Integer.parseInt(txtFieldCarReleaseYear.getText());
                model = txtFieldCarModel.getText();
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(null,
                        "Проверьте правильность ввода данных.",
                        "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Car newCar = new Car(model, mileage, averageFuelConsumption, releaseYear);
            CarManager.cars.add(newCar);

            mainWindow.addCarToComboBox(newCar);
            JOptionPane.showMessageDialog(null,
                    "Машина была успешно добавлена!",
                    "Результат", JOptionPane.INFORMATION_MESSAGE);

            setFieldsEmpty();
            dialog.setVisible(false);
        }
    }

    /**
     * Exit AddCarDialog listener
     */
    class ExitDialogListener implements ActionListener{
        private JDialog dialog;

        public ExitDialogListener(JDialog dialog){
            this.dialog = dialog;
        }

        public void actionPerformed(ActionEvent e) {
            setFieldsEmpty();
            dialog.setVisible(false);
        }
    }
}

