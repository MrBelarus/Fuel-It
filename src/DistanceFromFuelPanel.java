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

    /**
     * Constructor for creating an object
     *
     * @param mainWindow parent window (class MainWindow)
     */
    public DistanceFromFuelPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setLayout(new FlowLayout());
        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel pnlButton = new JPanel();

        //create labels with input fields
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1));
        JLabel inputLabel = new JLabel("������� ���-�� ������� (�):");
        inputLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        txtFldFuel = new JTextField("");
        txtFldFuel.setFont(new Font("Arial", Font.BOLD, 18));
        fieldsPanel.add(inputLabel, Component.LEFT_ALIGNMENT);
        fieldsPanel.add(txtFldFuel);

        //create calculate button
        JButton btnCalculate = new JButton("����������");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalculate.setPreferredSize(new Dimension(300, 50));
        btnCalculate.addActionListener(new CalculateDistanceFromFuelClickListener());
        pnlButton.add(btnCalculate);

        formPanel.add(pnlButton, BorderLayout.SOUTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.setPreferredSize(new Dimension(300, 125));

        add(formPanel);
        setBorder(BorderFactory.createEtchedBorder());
    }

    /**
     * Calculate button listener
     */
    class CalculateDistanceFromFuelClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Car selectedCar = mainWindow.getSelectedCar();

            if (selectedCar == null) {
                JOptionPane.showMessageDialog(null, "�������� ����������.",
                        "������ ��������", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (Objects.equals(txtFldFuel.getText(), "")) {
                JOptionPane.showMessageDialog(null,
                        "��������� ������!",
                        "������ ��������", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float fuelAmount;
            try {
                fuelAmount = Float.parseFloat(txtFldFuel.getText());
                if (fuelAmount < 0){
                    JOptionPane.showMessageDialog(null,
                            "���������� �� ������ ������������� ������!",
                            "������ ��������", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,
                        "��������� ������������ ����� ������.",
                        "������ ��������", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float userResult = CarManager.calculateUserDistance(fuelAmount, selectedCar);
            float factoryResult = CarManager.calculateFactoryDistance(fuelAmount, selectedCar);
            String info = String.format("���������� %s:" +
                            "\n��������� ��� ������� " +
                            "������� ������� �������: %.1f��" +
                            "\n��������� ��� ��������� " +
                            "������� ������� �������: %.1f��",
                    selectedCar.getModel(), userResult, factoryResult);
            JOptionPane.showMessageDialog(null, info,
                    "��������� ��������", JOptionPane.INFORMATION_MESSAGE);

            txtFldFuel.setText("");
        }
    }
}
