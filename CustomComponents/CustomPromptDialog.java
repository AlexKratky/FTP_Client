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
import javax.swing.SpringLayout;
import java.awt.event.ActionEvent;
import CustomComponents.CustomInput;
import Classes.SpringUtilities;
/**
 * Write a description of class CustomPromptDialog here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CustomPromptDialog extends CustomDialog {
    public static CustomInput inputName = null;
   
    
   public CustomPromptDialog(JFrame frame) {
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
    public CustomPromptDialog(JFrame frame, String title, boolean modal) {
        super(frame, title, modal);
        this.frame = frame;
    }
    /**
     * if type  = true      > Rename dialog
     *          = false     > Create dialog
     */
    public void display(String t, boolean type) {
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
        
      
        
        
        JLabel labelName = new JLabel(type ? "New name " : "Enter name ", JLabel.TRAILING);
        labelName.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelName.setForeground(foreground);
        p.add(labelName);
        inputName = new CustomInput();
        inputName.setText(t);
        labelName.setLabelFor(inputName);
        p.add(inputName);

        /*CustomButton but_ok = new CustomButton("Save");
        but_ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                
                
                setVisible(false);
                dispose();
            }
        });
        p.add(but_ok);*/
        /*
         * JCheckBox checkPassword = new JCheckBox("Save password to file");
         * checkPassword.setForeground(foreground);
         * checkPassword.setBackground(background); checkPassword.setFont(new
         * Font("Ubuntu Light", Font.PLAIN, 20));
         * labelPassword.setLabelFor(checkPassword); p.add(checkPassword);
         */
 

        p.add(new JLabel("", JLabel.TRAILING));
        CustomButton but_ok = new CustomButton(type ? "Rename" : "Create");
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
        SpringUtilities.makeCompactGrid(p, 2, 2, // rows, cols
                6, 6, // initX, initY
                6, 6);
        cp.add(p);
        setVisible(true);
    }
}
