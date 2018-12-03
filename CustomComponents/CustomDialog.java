/**
 * @name CustomDialog.java
 * @version v0.1 (27-11-2018)[dd-mm-yyyy]
 * @link https://alexkratky.cz Author website
 * @author Alex Kratky <info@alexkratky.cz>
 * @copyright Copyright (c) 2018 Alex Kratky
 * @license http://opensource.org/licenses/mit-license.php MIT License
 * @description Custom dialogs.
 */

package CustomComponents;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JLabel;
import CustomComponents.CustomButton;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.SimpleAttributeSet;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomDialog extends JDialog {
    /**
     * JFrame reference.
     */
    protected JFrame frame = null;

    /**
     * Default message.
     */
    protected String msg = "";

    /**
     * Default window width.
     */
    protected int w = 300;

    /**
     * Default window height.
     */
    protected int h = 100;

    /**
     * Color of foreground.
     */
    protected Color foreground = new Color(195, 7, 63);

    /**
     * Color of background.
     */
    protected Color background = new Color(26, 26, 29);

    /**
     * Default button(s) (BUTTON_OK)
     */
    protected int buttons = 1;

    /**
     * Default single button width (BUTTON_FULL_WIDTH)
     */
    protected int button_width = 1;

    /**
     * OK button listener.
     */
    protected ActionListener but_ok_listener = null;

    /**
     * CANCEL button listener.
     */
    protected ActionListener but_cancel_listener = null;

    /**
     * Specify the button - OK button.
     */
    public static final int BUTTON_OK = 1;

    /**
     * Specify the button - CANCEL button.
     */
    public static final int BUTTON_CANCEL = 2;

    /**
     * Specify buttons - OK & CANCEL buttons.
     */
    public static final int BUTTON_OK_CANCEL = 3;

    /**
     * Specify that the single button will have full width.
     */
    public static final int BUTTON_FULL_WIDTH = 1;

    /**
     * Specify that the single button will have half width and will be centered.
     */
    public static final int BUTTON_HALF_WIDTH = 2;

    /**
     * Create instance of CustomDialog.
     * 
     * @param frame Parent of CustomDialog.
     */
    public CustomDialog(JFrame frame) {
        super(frame);
        this.frame = frame;
    }

    /**
     * Create instance of CustomDialog.
     * 
     * @param frame Parent of CustomDialog.
     * @param title Title of dialog.
     * @param modal Specify if its modal window or not.
     */
    public CustomDialog(JFrame frame, String title, boolean modal) {
        super(frame, title, modal);
        this.frame = frame;
    }

    /* Set */
    /**
     * Set text of CustomDialog.
     * 
     * @param msg Text
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Set which buttons will CustomDialog have.
     * 
     * @param buttons Use predefined constants BUTTON_OK, BUTTON_CANCEL or
     *                BUTTON_OK_CANCEL
     */
    public void setButtons(int buttons) {
        this.buttons = buttons;
    }

    /**
     * Set width of single button. Default value is BUTTON_FULL_WIDTH.
     * 
     * @param bw Use predefined constants BUTTON_FULL_WIDTH or BUTTON_HALF_WIDTH.
     */
    public void setButtonWidth(int bw) {
        button_width = bw;
    }

    /**
     * Set custom ActionListener for buttons. When you need close CustomDialog from
     * ActionListener, use closeDialog().
     * 
     * @param button Use predefined constants BUTTON_OK or BUTTON_CANCEL
     * @param AL     ActionListener for button.
     */
    public void addActionListenerFor(int button, ActionListener AL) {
        if (button == BUTTON_OK) {
            but_ok_listener = AL;
        } else if (button == BUTTON_CANCEL) {
            but_cancel_listener = AL;
        }
    }

    // setTitle(),setSize(),setModal()
    /* Set ends */
    /**
     * Display CustomDialog. Before calling this method you should have to call
     * methods: setTitle(), setSize() and setModal() from parent class JDialog. If
     * you used second constructor you need to use only setSize().
     */
    public void display() {
        w = getWidth();
        h = getHeight();
        pack();
        setSize(w, h);
        setResizable(false);
        setLocationRelativeTo(frame);
        Container cp = getContentPane();
        cp.setLayout(null);
        cp.setBackground(background);
        JTextPane msgLabel = new JTextPane();
        msgLabel.setText(msg);
        msgLabel.setEnabled(false);
        msgLabel.setFont(new Font("Ubuntu Light", Font.PLAIN, 18));
        msgLabel.setBounds(0, 2, w, h - 10 - 80);
        StyledDocument doc = msgLabel.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        msgLabel.setDisabledTextColor(foreground);
        msgLabel.setForeground(foreground);
        msgLabel.setBackground(background);
        cp.add(msgLabel);
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 2));
        bottom.setBackground(background);
        // -6 = border;
        bottom.setBounds(0, getHeight() - 80, getWidth() - 6, 51);

        CustomButton but_ok = new CustomButton("OK");
        if (but_ok_listener == null) {
            but_ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
            });
        } else {
            but_ok.addActionListener(but_ok_listener);
        }
        CustomButton but_cancel = new CustomButton("Cancel");
        if (but_cancel_listener == null) {
            but_cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
            });
        } else {
            but_cancel.addActionListener(but_cancel_listener);
        }
        if (buttons == BUTTON_OK) {
            bottom.setLayout(null);
            if (button_width == BUTTON_HALF_WIDTH) {
                but_ok.setBounds((getWidth() / 4), 0, (getWidth() / 2), 51);
            } else {
                but_ok.setBounds(0, 0, (getWidth() - 5), 51);
            }
            bottom.add(but_ok);
        } else if (buttons == BUTTON_CANCEL) {
            bottom.setLayout(null);
            if (button_width == BUTTON_HALF_WIDTH) {
                but_cancel.setBounds((getWidth() / 4), 0, (getWidth() / 2), 51);
            } else {
                but_cancel.setBounds(0, 0, (getWidth() - 5), 51);
            }
            bottom.add(but_cancel);
        } else {// BUTTON_OK_CANCEL
            bottom.add(but_ok);
            bottom.add(but_cancel);
        }

        cp.add(bottom);
        setVisible(true);
    }

    /**
     * Could be called for closing CustomDialog window from custon button listeners
     */
    public void closeDialog() {
        setVisible(false);
        dispose();
    }
}
