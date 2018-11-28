package CustomComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;

class CustomCheckBoxIcon implements Icon {
    private Color foreground = new Color(195, 7, 63);
    private Color background = new Color(26, 26, 29);
    
    public void paintIcon(Component component, Graphics g, int x, int y) {
        AbstractButton abstractButton = (AbstractButton)component;
        ButtonModel buttonModel = abstractButton.getModel();
        g.setColor(background);
        g.drawRect(0, 0, 20,25);
        g.setColor(foreground);
        //Color color = buttonModel.isSelected() ?  Color.BLUE : Color.RED;
        //g.setColor(color);
        if(!buttonModel.isSelected()) {
            g.drawRect(1, 6, 20,20);
        } else {
            g.drawRect(1, 6, 20,20);
            g.fillRect(3, 8, 17,17);
        }
    
    }
    public int getIconWidth() {
        return 20;
    }
    public int getIconHeight() {
        return 25;
    }
}