import FTP_Dialog.FTP_Dialog;
import FTP_Dialog.FTPDataListener;
import javax.swing.SwingUtilities;
import CustomComponents.CustomFont;
public class MainClass
{
    public static void main(String args[]) {
        CustomFont CF = new CustomFont();
        //CF.registerFont("UbuntuRegular");
        CF.registerFont("UbuntuLight");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FTP_Init ftpInit = new FTP_Init();
                ftpInit.displayInitWindow();
            }
        });

    }
    
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
    
    public static void displaySettings() {}
}
