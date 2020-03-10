package hxy.analysis;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hxy.dao.BugPatternDao;
import hxy.dao.model.BugPattern;
import hxy.tanslate.TextTranslate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author hongliang.dinghl
 * Dom4j 生成XML文档与解析XML文档
 */
public class Dom4jDemo {

    //    @Test
    public void createXml(String fileName) {
        Document document = DocumentHelper.createDocument();
        Element employees = document.addElement("employees");
        Element employee = employees.addElement("employee");
        Element name = employee.addElement("name");
        name.setText("ddvip");
        Element sex = employee.addElement("sex");
        sex.setText("m");
        Element age = employee.addElement("age");
        age.setText("29");
        try {
            Writer fileWriter = new FileWriter(fileName);
            XMLWriter xmlWriter = new XMLWriter(fileWriter);
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }


    }


    /**
     * 解析xml存储到数据库
     *
     * @param fileName
     */
    public void parserXmlToDB(String fileName) {

        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputXml);
            Element employees = document.getRootElement();

            System.out.println("root:" + employees.getName());

            List<BugPattern> list = new ArrayList<>();

            for (Iterator i = employees.elementIterator(); i.hasNext(); ) {
                Element employee = (Element) i.next();
                BugPattern bugPattern = null;


                if ("BugPattern".equals(employee.getName())) {
                    bugPattern = new BugPattern();
                    employee.getNamespace();
                    String type = employee.attributeValue("type");
                    System.out.println(employee.getName() + ":" + type);
                    bugPattern.setBugPatthern(type);

                }
                for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    String name = node.getName();
                    String text = node.getText();
                    System.out.println(name + ":" + text);
                    if ("ShortDescription".equals(name)) {
                        if (bugPattern!=null){
                            bugPattern.setShortDescription(text);
                        }else{

                        }
                    } else if ("LongDescription".equals(name)) {

                    } else if ("Details".equals(name)) {
                        if (bugPattern!=null){
                            bugPattern.setDetails(text.replace("<p>", "").replace("</p>", ""));
                        }
                    }


                }
                if (bugPattern != null) {
                    list.add(bugPattern);

                }


            }
            BugPatternDao bugPatternDao =new BugPatternDao();
            bugPatternDao.insert(list);
            System.out.println("bugpattern:" + list.size());


        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("dom4j parserXml");
    }

    /**
     * 翻译message.xml文件
     *
     * @param fileName
     */
    public void parserXml(String fileName) {
        BufferedWriter out = null;

        try {
            File writeName = new File("output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            FileWriter writer = new FileWriter(writeName);
            out = new BufferedWriter(writer);

        } catch (IOException e) {
            e.printStackTrace();
        }


//        新建一个新的文档
        Document documentNew = DocumentHelper.createDocument();


        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputXml);
            Element employees = document.getRootElement();

            System.out.println("root:" + employees.getName());

//            documentNew.setRootElement(employees);
            documentNew.addElement(employees.getName());

            for (Iterator i = employees.elementIterator(); i.hasNext(); ) {
                Element employee = (Element) i.next();
//                documentNew.add(employee);
                Element a = documentNew.getRootElement().addElement(employee.getName());
                for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
//                    System.out.println(node.getName() + ":" + node.getText());


                    TextTranslate textTranslate = new TextTranslate();

                    String msg = textTranslate.translate(node.getText());

//                    out.write(String.format("%s:%s\n",node.getName() ,"<<开始"+msg+"结束>>\n")); // \r\n即为换行
                    out.flush(); // 把缓存区内容压入文件

                    Element elementNew = node;
                    elementNew.setText(msg);
                    a.addElement(node.getName(), msg);
                }

            }
            try {
                //翻译后输出的文档
                Writer fileWriter = new FileWriter("/home/eric/new.xml");
                XMLWriter xmlWriter = new XMLWriter(fileWriter);
                xmlWriter.write(document);
                xmlWriter.close();
            } catch (IOException e) {

                System.out.println(e.getMessage());
            }


        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("dom4j parserXml");
    }

    public static void main(String args[]) {
        String path = "/home/eric/Work/Project/findbugs/translation/AndroidTranslate-master/messages.xml";
        path = "/home/eric/Work/Project/audit/code/engine/findbugs/translation/xml-translate/src/main/resources/messages_zh.xml";
        Dom4jDemo dom4jDemo = new Dom4jDemo();
        dom4jDemo.parserXmlToDB(path);

//        dom4jDemo.createXml("create.xml");
    }
}