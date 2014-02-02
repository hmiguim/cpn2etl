/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author hmg
 */
public class Main {

    public static void main(String args[]) {
        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                boolean place = false;
                boolean place_posX = false;
                boolean place_posY = false;

                @Override
                public void startElement(String uri, String localName, String qName,
                        Attributes attributes) throws SAXException {

                    if (qName.equalsIgnoreCase("PLACE")) {
                        System.out.println("ID: " + attributes.getValue("id"));
                        place = true;
                    } 
               }

                @Override
                public void endElement(String uri, String localName,
                        String qName) throws SAXException {

                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {

                    if (place) {
                        System.out.println("Text: " + new String(ch, start, length));
                        place = false;
                    }
                }

            };

            saxParser.parse("/Users/hmg/Desktop/SimpleProtocol.xml", handler);

        } catch (IOException | ParserConfigurationException | SAXException e) {
        }
    }
}