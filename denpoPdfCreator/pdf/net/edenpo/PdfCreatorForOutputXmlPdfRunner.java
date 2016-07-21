package pdf.net.edenpo;

import java.io.*;
import java.text.*;

import com.itextpdf.text.pdf.*;

public class PdfCreatorForOutputXmlPdfRunner {
    public static void main(String[] args) {
        try {
            String xml_filepath = "../../" + args[0];
            int outputType = Integer.parseInt(args[1]);
            
            // DBから注文情報取得
            Purchase purchase = new Purchase();
            purchase.loadXml(xml_filepath);
            
            // PDFファイルパスを指定
            PdfManager pdfMng = new PdfManager(purchase.telegramLayoutId);
            
            // /////PDFファイル出力/////////
            PdfReader reader = null;
            switch (outputType) {
            case 1:
                reader = pdfMng.getMainPdfReader(purchase);
                break;
            case 2:
                reader = pdfMng.getInfoPdfReader(purchase);
                break;
            case 3:
                reader = PdfConcat.concatPurcahsePDF(
                        pdfMng.getMainPdfReader(purchase),
                        pdfMng.getInfoPdfReader(purchase));
                break;
            case 4:
                // エンディング電報（メインのみ）
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
            // PDFを読み込み
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfCopyFields copy = new PdfCopyFields(baos);
            copy.addDocument(new PdfReader(reader));
            copy.close();
            reader.close();
            
            // 標準出力で出力する
            PrintStream fp = new PrintStream(System.out);
            // PDFを出力
            fp.write(baos.toByteArray());
            fp.flush();
            fp.close();
            baos.close();
        } catch (Exception e) {
            try {
                PrintStream ps = new PrintStream(new FileOutputStream(
                        "exception_log.txt", true));
                ps.println("--------------------------------------");
                ps.println(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
                        .format(new java.util.Date()));
                ps.println(args[0]);
                e.printStackTrace(ps);
                ps.close();
                e.printStackTrace();
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
    }
}
