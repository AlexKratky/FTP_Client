
/**
 * @name FTP_Init.java
 * @version v0.1 (19-11-2018)[dd-mm-yyyy]
 * @link https://alexkratky.cz Author website
 * @author Alex Kratky <info@alexkratky.cz>
 * @copyright Copyright (c) 2018 Alex Kratky
 * @license http://opensource.org/licenses/mit-license.php MIT License
 * @description Main window.
 */
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import CustomComponents.CustomButton;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import CustomComponents.CustomDialog;

public class FTP_Init {
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

    public FTP_Init() {

    }

    /**
     * Display init window.
     * 
     * @return void
     */
    public void displayInitWindow() {
        frame = new JFrame("FTP Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/icon.png")));
        cp = frame.getContentPane();
        cp.setBackground(background);
        cp.setLayout(null);

        JLabel title = new JLabel("FTP Client", SwingConstants.CENTER);
        title.setBounds(0, 15, frame.getWidth(), 34);
        title.setFont(new Font("Ubuntu Light", Font.PLAIN, 30));
        title.setForeground(foreground);

        JLabel subtitle = new JLabel("Welcome to FTP Client.", SwingConstants.CENTER);
        subtitle.setBounds(0, 60, frame.getWidth(), 24);
        subtitle.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
        subtitle.setForeground(Color.WHITE);

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 2));
        bottom.setBounds(0, frame.getHeight() - 80, frame.getWidth() - 6, 51);

        CustomButton settings = new CustomButton("Settings");
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainClass.displaySettings(frame);
            }
        });
        CustomButton connect = new CustomButton("Connect");
        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                MainClass.displayDialog();
            }
        });

        bottom.add(settings);
        bottom.add(connect);
        cp.add(title);
        cp.add(subtitle);
        cp.add(bottom);
        frame.setVisible(true);
        /*
         * CustomDialog CD = new CustomDialog(frame); CD.setTitle("testik");
         * CD.setModal(true); CD.setSize(300, 200); CD.setMsg("xd");
         * CD.addActionListenerFor(CustomDialog.BUTTON_OK, new ActionListener() { public
         * void actionPerformed(ActionEvent e) {
         * System.out.println("BUTTON_OK clicked"); CD.closeDialog();// } });
         * CD.display();
         */
    }

}
