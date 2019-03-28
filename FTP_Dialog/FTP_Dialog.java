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
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import CustomComponents.CustomPassword;
import java.nio.charset.StandardCharsets;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

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
    
    private boolean rememberPassword = false;

    /**
     * Container reference.
     */
    private Container cp = null;

    private byte[] encryptionKey = "qd602MgGnjtSbspf".getBytes(StandardCharsets.UTF_8);
    
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
        
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
    
            rememberPassword = (prop.getProperty("remember").equals("true") ? true : false);
        } catch (IOException ex) {
            //ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        cp = frame.getContentPane();
        cp.setBackground(background);

        SpringLayout layout = new SpringLayout();
        cp.setLayout(layout);

        JPanel p = new JPanel(new SpringLayout());
        p.setBackground(background);
        
        prop = new Properties();
        input = null;
        String configServer = "";
        String configUsername = "";
        String configPassword = "";
        String configPort = "";
        try {
            input = new FileInputStream("credentials.properties");
            prop.load(input);
    
            configServer = prop.getProperty("server");
            configUsername = prop.getProperty("username");
            configPort = prop.getProperty("port");
            configPassword = prop.getProperty("password");
            if(configPassword != null && configPassword.length() > 0) {
                configPassword = CustomPassword.decrypt(configPassword);
            }
        } catch (IOException ex) {
            //ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        JLabel labelServer = new JLabel("Server ", JLabel.TRAILING);
        labelServer.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelServer.setForeground(foreground);
        p.add(labelServer);
        CustomInput inputServer = new CustomInput();
        labelServer.setLabelFor(inputServer);
        inputServer.setText(configServer);
        p.add(inputServer);

        JLabel labelUser = new JLabel("User ", JLabel.TRAILING);
        labelUser.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelUser.setForeground(foreground);
        p.add(labelUser);
        CustomInput inputUser = new CustomInput();
        labelUser.setLabelFor(inputUser);
        inputUser.setText(configUsername);
        p.add(inputUser);

        JLabel labelPass = new JLabel("Password ", JLabel.TRAILING);
        labelPass.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelPass.setForeground(foreground);
        p.add(labelPass);
        CustomInputPassword inputPass = new CustomInputPassword();
        labelPass.setLabelFor(inputPass);
        if(rememberPassword)
            inputPass.setText(configPassword);
        p.add(inputPass);

        JLabel labelPort = new JLabel("Port ", JLabel.TRAILING);
        labelPort.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        labelPort.setForeground(foreground);
        p.add(labelPort);
        CustomInput inputPort = new CustomInput("21");
        labelPort.setLabelFor(inputPort);
        inputPort.setText(configPort);
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
                    
                    Properties prop = new Properties();
                    OutputStream output = null;
                    try {
                        output = new FileOutputStream("credentials.properties");
                       
                        prop.setProperty("server", server);
                        prop.setProperty("username", user);
                        prop.setProperty("port", inputPort.getText().trim());
                        if(rememberPassword)
                            prop.setProperty("password", CustomPassword.encrypt(pass));
                        prop.store(output, null);
                    } catch (IOException io) {
                        io.printStackTrace();
                    } finally {
                        if (output != null) {
                            try {
                                output.close();
                            } catch (IOException err) {
                                err.printStackTrace();
                            }
                        }
                    }
                    
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