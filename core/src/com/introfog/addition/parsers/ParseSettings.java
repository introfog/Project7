package com.introfog.addition.parsers;

import com.introfog.*;
import com.introfog.screens.ShowError;

import javax.xml.stream.*;

import java.io.*;

public class ParseSettings extends ParseBasis{
	private static final String path = "resource/xml/settings.xml";
	
	
	public static void parseSettings (){
		try{
			XMLStreamReader xmlReader = getXML (path);
			if (xmlReader == null){
				ShowError.getInstance ().initialize ("проблема с чтением " + path,
													 "файл не был найден.");
				return;
			}
			
			String name = "";
			while (xmlReader.hasNext ()){
				xmlReader.next ();
				if (xmlReader.isStartElement () && xmlReader.getLocalName ().compareTo ("field") == 0){
					name = xmlReader.getAttributeValue (0);
				}
				if (xmlReader.hasText () && xmlReader.getText ().trim ().length () > 0){
					String text = xmlReader.getText ();
					switch (name){
					case "numLevels":
						GameSystem.NUM_LEVELS = Integer.parseInt (text);
						break;
					case "isFirstGameStart":
						GameSystem.IS_FIRST_GAME_START = Boolean.parseBoolean (text);
						break;
					case "numPassedLevels":
						GameSystem.NUM_PASSED_LEVELS = Integer.parseInt (text);
						break;
					case "currentLevel":
						GameSystem.CURRENT_LEVEL = Integer.parseInt (text);
						break;
					case "gameOver":
						GameSystem.GAME_OVER = Boolean.parseBoolean (text);
						break;
					}
				}
			}
		}
		catch (XMLStreamException ex){
			ShowError.getInstance ().initialize ("проблема с чтением " + path,
												 "был сгенерирован XMLStreamException.");
		}
		catch (NumberFormatException ex){
			ShowError.getInstance ().initialize ("проблема с чтением " + path,
												 "неверный формат данных в файле.");
		}
	}
	
	public static void writeSettings (){
		try{
			XMLOutputFactory output = XMLOutputFactory.newInstance ();
			XMLStreamWriter xmlWriter = output.createXMLStreamWriter (
					new FileOutputStream (ABSOLUTE_PATH_TO_PROJECT + path));
			
			// Открываем XML-документ и Пишем корневой элемент BookCatalogue
			xmlWriter.writeStartDocument ("1.0");
			
			
			xmlWriter.writeStartElement ("root");
			
			xmlWriter.writeStartElement ("field");
			xmlWriter.writeAttribute ("name", "numLevels");
			xmlWriter.writeCharacters ("" + GameSystem.NUM_LEVELS);
			xmlWriter.writeEndElement ();
			
			xmlWriter.writeStartElement ("field");
			xmlWriter.writeAttribute ("name", "isFirstGameStart");
			xmlWriter.writeCharacters ("" + GameSystem.IS_FIRST_GAME_START);
			xmlWriter.writeEndElement ();
			
			xmlWriter.writeStartElement ("field");
			xmlWriter.writeAttribute ("name", "numPassedLevels");
			xmlWriter.writeCharacters ("" + GameSystem.NUM_PASSED_LEVELS);
			xmlWriter.writeEndElement ();
			
			xmlWriter.writeStartElement ("field");
			xmlWriter.writeAttribute ("name", "currentLevel");
			xmlWriter.writeCharacters ("" + GameSystem.CURRENT_LEVEL);
			xmlWriter.writeEndElement ();
			
			xmlWriter.writeStartElement ("field");
			xmlWriter.writeAttribute ("name", "gameOver");
			xmlWriter.writeCharacters ("" + GameSystem.GAME_OVER);
			xmlWriter.writeEndElement ();
			
			xmlWriter.writeEndElement ();
			
			
			xmlWriter.writeEndDocument ();
			xmlWriter.flush ();
		}
		catch (XMLStreamException ex){
			ShowError.getInstance ().initialize ("проблема с записью в " + path,
												 "был сгенерирован XMLStreamException.");
		}
		catch (IOException ex){
			ShowError.getInstance ().initialize ("проблема с записью в " + path,
												 "был сгенерирован IOException при открытии потока вывода.");
		}
	}
}
