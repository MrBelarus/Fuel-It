import Entities.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Input data panel
 * To calculate fuel from distance
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class FuelFromDistancePanel extends JPanel {
    private MainWindow mainWindow;
    private JTextField txtFldDistance;

    /**
     * Constructor for creating an object
     *
     * @param mainWindow parent window (class MainWindow)
     */
    public FuelFromDistancePanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setLayout(new FlowLayout());
        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel pnlButton = new JPanel();

        //create calculate button
        JButton btnCalculate = new JButton("??????????");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalculate.setPreferredSize(new Dimension(300, 50));
        btnCalculate.addActionListener(new CalculateFuelFromDistanceClickListener());
        pnlButton.add(btnCalculate);

        //create labels with input fields
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1));
        JLabel inputLabel = new JLabel("??????? ?????????? (??):");
        inputLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        txtFldDistance = new JTextField("");
        txtFldDistance.setFont(new Font("Arial", Font.BOLD, 16));
        fieldsPanel.add(inputLabel, Component.LEFT_ALIGNMENT);
        fieldsPanel.add(txtFldDistance);

        formPanel.add(pnlButton, BorderLayout.SOUTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.setPreferredSize(new Dimension(300, 125));

        add(formPanel);
        setBorder(BorderFactory.createEtchedBorder());
    }

    /**
     * Calculate button listener
     */
    class CalculateFuelFromDistanceClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Car selectedCar = mainWindow.getSelectedCar();

            if (selectedCar == null) {
                JOptionPane.showMessageDialog(null, "???????? ??????????.",
                        "?????? ????????", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (Objects.equals(txtFldDistance.getText(), "")) {
                JOptionPane.showMessageDialog(null,
                        "????????? ??????!",
                        "?????? ????????", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float distanceAmount;
            try {
                distanceAmount = Float.parseFloat(txtFldDistance.getText());
                if (distanceAmount < 0){
                    JOptionPane.showMessageDialog(null,
                            "?????????? ?? ?????? ????????????? ??????!",
                            "?????? ????????", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,
                        "????????? ???????????? ????? ??????.",
                        "?????? ????????", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float userResult =
                    CarManager.calculateUserFuelAmount(distanceAmount, selectedCar);
            float factoryResult =
                    CarManager.calculateFactoryFuelAmount(distanceAmount, selectedCar);

            String info = String.format("?????????? %s:" +
                            "\n????????? ??? ??????? " +
                            "??????? ??????? ???????: %.1f?" +
                            "\n????????? ??? ????????? " +
                            "??????? ??????? ???????: %.1f?",
                    selectedCar.getModel(), userResult, factoryResult);
            JOptionPane.showMessageDialog(null, info,
                    "????????? ????????", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
