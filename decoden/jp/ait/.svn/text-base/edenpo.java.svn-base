package jp.ait;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.swing.*;

/**
 * E電報のメインウィンドウ
 * 
 * @author kagi
 *
 */
public class edenpo extends JFrame  implements ActionListener, ComponentListener, WindowListener {
	final static String VERSION = "E-Denpo Ver1.0";			
	
//	final static Color BACK_COLOR = new Color(167,230,167);			// green
//	final static Color BACK_COLOR2 = new Color(255, 255, 204);		// ivory
//	final static Color FRAME_COLOR = new Color(48, 192, 0);	
	final static Color BACK_COLOR = new Color(233,23,22);			// green
	final static Color BACK_COLOR2 = new Color(255, 226, 226);		// ivory
	final static Color FRAME_COLOR = new Color(255, 152, 149);	
	final static Color FRAME_COLOR2 = new Color(255, 186, 186);	
	final static Color FRAME_COLOR3 = new Color(255, 160, 161);
	final static StackEx Stack = new StackEx();
	
	// メインウィンドウの初期サイズ
	final static int FRAME_WIDTH = 1022;
//	final static int FRAME_HEIGHT = 740;
	final static int FRAME_HEIGHT = 980;
		
	static JFrame Main_Frame;
	static LayerPanel Layer_Panel;
	static GPanel Graphics_Panel = null;
	static GraphicsPanel Graphics_PanelM;
	static ToolPanel Tool_Panel;
	static TextPanel Text_Panel;
	static Font Menu_Font;
	static TopPanel Top_Panel;
	
	private JButton size_btn, end_btn;
//	private JButton test_btn;
	
	static DocSize DocDialog;
	
	static String user_id = null;
	static String telegram_type_id = null;
	
	static boolean Boot = false;

	static String SERVER = null;
	static String PostURL = null;
	static String TemplateURL = null;
	
	edenpo(String title) {
		super(title);
		
//JOptionPane.showMessageDialog(this, "edenpo start");
		Main_Frame = this;
		
		Utility.setClassLoader(getClass().getClassLoader());
		
		/**
		 * Propertiesファイルを読む
		 * 　(PostのURLを得ている)
		 */
		
		// 始めにリソースからサーバーのパスを得る
		URL propURL = this.getClass().getClassLoader().getResource("decoden.properties");	
		
		String res_path = propURL.getPath();
//JOptionPane.showMessageDialog(this, "res_path="+res_path);
		
		// /decode.jar!/decoden.properties という形になっているので　/decoden.jar!以下を削除
		int index = res_path.lastIndexOf("/decoden.jar");
		
		// でてきたパスに /decoden.properties を付ける
		String path;

		if (index == -1) {
			// ローカルのeclipseで起動する時用
			path = "http://www.e-denpo.net/decoden.properties";
		} else {
			path = res_path.substring(0, index) + "/decoden.properties";
		}

//		path = res_path.substring(0, index) + "/decoden.properties";
//JOptionPane.showMessageDialog(this, "path="+path);

//		String path;
//path = "http://www.e-denpo.net/decoden.properties";
		
        Properties prop = new Properties();
     	URLConnection con = null;
     	try {
			URL url = new URL(path);
			con = url.openConnection();
			String userPassword =  "usr" + ":" + "EW8inC45";

			 // Base64 encode the authentication string
			String encoding = new sun.misc.BASE64Encoder().encode (userPassword.getBytes());
			    
			con.setRequestProperty ("Authorization", "Basic " + encoding);

			// readHttpURL
			con.setAllowUserInteraction (true);
			con.connect();
			DataInputStream in = new DataInputStream(con.getInputStream());
       			
       		prop.loadFromXML(in);
       		in.close();

       		SERVER = prop.getProperty("SERVER");
       		PostURL = prop.getProperty("POST_URL");
       		TemplateURL = prop.getProperty("TEMPLATE_URL");
//System.out.println(SERVER);
//System.out.println(PostURL);

//JOptionPane.showMessageDialog(this, "SERVER="+SERVER+"  PostURL="+PostURL);
           
        } catch (IOException ex) {
        	JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        
         
		setBackground(Color.white);
		Menu_Font = new Font("Default", Font.PLAIN, 11);
		
		/**
		 * グラフィックスパネルの作成
		 */
		Graphics_PanelM = new GraphicsPanel();
		Graphics_Panel = Graphics_PanelM.getGPanel();
		

		/**
	 	* レイヤーパネルの作成
	 	*/
		Layer_Panel = new LayerPanel();
		
		/**
		 * 上部パネルの作成
		 */
		Top_Panel = new TopPanel(this, Graphics_Panel, Layer_Panel);
	
		/**
		 * 左部ツールパネルの作成
		 */
		JPanel tool_panel = new JPanel();
		tool_panel.setLayout(null);
	    tool_panel.setBackground(Color.white);

		/**
		 * ツールパネルの中のツールボタンパネルを作成
		 */
	    Tool_Panel = new ToolPanel(Graphics_Panel);
	    Tool_Panel.setBounds(4, 2, 170, 342);
	    tool_panel.add(Tool_Panel);
	    tool_panel.setPreferredSize(new Dimension(180, 860)); 	// これが無いと表示しない
	    
		/**
		 * ツールパネルの中のテキストパネルを作成
		 */
	    Text_Panel = new TextPanel();
	    Text_Panel.setBounds(4, 346, 172, 200);
	    tool_panel.add(Text_Panel);

		
	    getContentPane().add(Top_Panel, BorderLayout.NORTH);
	    getContentPane().add(Graphics_PanelM, BorderLayout.CENTER);
	    getContentPane().add(Layer_Panel, BorderLayout.EAST);
	    getContentPane().add(tool_panel, BorderLayout.WEST);
		
/*		
	    test_btn = new JButton("test");
	    test_btn.setFont(Menu_Font);
	    test_btn.setBounds(30, 600, 80, 25);
	    tool_panel.add(test_btn);
	    test_btn.addActionListener(this);
*/	    

		/**
		 * 終了ボタンを作成
		 */
	    end_btn = new JButton(Utility.ImageIcon(this, "img/end.gif"));
	    end_btn.setRolloverIcon(Utility.ImageIcon(this, "img/end_rollover.gif"));
	    end_btn.setPressedIcon(Utility.ImageIcon(this, "img/end_press.gif"));
	    end_btn.setBorder(null);
	    end_btn.setBounds(2, 600, 170, 44);
	    tool_panel.add(end_btn);
	    end_btn.addActionListener(this);
	    tool_panel.addComponentListener(this);
		
		setBackground(BACK_COLOR);
		
		// 起動サイズを設定
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		// スクリーンの中央に配置
		Rectangle screen = getGraphicsConfiguration().getBounds();
		setLocation(screen.x + screen.width/2  - FRAME_WIDTH/2, 
				screen.y + screen.height/2 - FRAME_HEIGHT/2);
		
		DocDialog = new DocSize(null);
		/**
		 * ドキュメントのタイプ（向きと余白）を設定
		 */
		DocDialog.setAlwaysOnTop(true);
		DocDialog.setVisible(true);
		Graphics_Panel.setDocType(1, DocDialog.getDocMuki());
//		Graphics_Panel.setDocType(2, 0);
//		Graphics_Panel.setDocType(0, 0);

		
		setVisible(true);
    	
//		DocDialog.setAlwaysOnTop(false);
	
		/**
		 * 起動時にリサイズして作成したグラフィツクスパネルのサイズを覚える
		 */
		Graphics_PanelM.setInitSize();
		
	
		
		// 起動時はツールボタンは全て使用不可に
		Graphics_Panel.resetToolandLayer(true);
		
		/**
		 * Frameについている終了ボタン処理はデフォルトの処理に任せないで自分で調整する
		 */
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(this);
		
		/**
		 * アプリケーションアイコンを設定する
		 */
//		setIconImage(Utility.ImageIcon(this, "img/icon.gif").getImage());
		
		Boot = true;
		
		this.setLocation(1, 1);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == end_btn) {
			exit(this);
		}
	}
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
		
		// リサイズしたときボタンの位置を変える
//	    size_btn.setLocation(new Point(30, e.getComponent().getHeight()-55));
	    end_btn.setLocation(new Point(4, e.getComponent().getHeight()-48));
	    
		// リサイズしたとき終了ボタンの位置を変える
//	    test_btn.setLocation(new Point(30, e.getComponent().getHeight()-60));
	}
	public void componentShown(ComponentEvent e) {}
	
	public void exit(Component parentComponent) {
		int value = JOptionPane.showConfirmDialog(parentComponent, 
				"このアプリケーションを終了してよろしいですか？", "終了確認", JOptionPane.YES_NO_OPTION);
		if (value == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
	public void windowClosing(WindowEvent e) {
		exit(this);
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}	
	

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args != null &&  args.length >= 1) {
			user_id = args[0];
			//telegram_type_id = args[1];
			
			//JOptionPane.showMessageDialog(null, "args[0]="+args[0]+"  args[1]="+args[1]);			
		}
		
		
		// TODO 自動生成されたメソッド・スタブ
		new edenpo("デコレーションカード");
	}

}
