import Entities.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Input data panel
 * To calculate distance from fuel
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class DistanceFromFuelPanel extends JPanel {
    private MainWindow mainWindow;
    private JTextField txtFldFuel;

    public DistanceFromFuelPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setLayout(new FlowLayout());
        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel pnlButton = new JPanel();

        //create labels with input fields
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1));
        JLabel inputLabel = new JLabel("Введите кол-во топлива (л):");
        inputLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        txtFldFuel = new JTextField("");
        fieldsPanel.add(inputLabel, Component.LEFT_ALIGNMENT);
        fieldsPanel.add(txtFldFuel);

        //create calculate button
        JButton btnCalculate = new JButton("Расчитать");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalculate.setPreferredSize(new Dimension(300, 50));
        btnCalculate.addActionListener(new CalculateDistanceFromFuelClickListener());
        pnlButton.add(btnCalculate);

        formPanel.add(pnlButton, BorderLayout.SOUTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.setPreferredSize(new Dimension(300, 100));

        add(formPanel);
        setBorder(BorderFactory.createEtchedBorder());
    }

    class CalculateDistanceFromFuelClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Car selectedCar = mainWindow.getSelectedCar();

            if (Objects.equals(txtFldFuel.getText(), "")){
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

            float fuelAmount;
            try {
                fuelAmount = Float.parseFloat(txtFldFuel.getText());
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null,
                        "Проверьте правильность ввода данных.",
                        "Ошибка операции", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float result = CarManager.calculateDistance(fuelAmount, selectedCar);
            String info = String.format("Результат: "
                    + "%.2f" + "км.\nЕсли использовать автомобиль " +
                    selectedCar.getModel() + ".", result);
            JOptionPane.showMessageDialog(null, info,
                    "Результат операции", JOptionPane.INFORMATION_MESSAGE);

            txtFldFuel.setText("");
        }
    }
}
