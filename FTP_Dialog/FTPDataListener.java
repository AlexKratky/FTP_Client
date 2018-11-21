package FTP_Dialog;

/**
 * @name ftpDataListener.java
 * @version v0.1 (19-11-2018)[dd-mm-yyyy]
 * @link https://alexkratky.cz Author website
 * @author Alex Kratky <info@alexkratky.cz>
 * @copyright Copyright (c) 2018 Alex Kratky
 * @license http://opensource.org/licenses/mit-license.php MIT License
 * @description FTP Data Listener interface.
 */

public interface FTPDataListener {
        /**
         * Method called when FTP_Dialog return data. Data should be valid, server and
         * username will never be empty, password can be empty or filled and port is
         * only numeric higher than 0.
         * 
         * @param server   Server address of FTP.
         * @param username Username to FTP.
         * @param password Password of user.
         * @param port     Port of FTP server.
         * @return void
         */
        void dataPerformed(String server, String username, String password, int port);
}