import FTP_Dialog.FTP_Dialog;
import FTP_Dialog.FTPDataListener;

public class ftp_dialog_test
{
    public static void main(String args[]) {
        FTP_Dialog ftpDialog = new FTP_Dialog(new FTPDataListener() {
            @Override
            public void dataPerformed(String server, String username, String password, int port) {
                System.out.println(server + username + password + port);
            }
        });
    }
    
    public static void connected(String server, String username, String password, int port) {
        System.out.println(server);
        System.out.println(username);
        System.out.println(password);
        System.out.println(port);
    }
    
}
