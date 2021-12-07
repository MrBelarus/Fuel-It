import Entities.Car;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Objects;

/**
 * Input data panel
 * To calculate average fuel consumption
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class AverageFuelConsumptionPanel extends JPanel {
    private MainWindow mainWindow;

    private JTextField txtFldMileageStart = new JTextField("");
    private JTextField txtFldMileageFinish = new JTextField("");
    private JTextField txtFldAdditionalFuel = new JTextField("");

    private JSlider sliderFuelAmountOnStart = new JSlider(
            JSlider.HORIZONTAL, 0, 100, 50);
    private JSlider sliderFuelAmountOnFinish = new JSlider(
            JSlider.HORIZONTAL, 0, 100, 50);

    private JLabel lblFuelLevelOnStart;
    private JLabel lblFuelLevelOnFinish;

    private Car selectedCar;

    private JCheckBox checkBoxRefreshFields;

    /**
     * Constructor for creating an object
     *
     * @param mainWindow parent window (class MainWindow)
     */
    public AverageFuelConsumptionPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setLayout(new FlowLayout());
        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel pnlButton = new JPanel();

        JPanel fieldsPanel = new JPanel(new GridLayout(13, 1));

        //create labels with input fields
        String[] labelsText = new String[]{
                "������ ���������� �� ������ ���� (��):",
                "������ ���������� �� ����� ���� (��):",};
        JTextField[] inputFields = new JTextField[]{
                txtFldMileageStart,
                txtFldMileageFinish};

        for (int i = 0; i < inputFields.length; i++) {
            JLabel lblInput = createLabel(labelsText[i], Font.ITALIC, 16);
            inputFields[i].setFont(new Font("Arial", Font.BOLD, 18));
            fieldsPanel.add(lblInput, Component.LEFT_ALIGNMENT);
            fieldsPanel.add(inputFields[i]);
        }

        JLabel lblAdditionalFuel = createLabel(
                "���-�� ������������� ������� (�.):", Font.ITALIC, 16);
        fieldsPanel.add(lblAdditionalFuel, Component.LEFT_ALIGNMENT);
        txtFldAdditionalFuel.setFont(new Font("Arial", Font.BOLD, 18));
        fieldsPanel.add(txtFldAdditionalFuel);

        labelsText = new String[]{
                "��������� ������� ������� �� ������ ����:",
                "��������� ������� ������� �� ����� ����:"};
        JSlider[] sliders = new JSlider[]{
                sliderFuelAmountOnStart,
                sliderFuelAmountOnFinish};

        lblFuelLevelOnStart = createLabel("", Font.PLAIN, 18);
        lblFuelLevelOnStart.setHorizontalAlignment(SwingConstants.CENTER);
        lblFuelLevelOnFinish = createLabel("", Font.PLAIN, 18);
        lblFuelLevelOnFinish.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel[] lblFuelLevels = new JLabel[]{
                lblFuelLevelOnStart,
                lblFuelLevelOnFinish
        };

        for (int i = 0; i < sliders.length; i++) {
            JLabel lblInput = createLabel(labelsText[i], Font.ITALIC, 16);
            initSlider(sliders[i]);
            sliders[i].addChangeListener(new DisplayFuelSliderValue());
            fieldsPanel.add(lblInput, Component.LEFT_ALIGNMENT);
            fieldsPanel.add(lblFuelLevels[i], Component.CENTER_ALIGNMENT);
            fieldsPanel.add(sliders[i], Component.CENTER_ALIGNMENT);
        }

        //create refresh fields checkBox
        JPanel pnlRefreshInputData = new JPanel(new FlowLayout());
        checkBoxRefreshFields = new JCheckBox();
        pnlRefreshInputData.add(checkBoxRefreshFields);
        pnlRefreshInputData.add(createLabel(
                "�������� ���� ����� ����� �������.", Font.PLAIN, 16));
        fieldsPanel.add(pnlRefreshInputData);

        //create calculate button
        JButton btnCalculate = new JButton("����������");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalculate.setPreferredSize(new Dimension(300, 50));
        btnCalculate.addActionListener(new CalculateAverageConsumptionClickListener());
        pnlButton.add(btnCalculate);

        formPanel.add(pnlButton, BorderLayout.SOUTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.setPreferredSize(new Dimension(370, 510));

        add(formPanel);
        setBorder(BorderFactory.createEtchedBorder());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                refreshFieldsToDefault();
                updateFields(mainWindow.getSelectedCar());
            }
        });
    }

    /**
     * Updates input field information <br>
     * based on current selected car
     *
     * @param currentCar current selected car
     */
    public void updateFields(Car currentCar) {
        selectedCar = currentCar;

        if (currentCar == null) {
            refreshFieldsToDefault();
            return;
        }

        String totalPassedDistance = String.valueOf(
                (int) currentCar.getTotalPassedDistance());
        txtFldMileageStart.setText(totalPassedDistance);
        txtFldMileageFinish.setText(totalPassedDistance);
        lblFuelLevelOnStart.setText(
                sliderFuelAmountOnStart.getValue() * currentCar.getFuelTankCapacity() / 100f + "�.");
        lblFuelLevelOnFinish.setText(
                sliderFuelAmountOnFinish.getValue() * currentCar.getFuelTankCapacity() / 100f + "�.");
    }

    /**
     * Refreshes input fields to default values
     */
    private void refreshFieldsToDefault() {
        txtFldMileageStart.setText("0");
        txtFldMileageFinish.setText("0");
        txtFldAdditionalFuel.setText("0");
        sliderFuelAmountOnStart.setValue(50);
        sliderFuelAmountOnFinish.setValue(50);
        lblFuelLevelOnStart.setText("");
        lblFuelLevelOnFinish.setText("");
    }

    /**
     * Adds log line to car
     *
     * @param car            driven car
     * @param wastedLiters   amount of wasted liters
     * @param finishMileage  finish mileage
     * @param passedDistance passed distance
     */
    private void addOperationCarLog(Car car, float wastedLiters,
                                    float finishMileage, float passedDistance) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        String log = String.format(
                """
                          ����� ��������: %s
                                ������������� ������� - %.2f �.
                                ������� ������� ������ ������� - %.2f �./100��.
                                ��������� ���������� - %d ��.
                                ������ ���������� �� ����� �������� - %d ��.
                        """, formatter.format(date),
                wastedLiters, car.getUserAverageFuelConsumption(),
                (int) passedDistance, (int) finishMileage
        );

        car.addOperationLog(log);
    }

    /**
     * Init input slider
     *
     * @param slider to init
     */
    private void initSlider(JSlider slider) {
        //Turn on labels at major tick marks.
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        //Create the label table
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(0, createLabel("0", Font.BOLD, 13));
        labelTable.put(50, createLabel("0.5", Font.BOLD, 13));
        labelTable.put(100, createLabel("1", Font.BOLD, 13));
        slider.setLabelTable(labelTable);
    }

    /**
     * creates label with input text and arial font
     *
     * @param text what to display
     * @return created instance
     */
    private JLabel createLabel(String text, int fontStyle, int size) {
        JLabel inputLabel = new JLabel(text);
        inputLabel.setFont(new Font("Arial", fontStyle, size));
        inputLabel.setForeground(Color.black);
        return inputLabel;
    }

    /**
     * Calculate button listener
     */
    class CalculateAverageConsumptionClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(txtFldMileageStart.getText(), "") ||
                    Objects.equals(txtFldMileageFinish.getText(), "")) {
                showErrorMessageDialog("������ ��������", "��������� ������!");
                return;
            } else if (selectedCar == null) {
                showErrorMessageDialog("������ ��������", "�������� ����������.");
                return;
            }

            float fuelOnStartPercents = sliderFuelAmountOnStart.getValue();
            float fuelOnFinishPercents = sliderFuelAmountOnFinish.getValue();

            if (Objects.equals(txtFldAdditionalFuel.getText(), "")) {
                txtFldAdditionalFuel.setText("0");
            }

            float mileageStart;
            float mileageFinish;
            float filledFuelAmount;
            float wastedFuelAmount;
            try {
                mileageStart = Float.parseFloat(txtFldMileageStart.getText());
                mileageFinish = Float.parseFloat(txtFldMileageFinish.getText());
                filledFuelAmount = Float.parseFloat(txtFldAdditionalFuel.getText());
                wastedFuelAmount = selectedCar.getFuelTankCapacity() *
                        (fuelOnStartPercents - fuelOnFinishPercents) / 100f + filledFuelAmount;

                if (filledFuelAmount + fuelOnStartPercents / 100f * selectedCar.getFuelTankCapacity() >
                        selectedCar.getFuelTankCapacity()){
                    showErrorMessageDialog("������ ��������",
                            "���-�� ������������� ������� ���������" +
                                    "\n����� ���� ����������!");
                    return;
                }

                if (filledFuelAmount < 0f) {
                    showErrorMessageDialog("������ ��������",
                            "������� ������� �������� ������������� �������.");
                    return;
                }
                if (wastedFuelAmount < 0f) {
                    showErrorMessageDialog("������ ��������",
                            "������� ������� �������� ������ ������� � ����.");
                    return;
                }
                if (mileageStart > mileageFinish) {
                    showErrorMessageDialog("������ ��������",
                            "������� ������� �������� �������.");
                    return;
                }
                else if (mileageStart < selectedCar.getTotalPassedDistance()) {
                    showErrorMessageDialog("������ ��������", "������" +
                            " �� ������ ��������\n�� ����� ���� ������ ��������!");
                    return;
                }
            }
            catch (Exception exception) {
                showErrorMessageDialog("������ ��������",
                        "��������� ������������ ����� ������.");
                return;
            }

            float passedDistance = mileageFinish - mileageStart;

            float newCarAverageFuelConsumption =
                    CarManager.calculateUserAverageFuelConsumption(passedDistance,
                            wastedFuelAmount, selectedCar);
            float sessionAverageFuelConsumption =
                    CarManager.calculateSessionAverageFuelConsumption(passedDistance,
                            wastedFuelAmount);

            selectedCar.setTotalPassedDistance(mileageFinish);
            selectedCar.setUserTotalFuelWasted(selectedCar.getUserTotalFuelWasted() +
                    wastedFuelAmount);
            selectedCar.setUserTotalPassedDistance(
                    selectedCar.getUserTotalPassedDistance() + (int) passedDistance);

            String info = String.format("���������� " +
                            selectedCar.getModel() + ":" +
                            "\n���-�� ������������ �������: %.1f�." +
                            "\n���������� ����������: %.1f��." +
                            "\n������� ������ �������: %.1f�. �� 100 ��.\n" +
                            "\n������� ����� ������� ������ �������: %.1f�. �� 100��.",
                    wastedFuelAmount, passedDistance, sessionAverageFuelConsumption,
                    newCarAverageFuelConsumption);
            JOptionPane.showMessageDialog(null, info,
                    "��������� ��������", JOptionPane.INFORMATION_MESSAGE);


            addOperationCarLog(selectedCar, wastedFuelAmount, mileageFinish, passedDistance);

            mainWindow.updateCarInfoFields(selectedCar);

            if (checkBoxRefreshFields.isSelected()) {
                refreshFieldsToDefault();
            } else {
                updateFields(selectedCar);
            }
        }
    }

    /**
     * Change value listener for fuel slider
     */
    class DisplayFuelSliderValue implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource().equals(sliderFuelAmountOnStart)) {
                if (selectedCar == null) {
                    lblFuelLevelOnStart.setText("");
                } else {
                    lblFuelLevelOnStart.setText(selectedCar.getFuelTankCapacity() *
                            sliderFuelAmountOnStart.getValue() / 100f + "�.");
                }
            } else if (e.getSource().equals(sliderFuelAmountOnFinish)) {
                if (selectedCar == null) {
                    lblFuelLevelOnFinish.setText("");
                } else {
                    lblFuelLevelOnFinish.setText(selectedCar.getFuelTankCapacity() *
                            sliderFuelAmountOnFinish.getValue() / 100f + "�.");
                }
            }
        }
    }

    /**
     * displays common JOptionPane with error message
     *
     * @param title     title of option pane
     * @param errorText error message (text)
     */
    private void showErrorMessageDialog(String title, String errorText) {
        JOptionPane.showMessageDialog(null, errorText,
                title, JOptionPane.ERROR_MESSAGE);
    }
}
