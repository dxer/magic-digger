package org.digger.store;

import org.digger.conf.Config;
import org.digger.model.FetchResult;
import org.digger.utils.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author linghf
 * @version 1.0
 * @class FilePipeline
 * @since 2015年5月26日
 */
public class FileStorer extends AbstractStorer {

    @Override
    public void process(FetchResult fetchResult) {
        if (fetchResult == null) {
            return;
        }

        if (StringUtil.isEmpty(Config.getSavePath())) {
            return;
        }

        OutputFormat format = new OutputFormat("\t", true); // createPrettyPrint() 层次格式化
        XMLWriter output = null;


        try {


            output = new XMLWriter(new FileOutputStream(Config.getSavePath() + UUID.randomUUID().toString() + ".xml"), format);


            Document doc = DocumentHelper.createDocument();
            Element root = doc.addElement("root");
            Element url = root.addElement("url");
            url.setText(fetchResult.getUrl());

            Element fetchTime = root.addElement("fetchTime");
            System.out.println(fetchResult.getFetchTime());
            fetchTime.setText(fetchResult.getFetchTime().toString());

            Element body = root.addElement("body");

            Map<String, String> fetchTexts = fetchResult.getFetchText();

            if (fetchTexts != null && fetchTexts.size() > 0) {
                for (String label : fetchTexts.keySet()) {
                    String content = fetchTexts.get(label);
                    if (!StringUtil.isEmpty(label) && !StringUtil.isEmpty(content)) {
                        Element labelElement = body.addElement(label);
                        labelElement.setText(content);
                    }
                }
            }


            output.write(doc);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                }
            }
        }

    }

    public static void main(String[] args) {
        FetchResult fetchResult = new FetchResult();
        fetchResult.setUrl("http://www.baidu.com");
        fetchResult.setFetchTime(new Date());

        OutputFormat format = new OutputFormat("\t", true); // createPrettyPrint() 层次格式化
        XMLWriter output = null;

        try {
            output = new XMLWriter(new FileOutputStream(Config.getSavePath() + UUID.randomUUID().toString() + ".xml"), format);

            Document doc = DocumentHelper.createDocument();
            Element webpage = doc.addElement("webpage");
            Element url = webpage.addElement("url");
            url.setText(fetchResult.getUrl());

            Element fetchTime = webpage.addElement("fetchTime");
            fetchTime.setText(fetchResult.getFetchTime().toString());

            Element content = webpage.addElement("content");

            Map<String, String> fetchTexts = fetchResult.getFetchText();

            if (fetchTexts != null && fetchTexts.size() > 0) {
                for (String label : fetchTexts.keySet()) {
                    String con = fetchTexts.get(label);
                    Element labelElement = content.addElement(label);
                    labelElement.setText(con);
                }
            }

            output.write(doc);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
