import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AboutApplicationDialog extends JDialog {
    private JPanel pnlMain;
    private Dimension dialogSize = new Dimension(300, 200);

    public AboutApplicationDialog(MainWindow mainWindow){
        super(mainWindow, "Об приложении");

        pnlMain = new JPanel(new BorderLayout(10, 10));
        pnlMain.add(new JLabel("ASDASD"), BorderLayout.CENTER);

        System.out.println(SwingUtilities.isEventDispatchThread());
        setupDialog();
    }

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
        setVisible(active);
    }
}
