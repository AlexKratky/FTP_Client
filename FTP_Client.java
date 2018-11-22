import javax.swing.SwingUtilities;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;
import java.io.IOException;

public class FTP_Client {
    private String server;
    private String user;
    private String pass;
    private int port;
    private Color foreground = new Color(195, 7, 63);
    private Color background = new Color(26, 26, 29);
    private JFrame frame = null;
    private Container cp = null;
    private DefaultListModel filesModel = new DefaultListModel();
    private JList files;
    private FTPClient ftp = new FTPClient();
    private FTPClientConfig config = new FTPClientConfig();
    
    public FTP_Client () {
    
    }
    
    public void connect(String srv, String user, String pass, int port) {
        System.out.println(srv + " / " + user + " / " + pass + " / " + port);
        this.server = srv;
        this.user = user;
        this.pass = pass;
        this.port = port;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui();
            }
        });
    }
    
    public void gui() {
        frame = new JFrame("FTP - " + server);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        try {
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/icon.png")));
        } catch(Exception e) {System.out.println(e);}
        cp = frame.getContentPane();
        cp.setBackground(background);
        JMenuBar JMB = new JMenuBar();
        //JMB.setBackground(background);
        //JMB.setForeground(foreground);
        JMenu JM1 = new JMenu("File");
        JMB.add(JM1);
        JMenuItem status = new JMenuItem("Connecting ...");
        JMB.add(status);
        
        
        files = new JList(filesModel);
        files.setBackground(background);
        files.setForeground(foreground);
        
        frame.setJMenuBar(JMB);
        cp.add(files);
        frame.setVisible(true);
        
        establishConnection();
    }
    
    public void establishConnection() {
        ftp.configure(config);
        boolean error = false;
        try {
            int reply;
            ftp.connect(server, port);
            System.out.println("Connected to " + server + ".");
          
            System.out.print(ftp.getReplyString());
            boolean success = ftp.login(user, pass);
            if (!success) {
                System.out.println("Could not login to the server");
                return;
            } else {
                System.out.println("LOGGED IN SERVER");
            }
            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftp.getReplyCode();
    
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            } else {
               System.out.println("Connected"); 
               String f[] = ftp.listNames("/");
               filesModel.removeAllElements();
               for(int i = 0; i < f.length; i++) {
                   System.out.println(f[i]);
                   filesModel.insertElementAt(f[i], i);
               }
              }
          
              ftp.logout();
        } catch(IOException e) {
          error = true;
          e.printStackTrace();
        } finally {
          if(ftp.isConnected()) {
            try {
              ftp.disconnect();
            } catch(IOException ioe) {
              // do nothing
            }
          }
          //System.exit(error ? 1 : 0);
        }
    }
}