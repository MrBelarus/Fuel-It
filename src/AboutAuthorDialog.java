import Utils.ImageScaller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * JDialog class
 * Displays essential information about author
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class AboutAuthorDialog extends JDialog {
    private JPanel pnlMain;
    private JPanel pnlAuthorInfo;
    private JPanel pnlClose;

    private Dimension dialogSize = new Dimension(400, 500);

    public AboutAuthorDialog(MainWindow owner){
        super(owner, "Автор", true);
        pnlMain = new JPanel(new BorderLayout());

        createCloseSpace();
        createAuthorInfoSpace();

        pnlMain.add(pnlClose, BorderLayout.SOUTH);
        pnlMain.add(pnlAuthorInfo, BorderLayout.CENTER);

        setupFrame(pnlMain);
    }

    /**
     * creates necessary UI objects for author info
     */
    private void createAuthorInfoSpace(){
        pnlAuthorInfo = new JPanel(new BorderLayout());

        Image imgPhoto = ImageScaller.scaleImage(
                Application.getAppPath() + "\\src\\Resources\\Images\\AppIcon_v2.png",
                new Dimension(240, 240),
                Image.SCALE_SMOOTH);
        JLabel lblAuthorPhoto = new JLabel(new ImageIcon(imgPhoto));
        pnlAuthorInfo.add(lblAuthorPhoto, BorderLayout.CENTER);

        JPanel pnlAuthorInfoPanel = new JPanel(new GridBagLayout());
        JPanel pnlAuthorText = new JPanel(new GridLayout(4, 1));
        pnlAuthorText.add(new JLabel("Автор", SwingConstants.CENTER));
        pnlAuthorText.add(new JLabel("студент группы 10702119", SwingConstants.CENTER));
        pnlAuthorText.add(new JLabel("Кургей Владислав Юрьевич", SwingConstants.CENTER));
        pnlAuthorText.add(new JLabel("lakey38015@gmail.com", SwingConstants.CENTER));

        //set constraints of GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;

        pnlAuthorInfoPanel.add(pnlAuthorText, gbc);
        pnlAuthorInfo.add(pnlAuthorInfoPanel, BorderLayout.SOUTH);
    }

    /**
     * creates necessary UI objects for close
     */
    private void createCloseSpace(){
        pnlClose = new JPanel(new FlowLayout());
        pnlClose.add(createCloseButton());
    }

    /**
     * creates close button
     * @return created JPanel instance
     */
    private JButton createCloseButton(){
        JButton btn = new JButton("Назад");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(120, 50));
        return btn;
    }

    /**
     * Sets up core setting to dialog about author
     */
    public void setupFrame(JPanel pnlMain) {
        setLayout(new BorderLayout());
        setSize(dialogSize);
        add(pnlMain);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
