package com.hand.exam3;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class  test {

	public static void main(String[] args) {
		boolean flag =false;

		String[] datas = null;
		try {
			URL u = new URL("http://hq.sinajs.cn/list=sz300170");
			byte[] b = new byte[256];
			InputStream in = null;
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			while (!false) {
				try {
					in = u.openStream();					
					int i;
					while ((i = in.read(b)) != -1) {
						bo.write(b, 0, i);
					}
					String result = bo.toString();
					
					String[] stocks = result.split(";");


					for (String stock : stocks) {
						datas = stock.split(",");
						//根据对照自己对应数据

					}

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document document = builder.newDocument();
					int m=0;
					
					Element root = document.createElement("stock");//创建xml的根节点标签
					Element name = document.createElement("name");
					name.setTextContent(stocks[0] );
					Element open = document.createElement("open");
					open.setTextContent(stocks[1]);
//					Element name1 = document.createElement("name1");
//					name.setTextContent(stocks[2]);
//					Element open1 = document.createElement("open1");
//					open.setTextContent(stocks[3]);

					root.appendChild(name);
					root.appendChild(open);
//					root.appendChild(name1);
//					root.appendChild(open1);
					document.appendChild(root);					

					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();//将xml文档转化为其他格式
					StringWriter stringWriter = new StringWriter(); 
					transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
//					System.out.println(stringWriter.toString());

					transformer.transform(new DOMSource(document), new StreamResult(new File("newxml.xml")));//将新建的xml数据新建到xml文件中
				

					bo.reset();			



				} catch (Exception e) {
					System.out.println(e.getMessage());
				} finally {
					if (in != null) {
						in.close();
						flag = true;
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}

