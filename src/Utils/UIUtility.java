package Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Utility class to create swing and awt UI elements
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class UIUtility {
    /**
     * Creates an instance of JButton
     *
     * @param text            info on button
     * @param prefSize        preferred size
     * @param listener        ActionListener
     * @return instance of created button
     */
    public static JButton createButton(String text, Dimension prefSize,
                                       ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(prefSize);
        btn.addActionListener(listener);
        return btn;
    }

    /**
     * Creates an instance of JButton
     *
     * @param text            info on button
     * @param prefSize        preferred size
     * @return instance of created button
     */
    public static JButton createButton(String text, Dimension prefSize) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(prefSize);
        return btn;
    }

    /**
     * adds icon to JButton instance
     *
     * @param toBtn    instance
     * @param iconPath path to Icon
     * @return created instance
     */
    public static JButton addIconToButton(JButton toBtn, String iconPath) {
        ImageIcon questionIcon = new ImageIcon(ImageScaller.scaleImage(
                iconPath,
                toBtn.getPreferredSize(),
                Image.SCALE_SMOOTH));
        toBtn.setIcon(questionIcon);
        return toBtn;
    }

    /**
     * Creates empty panel with preferred size
     * @param prefSize preferred size of panel
     * @return created instance
     */
    public static JPanel createEmptyPanel(Dimension prefSize){
        JPanel pnlEmpty = new JPanel();
        pnlEmpty.setPreferredSize(prefSize);
        return pnlEmpty;
    }
}
