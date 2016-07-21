package jp.ait;

import java.awt.*;
import javax.swing.*;


class Utility{
	static ClassLoader mClassLoader;
	
	static void setClassLoader(ClassLoader cl) {
		mClassLoader = cl;
	}
	
	static ImageIcon ImageIcon(Container panel, String path) {
//System.out.println("panel.getClass()="+panel.getClass());
//System.out.println("panel.getClass().getClassLoader()="+panel.getClass().getClassLoader());
//System.out.println("path="+path+"  panel.getClass().getClassLoader().getResource(path)="+panel.getClass().getClassLoader().getResource(path));

		return new javax.swing.ImageIcon(panel.getClass().getClassLoader().getResource(path));
		
	}

}
