package com.web.common.cntrl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class CommonXmlParsing {
	
	 public static void parsing(String respXML, String nodeNm ) {
		 
		 try{
			 
		 
			   //DocumentBuilderFactory 생성
			    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        factory.setNamespaceAware(true);
		        DocumentBuilder builder;
		        Document doc = null;
			    
			    InputSource is = new InputSource(new StringReader(respXML));
		        builder = factory.newDocumentBuilder();
			    
		        doc = builder.parse(is);
		        
		        
		        // 결과 코드 갖고 오기위한 내용 
		        StringBuilder nameSpace = new StringBuilder(
		        doc.getDocumentElement().getPrefix() != null ? doc.getDocumentElement().getPrefix() + ":" : "");
		        
		        String resultCode = "";
		        String resultMessge = "";
		        Node nodes= doc.getElementsByTagName(nameSpace + "result").item(0).getFirstChild();
		        resultCode = nodes.getTextContent();
		        System.out.println("resultCode : " + resultCode); // 결과
		        
		        // 성공
		        if("SUCCESS".equals(resultCode)){
		        	
		        	nodes = doc.getElementsByTagName(nameSpace + "gsbStatus").item(0).getFirstChild();
		        	resultMessge = nodes.getTextContent();
		            System.out.println("resultMessge : " + resultMessge); // 결과
		        	
		        }else{
		        	
		        	nodes = doc.getElementsByTagName(nameSpace + "reason").item(0).getFirstChild();
		        	resultMessge = nodes.getTextContent();
		            System.out.println("resultMessge : " + resultMessge); // 결과
		        }
		        
		        
		       // meeting 미팅 리스트
		       // meetingAttendeeHistory  참석자
			   // creatMeething  미팅 생성
		       // iCalendarURL 수정/삭제
		       // 미팅 정보 
		       if(!"".equals(nodeNm) && "creatMeething".equals(nodeNm)){
		    	   
		        	NodeList nodeList = doc.getDocumentElement().getElementsByTagNameNS("*", nodeNm);
		        	parsing(nodeList);
		        	nodeList = doc.getDocumentElement().getElementsByTagNameNS("*", "iCalendarURL");
		        	parsing(nodeList);
		        	
		       }else if(!"".equals(nodeNm) && "meeting".equals(nodeNm)){
		        	
		    	   NodeList nodeList = doc.getDocumentElement().getElementsByTagNameNS("*", nodeNm);
		           parsing(nodeList);
		           nodeList = doc.getDocumentElement().getElementsByTagNameNS("*", "matchingRecords");
		           parsing(nodeList);
		           
		       }else{
		    	   
		    	   NodeList nodeList = doc.getDocumentElement().getElementsByTagNameNS("*", nodeNm);
		        parsing(nodeList);
		    	   
		       }
		         
		        
		        
		        
	       
		 }catch(Exception e){
			 e.printStackTrace();
		 }  
	       
	 }

	public static void parsing(NodeList nodeList ){
		
		for (int i = 0; i < nodeList.getLength(); i++) {
    		NodeList child = nodeList.item(i).getChildNodes();
    		for (int j = 0; j < child.getLength(); j++) {
    			Node node = child.item(j);
    			System.out.println("현재 노드 이름 : " + node.getNodeName());
    			System.out.println("현재 노드 타입 : " + node.getNodeType());
    			System.out.println("현재 노드 값 : " + node.getTextContent());
    			System.out.println("현재 노드 네임스페이스 : " + node.getPrefix());
    			System.out.println("현재 노드의 다음 노드 : " + node.getNextSibling());
    			System.out.println(" i :::: " + i);
    			System.out.println();
    		}
    	}
		
		
	}
	
}
