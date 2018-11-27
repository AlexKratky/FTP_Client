
/**
 * @name MainClass.java
 * @version v0.1 (23-11-2018)[dd-mm-yyyy]
 * @link https://alexkratky.cz Author website
 * @author Alex Kratky <info@alexkratky.cz>
 * @copyright Copyright (c) 2018 Alex Kratky
 * @license http://opensource.org/licenses/mit-license.php MIT License
 * @description Main class of FTP_Client.
 */

import FTP_Dialog.FTP_Dialog;
import FTP_Dialog.FTPDataListener;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import CustomComponents.CustomFont;
import CustomComponents.SettingsDialog;

public class MainClass {

    /**
     * Display init window.
     * 
     * @return void
     */
    public static void main(String args[]) {
        CustomFont CF = new CustomFont();
        // CF.registerFont("UbuntuRegular");
        CF.registerFont("UbuntuLight");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FTP_Init ftpInit = new FTP_Init();
                ftpInit.displayInitWindow();
            }
        });

    }

    /**
     * Display FTP_Dialog.
     * 
     * @return void
     */
    public static void displayDialog() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FTP_Client ftpClient = new FTP_Client();
                FTP_Dialog ftpDialog = new FTP_Dialog(new FTPDataListener() {
                    @Override
                    public void dataPerformed(String server, String username, String password, int port) {
                        ftpClient.connect(server, username, password, port);
                    }
                });
            }
        });
    }

    /**
     * Display settings.
     * 
     * @param parent Parent window of Settings window.
     * @return void
     */
    public static void displaySettings(JFrame parent) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // could add Button(Settings) transparent background
                new SettingsDialog(parent);
            }
        });
    }
}
