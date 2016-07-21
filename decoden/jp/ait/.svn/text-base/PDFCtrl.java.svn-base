package jp.ait;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;
import java.net.*;

import javax.swing.*;

import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import javax.jnlp.*;
import com.lowagie.text.pdf.VerticalText;

class PDFCtrl {
	static PdfWriter writer = null;
	
	public void sendPDF(int muki) {
	}
	
	// TEST用　サーバーに送らないでカレントフォルダに test.pdf を書き出す
	static public void SendPDF2(int muki) {
		Document document;		
		
		if (muki == 0) {
			// 縦向き
			document = new Document(PageSize.A5);
		} else {
			// 横向き
			document = new Document(PageSize.A5.rotate(), 0, 0, 0, 0);
		}
		long before, after;
		try {
		
			ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
			PdfWriter writer =  PdfWriter.getInstance(document, baos2);
	    
	    
			// step 3
			document.open();
			// step 4
			drawPDF(writer, (int)document.getPageSize().width(), (int)document.getPageSize().height());
			document.close();
			writer.close();
					
			// PDFを書き出す
//			out.write(baos2.toByteArray());
			write2(baos2);
		} catch(DocumentException de) {
			JOptionPane.showMessageDialog(null, de.getMessage());
		}
	}
	
	static public void SendPDF(int muki) {
		// step 1: creation of a document-object
		
//JOptionPane.showMessageDialog(edenpo.Main_Frame, "Start SendPDF");
		
		Document document;		
		
		if (muki == 0) {
			// 縦向き
			document = new Document(PageSize.A5);
		} else {
			// 横向き
			document = new Document(PageSize.A5.rotate(), 0, 0, 0, 0);
		}
		long before, after;
       	
		// TEST
//		edenpo.PostURL = "http://125.101.10.179:9000/testup/return_pdf";
//		edenpo.PostURL = "http://test-denpo.a-it.jp/user/create_decoration_card";
//		edenpo.PostURL = "http://125.101.10.179:9000/user/create_decoration_card";	
		try {

			// URLクラスのインスタンスを生成
			URL url = new URL(edenpo.PostURL);
//JOptionPane.showMessageDialog(edenpo.Main_Frame, edenpo.PostURL);

			
			// 接続します
			URLConnection con = url.openConnection();
			
			// パスワードを通す
			String userPassword =  "usr" + ":" + "EW8inC45";
//			String userPassword =  "msc" + ":" + "msc";

			 // Base64 encode the authentication string
			String encoding = new sun.misc.BASE64Encoder().encode (userPassword.getBytes());
			    
			con.setRequestProperty ("Authorization", "Basic " + encoding);

			
	    	String boundary = "-bx+0f";
			con.setRequestProperty("Content-Type", "multipart/form-data;  boundary=" + boundary);
//			con.setRequestProperty("Content-Type", "multipart/form-data");
			
			// 出力を行うように設定します (POST可能にする)
			con.setDoOutput(true);
		
//JOptionPane.showMessageDialog(edenpo.Main_Frame, "pass1");
       
			// POST用のOutputStreamを取得
			OutputStream os = con.getOutputStream();	    	
            DataOutputStream out = new DataOutputStream(os);
            
           
            /**`
             * id
             */
            write(out, "--" +boundary + "\r\n");
            write(out, "Content-Disposition: form-data; name=\"id\"\r\n");
		    write(out, "Content-Type: text/plain\r\n\r\n"); 
		    write(out, ""+edenpo.user_id);
            write(out, "\r\n");
            
            /**
             * file
             */
            write(out, "--" + boundary + "\r\n");
            write(out, "Content-Disposition: form-data; name=\"pdf\"; " + "filename=\""+ "pdf_file.pdf" + "\"" + "\r\n");
		    out.flush();
		    write(out, "Content-Type: application/pdf\r\n\r\n"); // 改行を2回、つまり空白行を入れる.その後ファイルの実体を書く.
		    
//System.out.println("id="+edenpo.user_id);
//JOptionPane.showMessageDialog(edenpo.Main_Frame, "pass2");
		    /**
		     * PDFをメモリ上に展開
		     */
		    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
		    PdfWriter writer =  PdfWriter.getInstance(document, baos2);
//		    PdfWriter  writer = PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));

		    
		    
	        // step 3
	        document.open();
	        // step 4
		    drawPDF(writer, (int)document.getPageSize().width(), (int)document.getPageSize().height());
			document.close();
						
			// PDFを書き出す
			out.write(baos2.toByteArray());
//			write2(baos2);    TEST用
			
	        write(out, "\r\n");
	        write(out, "--" + boundary + "--");
		    out.flush();
		    out.close();
//		    fileDataIn.close();


//JOptionPane.showMessageDialog(edenpo.Main_Frame, "pass3");
		
		
			// 結果を得る
		    InputStream in = con.getInputStream();	// 入力ストリームを取得
			BufferedReader bReader = new BufferedReader(new InputStreamReader(in));

			String line = bReader.readLine();
//			System.out.println("Result="+line);
			
			// 入力ストリームを閉じます
			bReader.close();

//		    String line = "http://www.yahoo.co.jp";
//JOptionPane.showMessageDialog(edenpo.Main_Frame, "result="+line);
			if (line != null) {
				showURL(line);					
			}

		    writer = null;

		}
		catch(DocumentException de) {
			JOptionPane.showMessageDialog(null, de.getMessage());
  		}
		catch(IOException ioe) {
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(null, ioe.getMessage());
		}
    
	}
	
	static void drawPDF(PdfWriter writer, int width, int height) {
       PdfContentByte cb = writer.getDirectContent();
       java.awt.Graphics2D g2 = cb.createGraphicsShapes((int)(width), (int)(height));
        
		edenpo.Graphics_Panel.setScale(72.0f / edenpo.Graphics_PanelM.DPI);
        
        //　クリア
        g2.setColor(Color.white);
        g2.fillRect(0, 0, width, height);
        
        // 背景描画
    	edenpo.Graphics_Panel.drawBackground(g2, width, height);

    	// 入力画像を描画
    	for (int i = 0; i < edenpo.Graphics_Panel.display_list.size(); i++) {
    		Group obj = (Group)edenpo.Graphics_Panel.display_list.get(i);
    		obj.draw(g2, edenpo.Main_Frame);

    	}
    	
    	g2.dispose();
		
	}
	
	static void showURL(String addr) {
		try {
			URL url = new URL(addr);
		
			String osName = System.getProperty("os.name");
			// MacOSなら jnlp のshowDocumentを使う
			//if (! "Mac OS".equals(osName)) {
			if (! osName.startsWith("Mac OS")) {
				BrowserLauncher.openURL(addr);
				return;
			}

			// Lookup the javax.jnlp.BasicService object
	        BasicService bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
	        // Invoke the showDocument method
	        bs.showDocument(url);
		} catch(UnavailableServiceException ue) {
	          // Service is not supported
			JOptionPane.showMessageDialog(null, ue.getMessage());
		} catch(IOException ioe) {
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(null, ioe.getMessage());
		}
	}


	/**
	 * Debugに併用
	 * @param out
	 * @param str
	 */
	static private void write(DataOutputStream out, String str) {
//		System.out.print(str);
		try {
			out.writeBytes(str);
		}
		catch(IOException ioe) {
			JOptionPane.showMessageDialog(null, ioe.getMessage());
		}
	}
	
	static private void write2(ByteArrayOutputStream out) {
	   OutputStream fout = null;
	   byte [] date = out.toByteArray();
	   int size = out.size();
	   
	    try {
	        fout = new BufferedOutputStream(new FileOutputStream("test.pdf"));
	        int b;
	        for (int i = 0; i < size; i++) {
	        	fout.write(date[i]);
	        }
	    } catch (FileNotFoundException e) {
	    } catch (IOException e) {
	    } finally {
	        try {
	            if (out != null) {
	            	fout.flush();
	            	fout.close();
	            }
	        } catch (Exception e) {
	        }
	    }
	}

}
