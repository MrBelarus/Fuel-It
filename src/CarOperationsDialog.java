import Entities.Car;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class CarOperationsDialog extends JDialog{
    private MainWindow mainWindow;

    private JTextArea txtAreaLog;
    private JPanel pnlMain;

    private Dimension dialogSize = new Dimension(450, 305);

    /**
     * Constructor for creating an object
     * @param mainWindow parent window (class MainWindow)
     */
    public CarOperationsDialog(MainWindow mainWindow){
        super(mainWindow, "Отчет по автомобилю");
        this.mainWindow = mainWindow;

        pnlMain = new JPanel(new BorderLayout(10, 10));
        createTxtAreaLog();

        setupDialog();
    }

    private void createTxtAreaLog() {
        //add panel for cars info
        JPanel pnlLogInfo = new JPanel(new BorderLayout(5, 5));
        txtAreaLog = new JTextArea(0, 1);
        txtAreaLog.setEditable(false);
        txtAreaLog.setLineWrap(true);
        txtAreaLog.setFont(new Font("Arial", Font.BOLD, 14));
        txtAreaLog.setForeground(Color.black);
        txtAreaLog.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        //create scroll for car txtArea
        JScrollPane txtAreaScroll = new JScrollPane(txtAreaLog);
        txtAreaScroll.setPreferredSize(new Dimension(400, 0));
        txtAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        txtAreaScroll.getViewport().setOpaque(false);
        txtAreaScroll.setOpaque(false);

        //add created txtArea with scroll to car info panel
        pnlMain.add(txtAreaScroll, BorderLayout.CENTER);
    }

    /**
     * Sets main settings to dialog about application
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

    private void UpdateLogInfo(Car car){
        if (car == null){
            txtAreaLog.setText("Выберите машину чтобы увидеть список операций!");
        }

        StringBuilder logBuilder = new StringBuilder();
        ArrayList<String> operations = car.getOperationsLog();
        if (operations == null || operations.size() == 0){
            txtAreaLog.setText(String.format("Отсутствуют операции над автомобилем %s!",
                    car.getModel()));
            return;
        }

        logBuilder.append("Список операций над автомобилем ").append(
                car.getModel()).append("\n\n");
        for (String line : operations){
            logBuilder.append(line).append("\n");
        }

        txtAreaLog.setText(logBuilder.toString());
    }

    /**
     * enables or disable JDialog instance
     * @param active true - show, false - hide
     */
    public void enableFrame(boolean active) {
        if (active) {
            setSize(dialogSize);
            setLocationRelativeTo(getOwner());
            UpdateLogInfo(mainWindow.getSelectedCar());
        }
        setVisible(active);
    }
}
