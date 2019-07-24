package hxy.tanslate;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonSyntaxException;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.tmt.v20180321.TmtClient;

import com.tencentcloudapi.tmt.v20180321.models.TextTranslateRequest;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextTranslate {
    public static void main(String[] args) {

        TextTranslate textTranslate = new TextTranslate();



        String a1="    <p>The software uses an HTTP request parameter to construct a pathname that should be within a restricted directory, but it does not properly neutralize sequences such as \"..\" that can resolve to a location that is outside of that directory.\n" +
                "\n" +
                "See <a href=\"http://cwe.mitre.org/data/definitions/23.html\">http://cwe.mitre.org/data/definitions/23.html</a>\n" +
                "for more information.</p>\n" +
                "<p>FindBugs looks only for the most blatant, obvious cases of relative path traversal.\n" +
                "If FindBugs found <em>any</em>, you <em>almost certainly</em> have more\n" +
                "vulnerabilities that FindBugs doesn't report. If you are concerned about relative path traversal, you should seriously\n" +
                "consider using a commercial static analysis or pen-testing tool.\n" +
                "</p>";
        String a2="  <p>(<a href=\"http://java.sun.com/javase/6/docs/api/java/util/concurrent/ScheduledThreadPoolExecutor.html#ScheduledThreadPoolExecutor(int)\">Javadoc</a>)\n" +
                "A ScheduledThreadPoolExecutor with zero core threads will never execute anything; changes to the max pool size are ignored.\n" +
                "</p>";

     String msg=   textTranslate.translate(a1);
     System.out.println("最后结果："+msg);


    }


    public String translate(String word) {
        System.out.println(word);
//        word=word.replaceAll(":", "：").replace("/", "");
        try {

            Credential cred = new Credential("AKIDrrjMmxhDzWtGRWqOCx34qRnriOr5DfcN", "jEk2Hiz1qSxNaXR8aeS1OWO7anqeCXdU");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tmt.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            TmtClient client = new TmtClient(cred, "ap-shanghai", clientProfile);



            String params = String.format("{\"SourceText\":\"%s\",\"Source\":\"en\",\"Target\":\"zh\",\"ProjectId\":0}", word);
            TextTranslateRequest req = TextTranslateRequest.fromJsonString(params, TextTranslateRequest.class);

            TextTranslateResponse resp = client.TextTranslate(req);

            String consquence = TextTranslateRequest.toJsonString(resp);
            System.out.println(consquence);
            JSONObject object = (JSONObject) JSONObject.parse(consquence);

            String finalMsg = object.getString("TargetText");

            System.out.println(finalMsg);
            return finalMsg;


        } catch (TencentCloudSDKException | JsonSyntaxException e) {
            System.out.println(e.toString());

            //处理下，
           return translateAgain(word);

        }

    }


    public String translate2(String word) {
        System.out.println(word);
        word=word.replaceAll(":", "：").replace("/", "");
        try {

            Credential cred = new Credential("AKIDrrjMmxhDzWtGRWqOCx34qRnriOr5DfcN", "jEk2Hiz1qSxNaXR8aeS1OWO7anqeCXdU");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tmt.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            TmtClient client = new TmtClient(cred, "ap-shanghai", clientProfile);



            String params = String.format("{\"SourceText\":\"%s\",\"Source\":\"en\",\"Target\":\"zh\",\"ProjectId\":0}", word);
            TextTranslateRequest req = TextTranslateRequest.fromJsonString(params, TextTranslateRequest.class);

            TextTranslateResponse resp = client.TextTranslate(req);

            String consquence = TextTranslateRequest.toJsonString(resp);
            System.out.println(consquence);
            JSONObject object = (JSONObject) JSONObject.parse(consquence);

            String finalMsg = object.getString("TargetText");

            System.out.println(finalMsg);
            return finalMsg;


        } catch (TencentCloudSDKException | JsonSyntaxException e) {
            System.out.println(e.toString());

            //处理下，

        }

        return "翻译失败2！";
    }
    /**
     *      * 正则表达式匹配两个指定字符串中间的内容      * @param soap      * @return     
     */
    public static List<String> getSubUtil(String soap, String rgex) {
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }

    /**
     *      * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样      * @param soap     
     * * @param rgex      * @return     
     */
    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }


    /**
     * 对于特殊的语句处理，之后再翻译，翻译完成之后再次执行。
     *
     * @param 输入特殊的字符串，处理后再次翻译
     *
     */

    public String translateAgain(String param) {

        System.out.println("再次翻译的语句："+param);
        String rgex = "<a(.*?)</a>"; //匹配规则
        String rgexStr = getSubUtilSimple(param, rgex);
        System.out.println("匹配的结果"+rgexStr);
        String aString = String.format("<a%s</a>", rgexStr);
        System.out.println(aString);

        String consquence = param.replace(aString, "");
        System.out.println("结果" + consquence);

        //调用翻译，得到结果

       consquence = translate2(consquence);

        StringBuilder sb = new StringBuilder(consquence);// 构造一个StringBuilder对象
        sb.insert(4, aString);// 在指定的位置1，插入指定的字符串
        String str1 = sb.toString();
        System.out.println("最后"+str1);

        return str1;
    }



}