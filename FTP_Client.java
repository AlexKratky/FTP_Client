
/**
 * @name FTP_Client.java
 * @version v0.1 (23-11-2018)[dd-mm-yyyy]
 * @link https://alexkratky.cz Author website
 * @author Alex Kratky <info@alexkratky.cz>
 * @copyright Copyright (c) 2018 Alex Kratky
 * @license http://opensource.org/licenses/mit-license.php MIT License
 * @description FTP Data Listener interface.
 */

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
import javax.swing.JFileChooser;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

public class FTP_Client {
    /**
     * Server address of FTP Server.
     */
    private String server;

    /**
     * Username.
     */
    private String user;

    /**
     * Password.
     */
    private String pass;

    /**
     * Port of FTP Server.
     */
    private int port;

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

    private DefaultListModel filesModel = new DefaultListModel();
    private JList files;
    private String path = "/";
    private FTPClient ftp = new FTPClient();
    private FTPClientConfig config = new FTPClientConfig();

    public FTP_Client() {

    }

    /**
     * Set creditials and display GUI.
     * 
     * @param srv  Adress of server.
     * @param user FTP Username.
     * @param pass FTP Password.
     * @param port Port of server.
     * @return void
     */
    public void connect(String srv, String user, String pass, int port) {
        //System.out.println(srv + " / " + user + " / " + pass + " / " + port);
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

    /**
     * Display GUI, called from connect().
     * 
     * @return void
     */
    public void gui() {
        frame = new JFrame("FTP - " + server);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        try {
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/icon.png")));
        } catch (Exception e) {
            System.out.println(e);
        }
        cp = frame.getContentPane();
        cp.setBackground(background);
        JMenuBar JMB = new JMenuBar();
        // JMB.setBackground(background);
        // JMB.setForeground(foreground);
        JMenu JM1 = new JMenu("File");
        JMB.add(JM1);
        JMenuItem upload = new JMenuItem("Upload");
        upload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upload();
            }
        });
        JM1.add(upload);
        
        JMenuItem refresh = new JMenuItem("Refresh");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        JM1.add(refresh);
        
        JMenuItem delete = new JMenuItem("Delete selected items");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        JM1.add(delete);

        JMenuItem status = new JMenuItem("...");
        JMB.add(status);

        files = new JList(filesModel);
        files.setBackground(background);
        files.setForeground(foreground);

        frame.setJMenuBar(JMB);
        cp.add(files);
        frame.setVisible(true);

        establishConnection();
    }

    /**
     * Establish connection to FTP server and display files and folders.
     * 
     * @return void
     */
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

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            } else {
                System.out.println("Connected");
                String f[] = ftp.listNames("/");
                filesModel.removeAllElements();
                for (int i = 0; i < f.length; i++) {
                    System.out.println(f[i]);
                    filesModel.insertElementAt(f[i], i);
                }
            }

            //ftp.logout();
        } catch (IOException e) {
            error = true;
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                /*try {
                    //ftp.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }*/
            }
            // System.exit(error ? 1 : 0);
        }
    }
    
    public void upload() {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            if (file.exists()) {
                System.out.println("Soubor existuje");
            }
            
            try {
                FileInputStream targetStream = new FileInputStream(file);
                System.out.println("Opening: " + file.getName() + ".");
                
                Thread uploadThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ftp.storeFile(path + file.getName(), targetStream);
                            refresh();
                        } catch (IOException IOE) {
                            System.out.println(IOE);
                        }
                    }
                });  
                uploadThread.start();
            } catch (IOException err) {
                System.out.println("File doesnt exists " + err);
            }
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
    
    public void refresh() {
        try {
            String f[] = ftp.listNames(path);
            filesModel.removeAllElements();
            for (int i = 0; i < f.length; i++) {
                System.out.println(f[i]);
                filesModel.insertElementAt(f[i], i);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void delete() {
        try {
            /*String f[] = ftp.listNames(path);
            filesModel.removeAllElements();
            for (int i = 0; i < f.length; i++) {
                System.out.println(f[i]);
                filesModel.insertElementAt(f[i], i);
            }*/
           
            int[] selected = files.getSelectedIndices();      
            Object[] selectedItems = files.getSelectedValues();
            for (int i = 0; i < selected.length; i++) {
                //filesModel.remove(selected);
                ftp.deleteFile(selectedItems[i].toString());
            }
            refresh();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}