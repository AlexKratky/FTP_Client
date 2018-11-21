/**
    * @name        CustomInput.java
    * @version     v0.1 (19-11-2018)[dd-mm-yyyy]
    * @link        https://alexkratky.cz          Author website
    * @author      Alex Kratky <info@alexkratky.cz>
    * @copyright   Copyright (c) 2018 Alex Kratky
    * @license     http://opensource.org/licenses/mit-license.php  MIT License
    * @description Custom Input
*/

package CustomComponents;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

public class CustomInput extends JTextField {
    private Color foreground = new Color(195, 7, 63);
    private Color background = new Color(26, 26, 29);
    int padding = 8;

    /**
     * Create custom input with default values.
     */
    public CustomInput() {
        super(15);
        createInput();
    }

    /**
     * Create custom input with specified foreground and background color.
     * 
     * @param foreground Color(java.awt.Color) of foreground.
     * @param background Color(java.awt.Color) of background.
     */
    public CustomInput(Color foreground, Color background) {
        super(15);
        this.foreground = foreground;
        this.background = background;
        createInput();
    }

    /**
     * Create custom input with specified text value.
     * 
     * @param value Default text value.
     */
    public CustomInput(String value) {
        super(value, 15);
        createInput();
    }

    /**
     * Method to set custom styles specified by constructor and called by
     * constructor.
     * 
     * @return void
     */
    public void createInput() {
        this.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        this.setBackground(background);
        this.setForeground(foreground);
        // this.setColumns(15);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(foreground, 2),
                BorderFactory.createLineBorder(background, padding)));
    }
}