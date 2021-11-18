package FuelIt.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Utility class to create swing and awt UI elements
 */
public class UICreator {
    /**
     * Creates an instance of JButton
     * @param text info on button
     * @param prefSize preferred size
     * @param listener ActionListener
     * @param backgroundColor
     * @return instance of created button
     */
    public static JButton CreateButton(String text, Dimension prefSize,
                                        ActionListener listener, Color backgroundColor)
    {
        JButton btn = new JButton(text);
        btn.setPreferredSize(prefSize);
        btn.addActionListener(listener);
        btn.setBackground(backgroundColor);
        return  btn;
    }
}
