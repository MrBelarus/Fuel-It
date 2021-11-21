import Entities.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private JTextField txtFldFuel = new JTextField("");
    private JTextField txtFldDistance = new JTextField("");

    public AverageFuelConsumptionPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setLayout(new FlowLayout());
        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel pnlButton = new JPanel();

        //create labels with input fields
        String[] labelsText = new String[]{
                "Введите кол-во потраченного топлива (л):",
                "Введите пройденное расстояние (км):"};
        JTextField[] inputFields = new JTextField[]{
                txtFldFuel,
                txtFldDistance,};
        JPanel fieldsPanel = new JPanel(new GridLayout(inputFields.length * 2, 1));

        for (int i = 0; i < inputFields.length; i++) {
            JLabel inputLabel = new JLabel(labelsText[i]);
            inputLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            fieldsPanel.add(inputLabel, Component.LEFT_ALIGNMENT);
            fieldsPanel.add(inputFields[i]);
        }

        //create calculate button
        JButton btnCalculate = new JButton("Расчитать");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalculate.setPreferredSize(new Dimension(300, 50));
        btnCalculate.addActionListener(new CalculateAverageConsumptionClickListener());
        pnlButton.add(btnCalculate);

        formPanel.add(pnlButton, BorderLayout.SOUTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.setPreferredSize(new Dimension(350, 150));

        add(formPanel);
        setBorder(BorderFactory.createEtchedBorder());
    }

    class CalculateAverageConsumptionClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Car selectedCar = mainWindow.getSelectedCar();

            if (Objects.equals(txtFldFuel.getText(), "") ||
                    Objects.equals(txtFldDistance.getText(), "")){
                JOptionPane.showMessageDialog(null,
                        "Заполните данные!",
                        "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (selectedCar == null) {
                JOptionPane.showMessageDialog(null, "Выберите автомобиль.",
                        "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float passedDistanceAmount;
            float wastedFuelAmount;
            try {
                passedDistanceAmount= Float.parseFloat(txtFldDistance.getText());
                wastedFuelAmount = Float.parseFloat(txtFldFuel.getText());
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(null,
                        "Проверьте правильность ввода данных.",
                        "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float newCarAverageFuelConsumption =
                    CarManager.calculateAverageFuelConsumption(passedDistanceAmount,
                    wastedFuelAmount, selectedCar);
            float sessionAverageFuelConsumption =
                    CarManager.calculateSessionAverageFuelConsumption(passedDistanceAmount,
                            wastedFuelAmount);

            String info = String.format("Новый средний расход " +
                    "топлива автомобиля " + selectedCar.getModel() +
                    ":\n%.1f л. на 100 км.\nСредний расход топлива сессии на " +
                    "%.1f км = %.1f л.",
                    newCarAverageFuelConsumption, passedDistanceAmount, sessionAverageFuelConsumption);
            JOptionPane.showMessageDialog(null, info,
                    "Результат операции", JOptionPane.INFORMATION_MESSAGE);

            mainWindow.updateCarInfoText(selectedCar);

            txtFldFuel.setText("");
            txtFldDistance.setText("");
        }
    }
}
