package xmlParser_rev1;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class helloworld {
	
	private static Document parseXmlFile(String fileName){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(fileName);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void parseDocument(Document doc, ReviewPICO r){
		int i;
		NodeList nL;
		
		nL = doc.getElementsByTagName("CRIT_PARTICIPANTS");
		//r.reviewP = printNode(nL.item(0), "");
		String test = printNode(nL.item(0), "");
		System.out.println(test);
		
		
		nL = doc.getElementsByTagName("CRIT_INTERVENTIONS");
		printNode (nL.item(0), "");
		/*nL = doc.getElementsByTagName("CRIT_OUTCOMES");
		printNode (nL.item(0) );
		nL = doc.getElementsByTagName("INCLUDED_CHAR");
		for (i = 0; i < nL.getLength(); i++)
		    printNode (nL.item(i) );		
		nL = doc.getElementsByTagName("COMPARISON");
		for (i = 0; i < nL.getLength(); i++)
		    printNode (nL.item(i) );
		    
		    */
	}

	private String printNode(Node rootNode, String temp) {
		if (rootNode.getNodeValue() != null) {
	         temp = temp.concat(rootNode.getNodeValue().trim());
	         
		}
		//else
			//System.out.println("blah");
	    NodeList nL = rootNode.getChildNodes();
	    for (int i = 0; i < nL.getLength(); i++) {
	        printNode(nL.item(i), temp);
	    }
	    System.out.println(temp);
	    System.out.println(temp.endsWith("\n"));
	    return temp;
	}
	
	public static void main(String[] args){

		ReviewPICO reviewPICO = new ReviewPICO("", "", "");;
		String xmlFile = "CD010352.xml";
		
		//need to fix this -- add address of object, not instance
		helloworld letsdothis = new helloworld();
		
		letsdothis.parseDocument(parseXmlFile(xmlFile), reviewPICO);
		System.out.println(reviewPICO.reviewP);

		
	}
}

class ReviewPICO {
	public ReviewPICO(String string, String string2, String string3) {
	}
	String reviewP;
	String reviewIC;
	String reviewO;
}

class Studies {
	String studyP;
	String studyIC;
	String studyO;
}
