package xmlParser_rev1;

import java.io.IOException;
import java.util.regex.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class cochraneXML {

	Document doc;
	
    String finalString;  // concatenate all output into one string --- need to separate into PICO objects later
    
    String reviewP;
	
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			doc = builder.parse("CD010352.xml");
//			doc = builder.parse("CD001191.xml");
//			doc = builder.parse("CD003559.xml");  // CHAR_PARTICIPANTS and INCLUDED_CHAR will result in extremely long lines
//			doc = builder.parse("CD003564.xml");  // INCLUDED_CHAR will result in extremely long lines
//			doc = builder.parse("CD003793.xml");  // CHAR_PARTICIPANTS and INCLUDED_CHAR will result in extremely long lines
//			doc = builder.parse("CD005307.xml");  // INCLUDED_CHAR will result in extremely long lines
//			doc = builder.parse("CD005381.xml");
//			doc = builder.parse("CD006922.xml");  // CHAR_PARTICIPANTS and INCLUDED_CHAR will result in extremely long lines
//			doc = builder.parse("CD006924.xml");  // INCLUDED_CHAR will result in extremely long lines
//			doc = builder.parse("CD007949.xml");  // CHAR_PARTICIPANTS and INCLUDED_CHAR will result in extremely long lines
//			doc = builder.parse("CD008345.xml");  // INCLUDED_CHAR will result in extremely long lines

			//get the root elememt
			doc.getDocumentElement();
//		    doc.getElementsByTagName("COCHRANE_REVIEW"); 
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseDocument(){
		NodeList nL;
        String[] searchWords = { 
        		                "CRIT_PARTICIPANTS",
        		                "CHAR_PARTICIPANTS",
        		                "CRIT_INTERVENTIONS",
        		                "CHAR_INTERVENTIONS",
                                "CRIT_OUTCOMES",
                                "CHAR_OUTCOMES",
                                "INCLUDED_CHAR",
                                "EXCLUDED_CHAR",
                                "AWAITING_CHAR",
                                "ONGOING_CHAR",
                                "COMPARISON"
                                };

        for(int idx = 0; idx < searchWords.length; idx++) {
    		nL = doc.getElementsByTagName(searchWords[idx]);
    		finalString = "";
    		for (int i = 0; i < nL.getLength(); i++)
    		    printNode (nL.item(i) );		
    	    if (finalString.length() != 0 ) {
//    	        System.out.println( "    !!! " + searchWords[idx] );   
    	        System.out.println( finalString );
    	    }
        	
        }
	}

	private void printNode(Node rootNode) {
        String longString="", tmpString="";
        
		if (rootNode.getNodeValue() != null) {
			
			longString = rootNode.getNodeValue();
			 
		    //regular expression pattern to find lines that are not whitespace
		    Pattern checkRegex = Pattern.compile("[^(\\s*\n)][^(\t*\n)][^(\r*\n)][^(\b*\n)][^(\f*\n)]");
		    Matcher regexMatcher = checkRegex.matcher( longString );
		     
		     // trim whitespace and \n from middle of string
			if ( regexMatcher.find() ){
    	    	    tmpString = longString.trim();
	        	    tmpString = tmpString.replace("\n", "");
//	        	    System.out.println(tmpString);                  
//	        	    System.out.println("   ***  " + finalString);   
	        	    finalString = finalString + tmpString + " ";
		    }
		}
	    NodeList nL = rootNode.getChildNodes();
	    for (int i = 0; i < nL.getLength(); i++) {
	        printNode(nL.item(i));
	    }
	}
	
	public static void main(String[] args){
		cochraneXML cochraneXML_ins = new cochraneXML();
		
		//parse the xml file and get the dom object
		cochraneXML_ins.parseXmlFile();
		cochraneXML_ins.parseDocument();
	}
}
