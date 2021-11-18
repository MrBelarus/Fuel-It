import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CarSelectDialog extends JDialog {
    private MainWindow mainWindow;

    private JPanel pnlMain;
    private JPanel pnlCars;
    private JPanel pnlButtons;


    public CarSelectDialog(MainWindow mainWindow) {
        super(mainWindow, "Vehicles list");
        this.mainWindow = mainWindow;

        pnlMain = new JPanel(new BorderLayout(20, 10));

        pnlCars = new JPanel(new GridLayout(15, 1, 0, 0));
        pnlCars.setBorder(new BevelBorder(BevelBorder.LOWERED));

        //TODO: add if there is something in save file
        for (int i = 1; i <= 15; i++) {
            JButton btn = new JButton("Car " + i);
            btn.setPreferredSize(new Dimension(450, 70));

            int vgapPanel = 15;
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, vgapPanel));

            btnPanel.setPreferredSize(new Dimension(0, btn.getPreferredSize().height + vgapPanel));   //width is controlled by grid layout
            btnPanel.add(btn);
            pnlCars.add(btnPanel);
        }

        JScrollPane scrollPane = new JScrollPane(pnlCars);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlButtons.setBorder(new BevelBorder(BevelBorder.RAISED));

        pnlMain.add(scrollPane, BorderLayout.CENTER);
        pnlMain.add(pnlButtons, BorderLayout.SOUTH);

        setupFrame(pnlMain);

        //TODO: add new item
//        carsPanel.setLayout(new GridLayout(6, 1, 10, 10));
//        JButton btn = new JButton("Car *");
//        carsPanel.add(btn);
    }

    private void setupFrame(JPanel mainPanel) {
        setLayout(new BorderLayout());
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultLookAndFeelDecorated(true);
        add(mainPanel);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //override on closing operation for more control
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                enableDialog(false);
            }
        });
    }

    public void enableDialog(boolean active) {
        if (active) {
            setSize(new Dimension(Application.WINDOW_SIZE));
            setLocationRelativeTo(null);
        }
        setVisible(active);
    }
}
