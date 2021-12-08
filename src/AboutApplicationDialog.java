import Utils.ImageScaller;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * JDialog class used to display about application information
 */
public class AboutApplicationDialog extends JDialog {
    private JPanel pnlMain;

    private JPanel pnlTop;
    private JPanel pnlMiddle;
    private JPanel pnlBottom;

    private Dimension dialogSize = new Dimension(780, 435);

    /**
     * Constructor for creating an object
     * @param mainWindow parent window (class MainWindow)
     */
    public AboutApplicationDialog(MainWindow mainWindow){
        super(mainWindow, "� ���������");

        pnlMain = new JPanel(new BorderLayout(10, 10));

        pnlMain.add(createTopPanel(), BorderLayout.NORTH);
        pnlMain.add(createMiddlePanel(), BorderLayout.CENTER);
        pnlMain.add(createBottomPanel(), BorderLayout.SOUTH);

        setupDialog();
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

    /**
     * enables or disable JDialog instance
     * @param active true - show, false - hide
     */
    public void enableFrame(boolean active) {
        if (active) {
            setSize(dialogSize);
            setLocationRelativeTo(getOwner());
        }
        setVisible(active);
    }

    /**
     * creates necessary UI elements for top panel
     * @return created JPanel instance
     */
    private JPanel createTopPanel(){
        pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblProjectName = new JLabel(
                "���������� ��� ����� ������� �������");
        lblProjectName.setBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pnlTop.add(lblProjectName);
        return pnlTop;
    }

    /**
     * creates necessary UI elements for middle panel
     * @return created JPanel instance
     */
    private JPanel createMiddlePanel(){
        pnlMiddle = new JPanel(new BorderLayout(20, 10));

        JPanel pnlPreview = new JPanel(new FlowLayout());

        Image imgPhoto = ImageScaller.scaleImage(
                "/resources/images/app-preview-v2.png",
                new Dimension(350, 300),
                Image.SCALE_SMOOTH);
        JLabel lblProgramPreview = new JLabel(new ImageIcon(imgPhoto));
        pnlPreview.add(lblProgramPreview);

        JPanel pnlProgramInfo = new JPanel(new BorderLayout());

        JTextArea txtAreaProgramInfo = new JTextArea(15, 1);
        txtAreaProgramInfo.setEditable(false);
        txtAreaProgramInfo.setLineWrap(true);
        txtAreaProgramInfo.setFont(new Font("Arial", Font.BOLD, 14));
        txtAreaProgramInfo.setForeground(Color.black);
        txtAreaProgramInfo.setBorder(
                BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        txtAreaProgramInfo.setBackground(new Color(238, 238, 238));
        JScrollPane txtAreaScroll = new JScrollPane(txtAreaProgramInfo);
        txtAreaScroll.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        txtAreaScroll.getViewport().setOpaque(false);
        txtAreaScroll.setOpaque(false);

        txtAreaProgramInfo.setText("��������� ���������:\n" +
                "   -����� ���� �� �����������\n" +
                "   -���������/������� ���������� �� ����������\n" +
                "   -������������ ���-�� ������� �� �������\n" +
                "���������������� ����������\n" +
                "   -������������ ������ �� ���-�� �������\n" +
                "���������������� ����������\n" +
                "   -������������ ������� ������ �������\n" +
                "���������������� ����������");

        pnlProgramInfo.add(txtAreaScroll, BorderLayout.CENTER);

        pnlMiddle.add(pnlPreview, BorderLayout.WEST);
        pnlMiddle.add(pnlProgramInfo, BorderLayout.CENTER);

        return pnlMiddle;
    }

    /**
     * creates necessary UI elements for bottom panel
     * @return created JPanel instance
     */
    private JPanel createBottomPanel(){
        pnlBottom = new JPanel(new BorderLayout());

        JPanel pnlAppVersion = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel lblAppVersion = new JLabel("������ " +
                Application.VERSION, SwingConstants.CENTER);
        lblAppVersion.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pnlAppVersion.add(lblAppVersion);

        JPanel pnlExit = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnExit = new JButton("�����");
        btnExit.setFont(new Font("Arial", Font.BOLD, 12));
        btnExit.setBackground(new Color(255, 68, 68));
        btnExit.addActionListener(new ExitDialogListener(this));
        pnlExit.add(btnExit);

        pnlBottom.add(pnlExit, BorderLayout.EAST);
        pnlBottom.add(pnlAppVersion, BorderLayout.WEST);
        return pnlBottom;
    }

    /**
     * Add exit dialog button listener
     */
    class ExitDialogListener implements ActionListener {
        private JDialog dialog;

        public ExitDialogListener(JDialog dialog){
            this.dialog = dialog;
        }

        public void actionPerformed(ActionEvent e) {
            dialog.setVisible(false);
        }
    }
}
