/**
    * @name        CustomFont.java
    * @version     v0.1 (19-11-2018)[dd-mm-yyyy]
    * @link        https://alexkratky.cz          Author website
    * @author      Alex Kratky <info@alexkratky.cz>
    * @copyright   Copyright (c) 2018 Alex Kratky
    * @license     http://opensource.org/licenses/mit-license.php  MIT License
    * @description Register a custom font to GrahphicsEnvironment
*/

package CustomComponents;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import CustomComponents.CustomDialog;
//import java.util.Arrays;

public class CustomFont {

    /**
     * Method will register a font to GraphicsEnvironment
     * @param FontName Name of font located at ./fonts/. Do not include extension.
     * @return void
     */
    public void registerFont(String FontName) { // throws IOException, FontFormatException {
        try {
            File F = new File("fonts\\" + FontName + ".ttf");
            System.out.println(F.getAbsolutePath());
            if (!F.exists()) {
                System.out.println(FontName + " doesnt exists");
                CustomDialog CD = new CustomDialog(null, "Error", true);
                CD.setSize(300, 200);
                CD.setMsg("Missing font - fonts\\"+FontName+".ttf");
                CD.display();

            } else {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, F));
            }
            // System.out.println(Arrays.toString(ge.getAvailableFontFamilyNames()));
        } catch (IOException | FontFormatException e) {
            // Handle exception
            System.out.println(e);
            CustomDialog CD = new CustomDialog(null, "Error", true);
            CD.setSize(300, 200);
            CD.setMsg(e.toString());
            CD.display();
        }
    }
}
