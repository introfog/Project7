package com.introfog.addition.parsers;

import com.introfog.GameSystem;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.*;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

public abstract class ParseBasis{
	protected static boolean isFromIDEA = true; //флаг, хранящий откуда мы запускаем проект, с IDEA или с .jar архива
	
	
	private static String getAbsolutePath (){
		URL location = ParseBasis.class.getProtectionDomain ().getCodeSource ().getLocation ();
		String classLocation = null;
		
		try{
			classLocation = URLDecoder.decode (location.getFile ().substring (1).replace ('/', File.separatorChar), Charset.defaultCharset ().name ());
		}
		catch (UnsupportedEncodingException ex){
			ex.printStackTrace (System.out);
		}
		
		return classLocation;
	}
	
	protected static Document getDocument (String fromIDEA, String fromDesktop){
		Document document;
		try{
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance ().newDocumentBuilder ();
			
			StringBuilder path = new StringBuilder (getAbsolutePath ());
			int index = path.lastIndexOf (GameSystem.NAME_JAR_ARCHIVE); //ищем в этом пути имя архива
			if (index == -1){ //если не нашли, значит мы запускаем проект с IDEA
				isFromIDEA = true;
				document = documentBuilder.parse (fromIDEA);
			}
			else{ //если нашли, значит запускаем с .jar архива
				isFromIDEA = false;
				path.delete (index, path.length ()); //удаляем все начиная с имя архива
				path.append (fromDesktop);
				document = documentBuilder.parse (path.toString ());
			}
			
			
			return document;
		}
		catch (SAXException | IOException | ParserConfigurationException ex){
			ex.printStackTrace (System.out);
			return null;
		}
	}
	
	protected static XMLStreamReader getXML (String fromIDEA, String fromDesktop){
		XMLStreamReader xmlr;
		try{
			URLDecoder decoder = new URLDecoder ();
			StringBuilder path = new StringBuilder (decoder.decode (
					ParseBasis.class.getProtectionDomain ().getCodeSource ().getLocation ().getPath ()));
			int index = path.lastIndexOf (GameSystem.NAME_JAR_ARCHIVE); //ищем в этом пути имя архива
			if (index == -1){ //если не нашли, значит мы запускаем проект с IDEA
				isFromIDEA = true;
				xmlr = XMLInputFactory.newInstance ().createXMLStreamReader (fromIDEA, new FileInputStream (fromIDEA));
			}
			else{ //если нашли, значит запускаем с .jar архива
				isFromIDEA = false;
				path.delete (index, path.length ()); //удаляем все начиная с имя архива
				path.append (fromDesktop);
				xmlr = XMLInputFactory.newInstance ().createXMLStreamReader (path.toString (),
																			 new FileInputStream (path.toString ()));
			}
			
			return xmlr;
		}
		catch (IOException | XMLStreamException ex){
			ex.printStackTrace (System.out);
			return null;
		}
	}
}
