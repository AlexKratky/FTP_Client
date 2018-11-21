/**
    * @name        CustomButton.java
    * @version     v0.1 (19-11-2018)[dd-mm-yyyy]
    * @link        https://alexkratky.cz          Author website
    * @author      Alex Kratky <info@alexkratky.cz>
    * @copyright   Copyright (c) 2018 Alex Kratky
    * @license     http://opensource.org/licenses/mit-license.php  MIT License
    * @description Custom button
*/

package CustomComponents;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultButtonModel;

public class CustomButton extends JButton {
    private Color foreground = new Color(195, 7, 63);
    private Color background = new Color(26, 26, 29);
    private int padding = 12;

    /**
     * Constructor to create a button with specified text
     * 
     * @param text Text of button
     */
    public CustomButton(String text) {
        super(text);
        createButton();
    }

    /**
     * Constructor to create a button with specified text, foreground and background
     * 
     * @param text       Text of button
     * @param foreground Color of foreground
     * @param background Color of background
     */
    public CustomButton(String text, Color foreground, Color background) {
        super(text);
        this.foreground = foreground;
        this.background = background;
        createButton();
    }

    /**
     * Method to create a button, called from constructor
     * 
     * @return void
     */
    public void createButton() {
        this.setBackground(background);
        this.setForeground(foreground);
        this.setFocusPainted(false);
        this.setModel(new FixedStateButtonModel());
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(foreground, 2),
                BorderFactory.createLineBorder(background, padding)));
        this.setFont(new Font("Ubuntu Light", Font.BOLD, 20));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonHover();
            }

            public void mouseExited(MouseEvent e) {
                buttonHoverLeave();
            }
        });
    }

    /**
     * Method handle mouseEntered()
     * 
     * @return void
     */
    public void buttonHover() {
        this.setForeground(Color.WHITE);
        this.setBackground(foreground);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(foreground, 2),
                BorderFactory.createLineBorder(foreground, padding)));
    }

    /**
     * Method handle mouseExited()
     * 
     * @return void
     */
    public void buttonHoverLeave() {
        this.setBackground(background);
        this.setForeground(foreground);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(foreground, 2),
                BorderFactory.createLineBorder(background, padding)));
    }

    /**
     * Custom model for CustomButton
     */
    public class FixedStateButtonModel extends DefaultButtonModel {

        @Override
        public boolean isPressed() {
            return false;
        }

        @Override
        public boolean isRollover() {
            return false;
        }

        @Override
        public void setRollover(boolean b) {

        }

    }
}