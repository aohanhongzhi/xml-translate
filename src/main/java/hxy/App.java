package hxy;

import hxy.analysis.Dom4jDemo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String path = "/home/eric/Work/Project/findbugs/translation/AndroidTranslate-master/messages.xml";
        Dom4jDemo dom4jDemo = new Dom4jDemo();
        dom4jDemo.parserXml(path);

//        dom4jDemo.createXml("create.xml");
    }
}
