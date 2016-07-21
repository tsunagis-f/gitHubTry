package jp.ait;

import java.awt.*;
import javax.swing.*;


public class ImagePanel  extends JPanel {
//	  ImageIcon icon;
	  String mPath;
	  
	  ImagePanel(String path) {
		  mPath = path;
		    	   
	   }
	   public void paintComponent(Graphics g) {
		   super.paintComponent(g); 

		   ImageIcon icon = Utility.ImageIcon(this, mPath);
		   g.drawImage(icon.getImage(), 0, 0, this);
		   icon = null;
		   		   
	   }
}
