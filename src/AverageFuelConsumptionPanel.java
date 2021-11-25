import Entities.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private JSlider sliderFuelAmountOnStart = new JSlider(
            JSlider.HORIZONTAL, 0, 100, 50);
    private JSlider sliderFuelAmountOnFinish = new JSlider(
            JSlider.HORIZONTAL, 0, 100, 50);

    public AverageFuelConsumptionPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setLayout(new FlowLayout());
        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel pnlButton = new JPanel();

        JPanel fieldsPanel = new JPanel(new GridLayout(8, 1));

        //create labels with input fields
        String[] labelsText = new String[]{
                "Пробег автомобиля на начало пути (км):",
                "Пробег автомобиля на конец пути (км):",};
        JTextField[] inputFields = new JTextField[]{
                txtFldMileageStart,
                txtFldMileageFinish};

        for (int i = 0; i < inputFields.length; i++) {
            JLabel inputLabel = createLabel(labelsText[i], Font.ITALIC, 16);
            inputFields[i].setFont(new Font("Arial", Font.BOLD, 18));
            fieldsPanel.add(inputLabel, Component.LEFT_ALIGNMENT);
            fieldsPanel.add(inputFields[i]);
        }

        labelsText = new String[]{
                "Примерный уровень топлива до начала пути:",
                "Примерный уровень топлива на конец пути:"};
        JSlider[] sliders = new JSlider[]{
                sliderFuelAmountOnStart,
                sliderFuelAmountOnFinish};

        for (int i = 0; i < sliders.length; i++) {
            JLabel inputLabel = createLabel(labelsText[i], Font.ITALIC, 16);
            initSlider(sliders[i]);
            fieldsPanel.add(inputLabel, Component.LEFT_ALIGNMENT);
            fieldsPanel.add(sliders[i], Component.CENTER_ALIGNMENT);
        }

        //create calculate button
        JButton btnCalculate = new JButton("Расчитать");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalculate.setPreferredSize(new Dimension(300, 50));
        btnCalculate.addActionListener(new CalculateAverageConsumptionClickListener());
        pnlButton.add(btnCalculate);

        formPanel.add(pnlButton, BorderLayout.SOUTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.setPreferredSize(new Dimension(370, 405));

        add(formPanel);
        setBorder(BorderFactory.createEtchedBorder());
    }

    /**
     * Calculate button listener
     */
    class CalculateAverageConsumptionClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Car selectedCar = mainWindow.getSelectedCar();

            if (Objects.equals(txtFldMileageStart.getText(), "") ||
                    Objects.equals(txtFldMileageFinish.getText(), "")) {
                JOptionPane.showMessageDialog(null,
                        "Заполните данные!",
                        "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (selectedCar == null) {
                JOptionPane.showMessageDialog(null, "Выберите автомобиль.",
                        "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float fuelOnStartPercents = sliderFuelAmountOnStart.getValue();
            float fuelOnFinishPercents = sliderFuelAmountOnFinish.getValue();

            if (fuelOnFinishPercents > fuelOnStartPercents){
                JOptionPane.showMessageDialog(null,
                        "Неверно указаны значения топлива в баке.",
                        "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float mileageStart;
            float mileageFinish;
            try {
                mileageStart = Float.parseFloat(txtFldMileageStart.getText());
                mileageFinish = Float.parseFloat(txtFldMileageFinish.getText());

                if (mileageStart > mileageFinish){
                    JOptionPane.showMessageDialog(null,
                            "Неверно указаны значения пробега.",
                            "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else if (mileageStart < selectedCar.getTotalPassedDistance()){
                    JOptionPane.showMessageDialog(null,
                            "Пробег на начало движения\nне может быть меньше текущего!",
                            "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,
                        "Проверьте правильность ввода данных.",
                        "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float wastedFuelAmount = selectedCar.getFuelTankCapacity() *
                    (fuelOnStartPercents - fuelOnFinishPercents) / 100f;
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
            selectedCar.setUserTotalPassedDistance(selectedCar.getUserTotalPassedDistance() +
                    (int)passedDistance);

            String info = String.format("Текущий средний расход " +
                            "топлива автомобиля " + selectedCar.getModel() +
                            ":\n%.1f л. на 100 км.\nСредний расход топлива сессии на " +
                            "%.1f км = %.1f л.",
                    newCarAverageFuelConsumption, passedDistance, sessionAverageFuelConsumption);
            JOptionPane.showMessageDialog(null, info,
                    "Результат операции", JOptionPane.INFORMATION_MESSAGE);


            addOperationCarLog(selectedCar, wastedFuelAmount, mileageFinish, passedDistance);

            mainWindow.updateCarInfoText(selectedCar);

            txtFldMileageStart.setText("");
            txtFldMileageFinish.setText("");
            sliderFuelAmountOnStart.setValue(50);
            sliderFuelAmountOnFinish.setValue(50);
        }
    }

    /**
     * Adds log line to car
     * @param car driven car
     * @param wastedLiters amount of wasted liters
     * @param finishMileage finish mileage
     * @param passedDistance passed distance
     */
    private void addOperationCarLog(Car car, float wastedLiters,
                                    float finishMileage, float passedDistance){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        String log = String.format(
                """
                  Время операции: %s
                        Израсходовано топлива - %.2f л.
                        Текущий средний расход топлива - %.2f л./100км.
                        Пройденно расстояние - %d км.
                        Пробег автомобиля на конец движения - %d км.
                """, formatter.format(date),
                wastedLiters, car.getUserAverageFuelConsumption(),
                (int)passedDistance, (int)finishMileage
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
     * @param text what to diplay
     * @return created instance
     */
    private JLabel createLabel(String text, int fontStyle, int size) {
        JLabel inputLabel = new JLabel(text);
        inputLabel.setFont(new Font("Arial", fontStyle, size));
        inputLabel.setForeground(Color.black);
        return inputLabel;
    }
}
