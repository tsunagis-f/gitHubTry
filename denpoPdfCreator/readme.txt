＜環境設定＞
01: ../XXXX.jar の全ファイルをクラスパスの通ったところにコピーしてください。
※例としては windowsの場合は java/lib/ext/XXXX.jar などになります。

02: Connection.properties を環境に合わせて作成してください。
※Connection.properties.sample をコピーし編集してください。
※変更例：
[host=jdbc:mysql://localhost/newdenpo] ⇒ [host=jdbc:mysql://127.0.0.1:8080/test_newdenpo]
[user=denpo] ⇒ [user=root]
[password=denpo] ⇒ [password=123456]

＜コマンド＞
cd java/denpoPdfCreator
java pdf.net.edenpo.PdfCreator #{@purchase.id} 1

＜引数説明＞
1つ目：注文ID(purchase.id) , XMLファイルパス
2つ目：出力種別
  1=メイン領域(PDF上部)
  2=情報領域（PDF下部）
  3=PDF全体(PDF上部+下部)

＜実行例＞
java pdf.net.edenpo.PdfCreator 522647 1
java pdf.net.edenpo.PdfCreator "java/denpoPdfCreator/sample.xml" 2
java pdf.net.edenpo.PdfCreator "java/denpoPdfCreator/sample.xml" 3
java pdf.net.edenpo.PdfCreator "java/denpoPdfCreator/sample.xml" 4
java pdf.net.edenpo.PdfCreator "java/denpoPdfCreator/sample.xml" 5

＜出力パス＞
上部PDF: uploads/pdf/"#{purchase.id}"/"#{purchase.id}"_main.pdf
下部PDF: uploads/pdf/"#{purchase.id}"/"#{purchase.id}"_info.pdf
全体PDF: uploads/pdf/"#{purchase.id}"/"#{purchase.id}".pdf
連結PDF: uploads/pdf/concat/"#{purchase.id}_#{purchase.id}...".pdf
XMLPDF : "#{xmlファイルと同じパス・同じ名前}".pdf 
