package pdf.net.edenpo;

import java.io.*;
import java.text.*;
import java.util.*;

import com.itextpdf.text.pdf.*;

public class PdfCreator {
    public static void main(String[] args) {
        try {
            int purchaseId = 0;
            try { purchaseId = Integer.parseInt(args[0]); } catch (NumberFormatException e) {} 
            int outputType = Integer.parseInt(args[1]);
            
            // PDF生成
            if (purchaseId != 0) {
                PdfCreator.createPdf(purchaseId, outputType);
            } else {
                PdfCreator.createPdf("../../" + args[0], outputType);
            }
        } catch(Exception e) {
            try{
                PrintStream ps = new PrintStream(new FileOutputStream("exception_log.txt",true));
                ps.println("--------------------------------------");
                ps.println(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new java.util.Date()));
                ps.println(args[0]);
                e.printStackTrace(ps);
                ps.close();
                e.printStackTrace();
            }catch(Exception ex){
                e.printStackTrace();
            }
        }
    }
    
    // DBから注文情報を取得PDF作成
    public static void createPdf(Integer purchaseId, Integer outputType) throws Exception {
        // ファイルパスの読込
        Properties prop = new Properties();
        prop.load(new FileInputStream("Filepath.properties"));
        String pdf_path = prop.getProperty("purchase_pdf_path");
        // DBから注文情報取得        
        Purchase purchase = DatabaseManager.getPurchase(purchaseId);
        // PDFファイルパスを指定
        String filepath = pdf_path + purchase.id + "/" + purchase.id + ".pdf";
        // PDF生成
        PdfCreator.createPdf(purchase, outputType, filepath);
    }
    
    // XML からPDF作成
    public static void createPdf(String xml_filepath, Integer outputType) throws Exception {        
        // XMLから注文情報取得        
        Purchase purchase = new Purchase();
        purchase.loadXml(xml_filepath);
        // PDFファイルパスを指定
        String filepath = xml_filepath.replaceAll(xml_filepath.substring(xml_filepath.lastIndexOf(".")), ".pdf");
        // PDF生成
        PdfCreator.createPdf(purchase, outputType, filepath);
    }    
    
    public static void createPdf(Purchase purchase, Integer outputType, String filepath) throws Exception {
        // 電報レイアウトに応じたプロパティを呼び出し
        PdfManager pdfMng = new PdfManager(purchase.telegramLayoutId);
        
        // /////PDFファイル出力/////////
        PdfReader reader = null;
        switch (outputType) {
        case 1:
            filepath = filepath.replaceAll(".pdf$", "_main.pdf");
            reader = pdfMng.getMainPdfReader(purchase);
            break;
        case 2:
            filepath = filepath.replaceAll(".pdf$", "_info.pdf");
            reader = pdfMng.getInfoPdfReader(purchase);
            break;
        case 3:
            reader = PdfConcat.concatPurcahsePDF(pdfMng.getMainPdfReader(purchase), pdfMng.getInfoPdfReader(purchase));
            break;
        case 4:
            // エンディング電報（メインのみ）
            filepath = filepath.replaceAll(".pdf$", "_main.pdf");
            reader = PdfConcat.concatEndingMainPDF(
                    pdfMng.getEndingMainPdfReader(purchase),
                    pdfMng.getEndingSubscriptionPdfReader(purchase,1),
                    pdfMng.getEndingSubscriptionPdfReader(purchase,2),
                    pdfMng.getEndingSubscriptionPdfReader(purchase,3)
            );
            break;
        case 5:
            // エンディング電報（全体）
            reader = PdfConcat.concatEndingPDF(
                    pdfMng.getEndingMainPdfReader(purchase),
                    pdfMng.getEndingSubscriptionPdfReader(purchase,1),
                    pdfMng.getEndingSubscriptionPdfReader(purchase,2),
                    pdfMng.getEndingSubscriptionPdfReader(purchase,3),
                    pdfMng.getInfoPdfReader(purchase),
                    pdfMng.getEndingSubscriptionInfoPdfReader(purchase,1),
                    pdfMng.getEndingSubscriptionInfoPdfReader(purchase,2),
                    pdfMng.getEndingSubscriptionInfoPdfReader(purchase,3)
            );
            break;
        }
        
        // ディレクトリ作成
        File carrent_dir = new File((new File(filepath)).getParent());
        if (!carrent_dir.exists()) {carrent_dir.mkdirs(); }
        
        // PDFの書き込み
        OutputStream out = new FileOutputStream(filepath);
        PdfStamper stamper = new PdfStamper(reader, out);
        stamper.close();
        out.close();
    }
}
