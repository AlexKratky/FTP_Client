
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
import org.apache.commons.net.ftp.FTP;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import CustomComponents.CustomFileChooser;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.JPopupMenu;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import CustomComponents.CustomDialog;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import CustomComponents.CustomPromptDialog;

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
    private JMenuItem status;
    
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
        UIManager.put("Menu.font", new Font("Ubuntu Light", Font.PLAIN, 14));
        UIManager.put("MenuItem.font", new Font("Ubuntu Light", Font.PLAIN, 14));
        JMenuBar JMB = new JMenuBar();
        //JMB.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
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
        
        JMenuItem mainDir = new JMenuItem("Go to /");
        mainDir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list("/");
            }
        });
        JM1.add(mainDir);
        
        JMenuItem delete = new JMenuItem("Delete selected items");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        JM1.add(delete);
        
        JMenuItem reestablish = new JMenuItem("Reestablish connection");
        reestablish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                establishConnection();
            }
        });
        JM1.add(reestablish);
        
        JMenuItem download = new JMenuItem("Download files");
        download.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                download();
            }
        });
        JM1.add(download);

        JMenuItem rename = new JMenuItem("Rename files");
        rename.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rename();
            }
        });
        JM1.add(rename);
        
        JMenuItem details = new JMenuItem("View details");
        details.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                details();
            }
        });
        JM1.add(details);
        
        JMenuItem dir = new JMenuItem("Create directory");
        dir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createDirectory();
            }
        });
        JM1.add(dir);

        status = new JMenuItem(path);
        JMB.add(status);

        files = new JList(filesModel);
        files.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
        files.setBackground(background);
        files.setForeground(foreground);
        
        files.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    System.out.println("Index: " + index);
                    System.out.println("Value: " + files.getModel().getElementAt(index));
                    String p = (String) files.getModel().getElementAt(index);
                    list(p+"/", true);
                }
            }
        });
        
        /*JPopupMenu popup = new JPopupMenu();
        popup.add(new JMenuItem("Hello World"));
        files.setComponentPopupMenu(popup);
        */
        /*
        JPopupMenu popupMenu = new JPopupMenu();
          popupMenu.add(new JMenuItem("PopupItem 1"));
          popupMenu.add(new JMenuItem("PopupItem 2"));
          popupMenu.add(new JPopupMenu.Separator());
          popupMenu.add(new JMenuItem("PopupItem 3"));
      
          files.addMouseListener(new MouseAdapter() {
             public void mouseClicked(MouseEvent me) {
                JList list = (JList)me.getSource();
                int row = list.locationToIndex(me.getPoint());
                list.setSelectedIndex(row);
                if (SwingUtilities.isRightMouseButton(me)    // if right mouse button clicked
                      && !files.isSelectionEmpty()            // and list selection is not empty
                      && files.locationToIndex(me.getPoint()) // and clicked point is
                         == files.getSelectedIndex()) {       //   inside selected item bounds
                   popupMenu.show(files, me.getX(), me.getY());
                }
             }
          });
        */
        
        files.addKeyListener(new KeyAdapter() {
           public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    String p = (String) files.getSelectedValue();
                    list(p+"/", true);
                }
           }
        });
        
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
                list(path);
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
    
    public void list(String path, boolean combine) {
        if(path.equals("./")) {
            System.out.println("Path is equal to .");
            list(this.path);
        } else if(path.equals("../")) {
            
            System.out.println("Path is equal to ..");
            if(this.path.equals("/")) {
                return;
            }
            String p[] = this.path.split("/");
            /*for(String a:p) 
                System.out.println(a);*/
            String newPath = "";
            for(int i = 0; i < (p.length-1); i++) {
                newPath = newPath + p[i] + "/";
            }
            System.out.println("New path: " + newPath);
            list(newPath);
        } else
            list(this.path + path); 
    }
    
    public void list(String path) {
        //path = path.replaceAll("/+", "/");
        try {
            if(ftp.cwd(path)==550){
                 System.out.println("Directory Doesn't Exists");
                 return;
            }else if(ftp.cwd(path)==250){
                 System.out.println("Directory Exists");
            }else{
                 System.out.println("Unknown Status");
            }
            this.path = path;
            status.setText(path);
            System.out.println("Current path: " + path); 
        
            String f[] = ftp.listNames(path);
            Arrays.sort(f);
            f = Arrays.copyOfRange(f, 1, f.length);
            filesModel.removeAllElements();
            for (int i = 0; i < f.length; i++) {
                System.out.println(f[i]);
                filesModel.insertElementAt(f[i].replace(path, ""), i);
            }
        } catch(FTPConnectionClosedException|SocketException e) {
            CustomDialog CD = new CustomDialog(frame, "Error", true);
            CD.setSize(300, 200);
            CD.setMsg("Connection closed without indication. Trying to reestablish connection.");
            CD.display();
            establishConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void upload() {
        CustomFileChooser fc = new CustomFileChooser();
        fc.setMultiSelectionEnabled(true);
        int returnVal = fc.showOpenDialog(frame);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Thread uploadThread = new Thread(new Runnable() {
                @Override
                    public void run() {
                    File[] selectedFiles = fc.getSelectedFiles();
                    for(File file : selectedFiles) {
                        if (file.exists()) {
                            System.out.println("Soubor existuje");
                        }
                        try {
                            FileInputStream targetStream = new FileInputStream(file);
                            System.out.println("Opening: " + file.getName() + ".");
                            
                           
                            ftp.storeFile(path + file.getName(), targetStream);
                   
                                    
                        } catch(FTPConnectionClosedException|SocketException e) {
                            CustomDialog CD = new CustomDialog(frame, "Error", true);
                            CD.setSize(300, 200);
                            CD.setMsg("Connection closed without indication. Trying to reestablish connection.");
                            CD.display();
                            establishConnection();
                        
                        } catch (IOException err) {
                            System.out.println("File doesnt exists " + err);
                        }
                        
                    }
                    refresh();
                }
            });  
            uploadThread.start();
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
    
    public void refresh() {
        /*try {
            String f[] = ftp.listNames(path);
            filesModel.removeAllElements();
            for (int i = 0; i < f.length; i++) {
                System.out.println(f[i]);
                filesModel.insertElementAt(f[i], i);
            }
        } catch (Exception e) {
            System.out.println(e);
        }*/
        list(path);
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
        } catch(FTPConnectionClosedException|SocketException e) {
            CustomDialog CD = new CustomDialog(frame, "Error", true);
            CD.setSize(300, 200);
            CD.setMsg("Connection closed without indication. Trying to reestablish connection.");
            CD.display();
            establishConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void download() {
        try {
            File f = new File("./download/");
            if (!f.exists() && !f.isDirectory()) {
                boolean success = (new File("./download/")).mkdirs();
                if (!success) {
                    System.out.println("Failed to create folder download");
                    return;
                }
            }
            
            
            Thread uploadThread = new Thread(new Runnable() {
                @Override
                    public void run() {
                        try {
                            int[] selected = files.getSelectedIndices();      
                            Object[] selectedItems = files.getSelectedValues();
                            ftp.setFileType(FTP.BINARY_FILE_TYPE);
                            for (int i = 0; i < selected.length; i++) {
                               /* String remoteFile = path + selectedItems[i].toString();
                                System.out.println("Downloading file: " + remoteFile);
                                File downloadFile = new File("./download/" + selectedItems[i].toString());
                                System.out.println("File will be saved as " + downloadFile);
                                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
                                boolean success = ftp.retrieveFile(remoteFile, outputStream);
                                System.out.println(success ? "File was successfully downloaded" : "Error - File can't be downloaded");
                                outputStream.close();*/
                                
                                String remoteFile = path + selectedItems[i].toString();
                                System.out.println("Downloading file: " + remoteFile);
                                File downloadFile = new File("./download/" + selectedItems[i].toString());
                                System.out.println("File will be saved as " + downloadFile);
                                
                                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
                                InputStream inputStream = ftp.retrieveFileStream(remoteFile);
                                byte[] bytesArray = new byte[4096];
                                int bytesRead = -1;
                                while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                                    outputStream.write(bytesArray, 0, bytesRead);
                                }
                                boolean success = ftp.completePendingCommand();
                                System.out.println(success ? "File was successfully downloaded" : "Error - File can't be downloaded");

                                outputStream.close();
                                inputStream.close();
                            }
                        } catch(FTPConnectionClosedException|SocketException e) {
                            CustomDialog CD = new CustomDialog(frame, "Error", true);
                            CD.setSize(300, 200);
                            CD.setMsg("Connection closed without indication. Trying to reestablish connection.");
                            CD.display();
                            establishConnection();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                }
            });  
            uploadThread.start();
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void rename() {
        int[] selected = files.getSelectedIndices();      
        Object[] selectedItems = files.getSelectedValues();
        for (int i = 0; i < selected.length; i++) {

                System.out.println("Renaming: " + path + selectedItems[i].toString());
                String oldName = path + selectedItems[i].toString();
                String newName = path + selectedItems[i].toString();
                
                
                CustomPromptDialog CD = new CustomPromptDialog(frame); 
                CD.setTitle("Rename - " + oldName);
                CD.setModal(true); 
                CD.setSize(350, 200); 
                
                CD.addActionListenerFor(CustomDialog.BUTTON_OK, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Rename - " + CustomPromptDialog.inputName.getText()); 
                        try {
                            boolean success = ftp.rename(oldName, CustomPromptDialog.inputName.getText());
                            if (success) {
                                System.out.println(oldName + " was successfully renamed to: "
                                        + newName);
                            } else {
                                System.out.println("Failed to rename: " + oldName);
                                CustomDialog CD = new CustomDialog(frame, "Error", true);
                                CD.setSize(300, 200);
                                CD.setMsg("Failed to rename: " + oldName);
                                CD.display();
                            }
                            refresh();
                         } catch(FTPConnectionClosedException|SocketException ex) {
                            CustomDialog CD = new CustomDialog(frame, "Error", true);
                            CD.setSize(300, 200);
                            CD.setMsg("Connection closed without indication. Trying to reestablish connection.");
                            CD.display();
                            establishConnection();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } 
                        CD.closeDialog();
                    } 
                });
                CD.display(newName, true);
                
               
        }
    }
    
    public void details() {
        int[] selected = files.getSelectedIndices();      
        Object[] selectedItems = files.getSelectedValues();
        for (int i = 0; i < selected.length; i++) {
            try {
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                FTPFile ftpFile = ftp.mlistFile(path + selectedItems[i].toString());
                System.out.println("Viewing details of " + path + selectedItems[i].toString());
                
                String filePath = path + selectedItems[i].toString();
                ftp.sendCommand("SIZE", filePath);
                String size_reply = ftp.getReplyString();
                System.out.println("Reply for SIZE command: " + size_reply);
                if(size_reply.split(" ")[0].equals("550")) {
                    CustomDialog CD = new CustomDialog(frame); 
                    CD.setTitle("Details - " + path + selectedItems[i].toString());
                    CD.setModal(true); 
                    CD.setSize(350, 200); 
                    CD.setMsg("Name: " + selectedItems[i].toString() + "\r\n" + "Type: Directory");
                    CD.addActionListenerFor(CustomDialog.BUTTON_OK, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Details closed"); 
                            CD.closeDialog();
                        } 
                    });
                    CD.display();
                    return;
                }
                String time = ftp.getModificationTime(filePath);
                System.out.println("Server Reply: " + time);
                //dd.MM.yyyy HH:mm:ss 
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date modificationTime = null;
                try {
                    //String timePart = time.split(" ")[1];
                    modificationTime = dateFormat.parse(time);
                    System.out.println("File modification time: " + modificationTime);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                
                CustomDialog CD = new CustomDialog(frame); 
                CD.setTitle("Details - " + path + selectedItems[i].toString());
                CD.setModal(true); 
                CD.setSize(350, 200); 
                CD.setMsg("Name: " + selectedItems[i].toString() + "\r\n" + "Size: " + size_reply.split(" ")[1].trim() + "b\r\n"  + "Time: " + modificationTime);
                CD.addActionListenerFor(CustomDialog.BUTTON_OK, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Details closed"); 
                        CD.closeDialog();
                    } 
                });
                CD.display();
                
                
              
                if (ftpFile != null) {
                    String name = ftpFile.getName();
                    long size = ftpFile.getSize();
                    String timestamp = ftpFile.getTimestamp().getTime().toString();
                    String type = ftpFile.isDirectory() ? "Directory" : "File";
     
                    System.out.println("Name: " + name);
                    System.out.println("Size: " + size);
                    System.out.println("Type: " + type);
                    System.out.println("Timestamp: " + timestamp);
                    
                    /*CD = new CustomDialog(frame); 
                    CD.setTitle("Details - " + path + selectedItems[i].toString());
                    CD.setModal(true); 
                    CD.setSize(300, 200); 
                    CD.setMsg("Name: " + name + "\r\n" + "Size: " + size);
                    CD.addActionListenerFor(CustomDialog.BUTTON_OK, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Details closed"); 
                            CD.closeDialog();
                        } 
                    });
                    CD.display();*/
                } else {
                    System.out.println("The specified file/directory may not exist!");
                }
            } catch(FTPConnectionClosedException|SocketException e) {
                CustomDialog CD = new CustomDialog(frame, "Error", true);
                CD.setSize(300, 200);
                CD.setMsg("Connection closed without indication. Trying to reestablish connection.");
                CD.display();
                establishConnection();
            } catch (IOException e) {
                e.printStackTrace();
            } 
          
        }
    }
    
    public void createDirectory() {
        
        
        CustomPromptDialog CD = new CustomPromptDialog(frame); 
        CD.setTitle("Create new directory");
        CD.setModal(true); 
        CD.setSize(350, 200); 
        
        CD.addActionListenerFor(CustomDialog.BUTTON_OK, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Create - " + path+CustomPromptDialog.inputName.getText()); 
                try {
                    boolean success = ftp.makeDirectory(path+CustomPromptDialog.inputName.getText());
                    showServerReply(ftp);
                    if (success) {
                        System.out.println("Successfully created directory: " + path+CustomPromptDialog.inputName.getText());
                    } else {
                        System.out.println("Failed to create directory. See server's reply.");
                    }
                    refresh();
                 } catch(FTPConnectionClosedException|SocketException ex) {
                    CustomDialog CD = new CustomDialog(frame, "Error", true);
                    CD.setSize(300, 200);
                    CD.setMsg("Connection closed without indication. Trying to reestablish connection.");
                    CD.display();
                    establishConnection();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } 
                CD.closeDialog();
            } 
        });
        CD.display("", false);
    }
    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
}