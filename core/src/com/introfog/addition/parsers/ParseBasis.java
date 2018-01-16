package com.introfog.addition.parsers;

import com.introfog.GameSystem;
import com.introfog.screens.ShowError;

import javax.xml.stream.*;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

public abstract class ParseBasis{
	public static String ABSOLUTE_PATH_TO_PROJECT = "";
	
	
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
	
	
	protected static XMLStreamReader getXML (String path){
		XMLStreamReader xmlReader;
		try{
			String tmpS = ABSOLUTE_PATH_TO_PROJECT + path;
			xmlReader = XMLInputFactory.newInstance ().createXMLStreamReader (tmpS, new FileInputStream (tmpS));
			return xmlReader;
		}
		catch (XMLStreamException ex){
			ShowError.getInstance ().initialize ("проблема с созданием " + path,
												 "был сгенерирован XMLStreamException.");
			return null;
		}
		catch (IOException ex){
			ShowError.getInstance ().initialize ("проблема с созданием " + path,
												 "был сгенерирован IOException при создании потока ввода.");
			return null;
		}
	}
}