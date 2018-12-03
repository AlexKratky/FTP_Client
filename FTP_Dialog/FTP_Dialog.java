package FTP_Dialog;

/**
    * @name        FTP_Dialog.java
    * @version     v0.1 (19-11-2018)[dd-mm-yyyy]
    * @link        https://alexkratky.cz          Author website
    * @author      Alex Kratky <info@alexkratky.cz>
    * @copyright   Copyright (c) 2018 Alex Kratky
    * @license     http://opensource.org/licenses/mit-license.php  MIT License
    * @description FTP Dialog
*/

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import CustomComponents.CustomInput;
import CustomComponents.CustomInputPassword;
import CustomComponents.CustomButton;
import FTP_Dialog.FTPDataListener;
import Classes.SpringUtilities;
import java.awt.Toolkit;
import CustomComponents.CustomDialog;

public class FTP_Dialog {
    /**
     * Color of foreground.
     */
    private Color foreground = new Color(195, 7, 63);

    /**
     * Color of background.
     */
    private Color background = new Color(26, 26, 29);

    /**
     * JFrame reference.
     */
    private JFrame frame = null;

    /**
     * Container reference.
     */
    private Container cp = null;

    /**
     * Create and display FTP_Dialog.
     * 
     * @param ftpDL ftpDataListener to retrive data.
     */
    public FTP_Dialog(FTPDataListener ftpDL) {
        // createDialog(AE);
        createDialog(ftpDL);
    }

    // ActionListener AE
    /**
     * Method to create dialog, called from constructor.
     * 
     * @param ftpDL ftpDataListener to retrive data. Inluded from constructor
     * @return void
     */
    public void createDialog(FTPDataListener ftpDL) {
        frame = new JFrame("FTP Client Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 310);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        try {
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon.png")));
        } catch (Exception e) {
            CustomDialog CD = new CustomDialog(frame, "Error", true);
            CD.setSize(300, 200);
            CD.setMsg("Failed to load icon - " + e);
            CD.display();
        }
        cp = frame.getContentPane();
        cp.setBackground(background);

        SpringLayout layout = new SpringLayout();
        cp.setLayout(layout);

        JPanel p = new JPanel(new SpringLayout());
        p.setBackground(background);

        JLabel labelServer = new JLabel("Server ", JLabel.TRAILING);
        labelServer.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelServer.setForeground(foreground);
        p.add(labelServer);
        CustomInput inputServer = new CustomInput();
        labelServer.setLabelFor(inputServer);
        p.add(inputServer);

        JLabel labelUser = new JLabel("User ", JLabel.TRAILING);
        labelUser.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelUser.setForeground(foreground);
        p.add(labelUser);
        CustomInput inputUser = new CustomInput();
        labelUser.setLabelFor(inputUser);
        p.add(inputUser);

        JLabel labelPass = new JLabel("Password ", JLabel.TRAILING);
        labelPass.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelPass.setForeground(foreground);
        p.add(labelPass);
        CustomInputPassword inputPass = new CustomInputPassword();
        labelPass.setLabelFor(inputPass);
        p.add(inputPass);

        JLabel labelPort = new JLabel("Port ", JLabel.TRAILING);
        labelPort.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelPort.setForeground(foreground);
        p.add(labelPort);
        CustomInput inputPort = new CustomInput("21");
        labelPort.setLabelFor(inputPort);
        p.add(inputPort);
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String server = inputServer.getText().trim();
                String user = inputUser.getText().trim();
                String pass = String.valueOf(inputPass.getPassword());
                int port = 0;
                try {
                    port = Integer.valueOf(inputPort.getText().trim());
                } catch (NumberFormatException nfe) {
                    // not valid port
                }
                if (server.length() > 0 && user.length() > 0 && port > 0) {
                    ftpDL.dataPerformed(server, user, pass, port);
                    frame.setVisible(false);
                    frame.dispose();
                } else {
                    CustomDialog CD = new CustomDialog(frame, "Error", true);
                    CD.setSize(300, 200);
                    CD.setMsg("Invalid data.");
                    CD.display();
                }
            }
        };
        inputServer.addActionListener(action);
        inputUser.addActionListener(action);
        inputPass.addActionListener(action);
        inputPort.addActionListener(action);

        JLabel l = new JLabel("", JLabel.TRAILING);
        p.add(l);
        CustomButton CB = new CustomButton("Connect");
        CB.addActionListener(action);
        p.add(CB);

        SpringUtilities.makeCompactGrid(p, 5, 2, // rows, cols
                6, 6, // initX, initY
                6, 6); // xPad, yPad
        cp.add(p);
        frame.setVisible(true);
    }
}