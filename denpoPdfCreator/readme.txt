�����ݒ聄
01: ../XXXX.jar �̑S�t�@�C�����N���X�p�X�̒ʂ����Ƃ���ɃR�s�[���Ă��������B
����Ƃ��Ă� windows�̏ꍇ�� java/lib/ext/XXXX.jar �ȂǂɂȂ�܂��B

02: Connection.properties �����ɍ��킹�č쐬���Ă��������B
��Connection.properties.sample ���R�s�[���ҏW���Ă��������B
���ύX��F
[host=jdbc:mysql://localhost/newdenpo] �� [host=jdbc:mysql://127.0.0.1:8080/test_newdenpo]
[user=denpo] �� [user=root]
[password=denpo] �� [password=123456]

���R�}���h��
cd java/denpoPdfCreator
java pdf.net.edenpo.PdfCreator #{@purchase.id} 1

������������
1�ځF����ID(purchase.id) , XML�t�@�C���p�X
2�ځF�o�͎��
  1=���C���̈�(PDF�㕔)
  2=���̈�iPDF�����j
  3=PDF�S��(PDF�㕔+����)

�����s�၄
java pdf.net.edenpo.PdfCreator 522647 1
java pdf.net.edenpo.PdfCreator "java/denpoPdfCreator/sample.xml" 2
java pdf.net.edenpo.PdfCreator "java/denpoPdfCreator/sample.xml" 3
java pdf.net.edenpo.PdfCreator "java/denpoPdfCreator/sample.xml" 4
java pdf.net.edenpo.PdfCreator "java/denpoPdfCreator/sample.xml" 5

���o�̓p�X��
�㕔PDF: uploads/pdf/"#{purchase.id}"/"#{purchase.id}"_main.pdf
����PDF: uploads/pdf/"#{purchase.id}"/"#{purchase.id}"_info.pdf
�S��PDF: uploads/pdf/"#{purchase.id}"/"#{purchase.id}".pdf
�A��PDF: uploads/pdf/concat/"#{purchase.id}_#{purchase.id}...".pdf
XMLPDF : "#{xml�t�@�C���Ɠ����p�X�E�������O}".pdf 
