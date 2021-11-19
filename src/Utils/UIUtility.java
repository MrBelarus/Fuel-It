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
     * @param backgroundColor
     * @return instance of created button
     */
    public static JButton createButton(String text, Dimension prefSize,
                                       ActionListener listener, Color backgroundColor) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(prefSize);
        btn.addActionListener(listener);
        btn.setBackground(backgroundColor);
        return btn;
    }

    /**
     * adds icon to JButton instance
     *
     * @param toBtn    instance
     * @param iconPath path to Icon
     * @return
     */
    public static JButton addIconToButton(JButton toBtn, String iconPath) {
        ImageIcon questionIcon = new ImageIcon(ImageScaller.scaleImage(
                iconPath,
                toBtn.getPreferredSize(),
                Image.SCALE_SMOOTH));
        toBtn.setIcon(questionIcon);
        return toBtn;
    }
}
