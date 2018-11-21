/**
    * @name        CustomInputPassword.java
    * @version     v0.1 (19-11-2018)[dd-mm-yyyy]
    * @link        https://alexkratky.cz          Author website
    * @author      Alex Kratky <info@alexkratky.cz>
    * @copyright   Copyright (c) 2018 Alex Kratky
    * @license     http://opensource.org/licenses/mit-license.php  MIT License
    * @description Custom Passowrd Input
*/

package CustomComponents;

import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

public class CustomInputPassword extends JPasswordField {
    private Color foreground = new Color(195, 7, 63);
    private Color background = new Color(26, 26, 29);
    int padding = 8;

    /**
     * Create custom input password with default values
     */
    public CustomInputPassword() {
        super(15);
        createInput();
    }

    /**
     * Create custom input password with specified foreground and background color.
     * 
     * @param foreground Color(java.awt.Color) of foreground.
     * @param background Color(java.awt.Color) of background.
     */
    public CustomInputPassword(Color foreground, Color background) {
        super(15);
        this.foreground = foreground;
        this.background = background;
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
