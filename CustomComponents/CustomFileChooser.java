package CustomComponents;

import javax.swing.JFileChooser;
import java.awt.Container;
import sun.swing.FilePane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Component;

public class CustomFileChooser extends JFileChooser{
   public void updateUI(){
      LookAndFeel old = UIManager.getLookAndFeel();
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } 
      catch (Throwable ex) {
         old = null;
      } 

      super.updateUI();

      if(old != null){
         FilePane filePane = findFilePane(this);
         filePane.setViewType(FilePane.VIEWTYPE_DETAILS);
         //filePane.setViewType(FilePane.VIEWTYPE_LIST);

         Color background = UIManager.getColor("Label.background");
         setBackground(background);
         setOpaque(true);

         try {
            UIManager.setLookAndFeel(old);
         } 
         catch (UnsupportedLookAndFeelException ignored) {} // shouldn't get here
      }
   }



   private static FilePane findFilePane(Container parent){
      for(Component comp: parent.getComponents()){
         if(FilePane.class.isInstance(comp)){
            return (FilePane)comp;
         }
         if(comp instanceof Container){
            Container cont = (Container)comp;
            if(cont.getComponentCount() > 0){
               FilePane found = findFilePane(cont);
               if (found != null) {
                  return found;
               }
            }
         }
      }

      return null;
   }
}
