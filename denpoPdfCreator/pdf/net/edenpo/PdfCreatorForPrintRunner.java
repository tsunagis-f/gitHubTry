package pdf.net.edenpo;

import java.io.*;
import java.text.SimpleDateFormat;

import com.itextpdf.text.pdf.*;

public class PdfCreatorForPrintRunner {
    public static void main(String[] args) {
        try {
            // PDFを読み込み
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfCopyFields copy = new PdfCopyFields(baos);
            for (String filename : args) {
                copy.addDocument(new PdfReader(filename));
            }
            copy.close();
            
            // 標準出力で出力する
            PrintStream fp = new PrintStream(System.out);
            fp.write(baos.toByteArray());
            fp.flush();
            fp.close();
            baos.close();
        } catch (Exception e) {
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
}
