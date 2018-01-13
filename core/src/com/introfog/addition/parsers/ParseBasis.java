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
	public static String ABSOLUTE_PATH_TO_PROJECT = "";
	
	
	protected static boolean isFromIDEA = true; //флаг, хранящий откуда мы запускаем проект, с IDEA или с .jar архива
	
	
	public static void findAbsolutePath (){
		URL location = ParseBasis.class.getProtectionDomain ().getCodeSource ().getLocation ();
		
		try{
			String classLocation = URLDecoder.decode (location.getFile ().substring (1).replace ('/', File.separatorChar), Charset.defaultCharset ().name ());
			StringBuilder path = new StringBuilder (classLocation);
			if (path.lastIndexOf (GameSystem.NAME_JAR_ARCHIVE) == -1){
				path.delete (path.lastIndexOf ("core"), path.length ());
				ABSOLUTE_PATH_TO_PROJECT = path.toString ();
			}
			else{
				path.delete (path.lastIndexOf (GameSystem.NAME_JAR_ARCHIVE), path.length ());
				ABSOLUTE_PATH_TO_PROJECT = path.toString ();
			}
		}
		catch (UnsupportedEncodingException ex){
			ex.printStackTrace (System.out);
		}
	}
	
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
	
	protected static Document getDocument (String path){
		Document document;
		try{
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance ().newDocumentBuilder ();
			
			String tmpS = ABSOLUTE_PATH_TO_PROJECT + path;
			System.out.println ("Path: " + tmpS);
			//крч я думаю дело в том что нужны другой слэш
			document = documentBuilder.parse (tmpS);
			
			return document;
		}
		catch (SAXException | IOException | ParserConfigurationException ex){
			ex.printStackTrace (System.out);
			return null;
		}
	}
	
	protected static XMLStreamReader getXML (String path){
		XMLStreamReader xmlReader;
		try{
			String tmpS = ABSOLUTE_PATH_TO_PROJECT + path;
			xmlReader = XMLInputFactory.newInstance ().createXMLStreamReader (tmpS, new FileInputStream (tmpS));
			return xmlReader;
		}
		catch (IOException | XMLStreamException ex){
			ex.printStackTrace (System.out);
			return null;
		}
	}
}
