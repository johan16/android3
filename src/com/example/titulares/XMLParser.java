package com.example.titulares;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
* 
* 
* Esta clase procesa los datos de un archivo XML
* Es una clase para cualquier tipo de archivo.
* Utiliza el nodo->item
* para obtener los valores "link" y "title"
* 
* Pueden modificar ambos valores para obtener otros nodos de XML.
* 
*/

public class XMLParser {


	private URL url;

	public XMLParser(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}


	public LinkedList<HashMap<String, String>> parse() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		LinkedList<HashMap<String, String>> entries = new LinkedList<HashMap<String, String>>();
		HashMap<String, String> entry;

		try {

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.url.openConnection().getInputStream());
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("item");
			for (int i=0;i<items.getLength();i++){
				entry = new HashMap<String, String>();				
				Node item = items.item(i);
				NodeList properties = item.getChildNodes();
				for (int j=0;j<properties.getLength();j++){
					Node property = properties.item(j);
					String name = property.getNodeName();

					if (name.equalsIgnoreCase("title")){
						// CAMBIAR AL NOMBRE DE TU ACTIVITY
						entry.put(PrincipalActivity.DATA_TITLE, property.getFirstChild().getNodeValue());
					} 
					if (name.equalsIgnoreCase("link")){
						// CAMBIAR AL NOMBRE DE TU ACTIVITY
						entry.put(PrincipalActivity.DATA_LINK, property.getFirstChild().getNodeValue());						
					} 
					if(name.equalsIgnoreCase("description")){
						// CAMBIAR AL NOMBRE DE TU ACTIVITY
						entry.put(PrincipalActivity.DATA_DESCRIPTION, property.getFirstChild().getNodeValue());						
					}

				}
				entries.add(entry);
			}
		} catch (Exception e) {
			//throw new RuntimeException(e);
			e.printStackTrace();
		} 

		return entries;
	}		
}