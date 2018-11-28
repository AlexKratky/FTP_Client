package CustomComponents;
/**
 * @name SettingsDialog.java
 * @version v0.1 (27-11-2018)[dd-mm-yyyy]
 * @link https://alexkratky.cz Author website
 * @author Alex Kratky <info@alexkratky.cz>
 * @copyright Copyright (c) 2018 Alex Kratky
 * @license http://opensource.org/licenses/mit-license.php MIT License
 * @description Custom settings dialog.
 */
import CustomComponents.CustomDialog;
import CustomComponents.CustomCheckBoxIcon;
import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import CustomComponents.CustomButton;
import Classes.SpringUtilities;
import CustomComponents.CustomInput;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;

public class SettingsDialog extends CustomDialog {

    /**
     * Create instance of SettingsDialog by specifing parent window.
     * @param frame Parent window.
     */
    public SettingsDialog(JFrame frame) {
        super(frame, "Settings", true);
        display();
    }

    @Override
    /**
     * Display settings window, called from constructor.
     */
    public void display() {
        w = 408;
        h = 180;
        pack();
        setSize(w, h);
        setResizable(false);
        setLocationRelativeTo(frame);
        Container cp = getContentPane();
        cp.setLayout(null);
        cp.setBackground(background);
        cp.setLayout(new SpringLayout());
        JPanel p = new JPanel(new SpringLayout());
        p.setBackground(background);

        JLabel labelName = new JLabel("Your name ", JLabel.TRAILING);
        labelName.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelName.setForeground(foreground);
        p.add(labelName);
        CustomInput inputName = new CustomInput();
        labelName.setLabelFor(inputName);
        p.add(inputName);

        JLabel labelPassword = new JLabel("Password ", JLabel.TRAILING);
        labelPassword.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelPassword.setForeground(foreground);
        p.add(labelPassword);
        
        /*
        JCheckBox checkPassword = new JCheckBox("Save password to file");
        checkPassword.setForeground(foreground);
        checkPassword.setBackground(background);
        checkPassword.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelPassword.setLabelFor(checkPassword);
        p.add(checkPassword);
        */
        CustomCheckBoxIcon checked = new CustomCheckBoxIcon();
        CustomCheckBoxIcon unchecked = new CustomCheckBoxIcon();
        JCheckBox checkPassword = new JCheckBox("", unchecked);
        checkPassword.setOpaque(false);
        checkPassword.setFocusPainted(false);
//        System.out.println(checkPassword.getX() + " "+ checkPassword.getY());
        checkPassword.setSelectedIcon(checked);
        labelPassword.setLabelFor(checkPassword);
        //checkPassword.setLocation(x.getX(), x.getY());
        checkPassword.setVisible(true);
        //top-left-bottom-right
        //checkPassword.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, Color.YELLOW));
        //checkPassword.setBorderPainted(true);
        p.add(checkPassword);
        
        p.add(new JLabel("", JLabel.TRAILING));
        CustomButton but_ok = new CustomButton("Save");
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
        p.add(but_ok);
        SpringUtilities.makeCompactGrid(p, 3, 2, // rows, cols
                6, 6, // initX, initY
                6, 6);
        cp.add(p);
        setVisible(true);
    }
}
