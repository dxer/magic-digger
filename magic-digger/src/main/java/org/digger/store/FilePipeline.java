package org.digger.store;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.digger.WebPage;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @author linghf
 * @version 1.0
 * @class FilePipeline
 * @since 2015年5月26日
 */
public class FilePipeline implements Pipeline {

    @Override
    public void process() {
        WebPage webPage = null;
        OutputFormat format = new OutputFormat("\t", true); // createPrettyPrint() 层次格式化
        XMLWriter output = null;

        try {
            Document doc = DocumentHelper.createDocument();
            Element webpage = doc.addElement("webpage");
            Element url = webpage.addElement("url");
            url.setText(webPage.getUrl());

            Element fetchTime = webpage.addElement("fetchTime");
            fetchTime.setText(webPage.getFetchTime().toString());

            Element content = webpage.addElement("content");

            Map<String, String> fetchTexts = webPage.getFetchText();

            if (fetchTexts != null && fetchTexts.size() > 0) {
                for (String label : fetchTexts.keySet()) {
                    String con = fetchTexts.get(label);
                    Element labelElement = content.addElement(label);
                    labelElement.setText(con);
                }
            }

            output = new XMLWriter(new FileOutputStream(""), format);
            output.write(doc);
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
