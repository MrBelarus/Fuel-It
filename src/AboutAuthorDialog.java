import javax.swing.*;
import java.awt.*;

public class AboutAuthorDialog extends JDialog {
    private JPanel pnlMain;

    public AboutAuthorDialog(JFrame owner){
        super(owner, "Автор");

        pnlMain = new JPanel(new BorderLayout());

        setupDialog();
    }

    private void setupDialog() {
        setLayout(new BorderLayout());
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultLookAndFeelDecorated(true);
        add(pnlMain);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
}
