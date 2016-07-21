package pdf.net.edenpo;

import java.io.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class PdfConcat {
    // ベースになるA4のキャンパス
    static private PdfWriter writer;
    // メインのPDFオブジェクト(こっちが上)
    static private PdfReader main_pdf;
    // 差出人情報などが入ったPDFオブジェクト（こっちが下）
    static private PdfReader info_pdf;
    // 情報PDFを始点（X座標）
    static private float     info_x;
    // 情報PDFの始点（Y座標）
    static private float     info_y;
    // メインPDFの始点（X座標）
    static private float     main_x;
    // メインPDFの始点（Y座標）
    static private float     main_y;
    
    static public PdfReader concatPurcahsePDF(PdfReader mainPdf, PdfReader infoPdf) throws DocumentException, IOException {
        main_pdf = mainPdf;
        info_pdf = infoPdf;
        
        // 情報領域・メイン領域の配置をセットする
        setMainAndInfoArea();
        
        // 1ページのみ
        int pageNum = 1;
        
        // PDF出力設定
        Document doc = new Document(PageSize.A4, 0, 0, 0, 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer = PdfWriter.getInstance(doc, baos);
        
        // Documentを開く
        doc.open();
        doc.newPage();
        
        // まずは情報側
        addTemplate(info_pdf, pageNum, info_x, info_y);
        
        // メイン電報領域を載せる
        addTemplate(main_pdf, pageNum, main_x, main_y);
        doc.close();
        
        // 結果を戻す
        return new PdfReader(baos.toByteArray());
    }

    static public PdfReader concatEndingMainPDF(PdfReader mainPdf, PdfReader subscriptionPdf1, PdfReader subscriptionPdf2, PdfReader subscriptionPdf3) throws DocumentException, IOException {
        main_pdf = mainPdf;
        // 情報領域・メイン領域の配置をセットする
        setMainAndInfoArea();
        
        int pageNum = 1;
        
        // PDF出力設定
        Document doc = new Document(PageSize.A5.rotate());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer = PdfWriter.getInstance(doc, baos);
        
        // Documentを開く
        doc.open();
        doc.newPage();
        addTemplate(main_pdf, pageNum, info_x, info_y);
        doc.newPage();
        addTemplate(subscriptionPdf1, pageNum, info_x, info_y);
        if (subscriptionPdf2 != null) {
          doc.newPage();
          addTemplate(subscriptionPdf2, pageNum, info_x, info_y);
        }
        if (subscriptionPdf3 != null) {
          doc.newPage();
          addTemplate(subscriptionPdf3, pageNum, info_x, info_y);
        }
        doc.close();
        
        // 結果を戻す
        return new PdfReader(baos.toByteArray());
    }
    
    static public PdfReader concatEndingPDF(
            PdfReader mainPdf, 
            PdfReader subscriptionPdf1, PdfReader subscriptionPdf2, PdfReader subscriptionPdf3,
            PdfReader infoPdf, 
            PdfReader subscriptionInfoPdf1, PdfReader subscriptionInfoPdf2, PdfReader subscriptionInfoPdf3
        ) throws DocumentException, IOException {
        main_pdf = mainPdf;
        info_pdf = infoPdf;
        // 情報領域・メイン領域の配置をセットする
        setMainAndInfoArea();
        
        int pageNum = 1;
        
        // PDF出力設定
        Document doc = new Document(PageSize.A4, 0, 0, 0, 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer = PdfWriter.getInstance(doc, baos);
        
        // Documentを開く
        doc.open();
        doc.newPage();
        addTemplate(info_pdf, pageNum, info_x, info_y);
        addTemplate(main_pdf, pageNum, main_x, main_y);
        doc.newPage();
        addTemplate(subscriptionInfoPdf1, pageNum, info_x, info_y);
        addTemplate(subscriptionPdf1, pageNum, main_x, main_y);
        if (subscriptionInfoPdf2 != null) {
          doc.newPage();
          addTemplate(subscriptionInfoPdf2, pageNum, info_x, info_y);
          addTemplate(subscriptionPdf2, pageNum, main_x, main_y);
        }
        if (subscriptionInfoPdf3 != null) {
          doc.newPage();
          addTemplate(subscriptionInfoPdf3, pageNum, info_x, info_y);
          addTemplate(subscriptionPdf3, pageNum, main_x, main_y);
        }
        doc.close();
        
        // 結果を戻す
        return new PdfReader(baos.toByteArray());
    }
    
    /*
     * 情報領域・メイン領域の配置をセットする
     */
    static private void setMainAndInfoArea() {
        int rotation = main_pdf.getPageRotation(1);
        if (rotation == 90 || rotation == 270) {
            /* 横書き */
            // 情報PDFを始点（X座標）
            info_x = 0;
            // 情報PDFの始点（Y座標）
            info_y = PageSize.A5.getWidth();
            // メインPDFの始点（X座標）
            main_x = 0;
            // メインPDFの始点（Y座標）
            main_y = PageSize.A4.getHeight();
        } else {
            /* 縦書き */
            // 情報PDFを始点（X座標）
            info_x = 0;
            // 情報PDFの始点（Y座標）
            info_y = PageSize.A5.getWidth();
            // メインPDFの始点（X座標）
            main_x = PageSize.A5.getHeight();
            // メインPDFの始点（Y座標）
            main_y = PageSize.A5.getWidth();
        }
    }
    
    /*
     * テンプレートへデータを流し込む
     */
    static private void addTemplate(PdfReader templateReader, int pageNum, float x, float y) {
        PdfImportedPage page = writer.getImportedPage(templateReader, pageNum);
        if (templateReader.getPageRotation(pageNum) == 90 || templateReader.getPageRotation(pageNum) == 270) {
            writer.getDirectContent().addTemplate(page, 0, -1f, 1f, 0, x, y);
        } else {
            writer.getDirectContent().addTemplate(page, 0, 1f, -1f, 0, x, y);
        }
    }
    
    /*
     * PDFを連結して戻す
     */
    static public PdfReader concatArrayPDF(PdfReader... pdfs) throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfCopyFields copy = new PdfCopyFields(baos);
        for(int i=0; i < pdfs.length; i++){
            copy.addDocument(pdfs[i]);
        }
        copy.close();
        
        // 結果を戻す
        return new PdfReader(baos.toByteArray());
    }
}
