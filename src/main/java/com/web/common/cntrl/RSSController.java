package com.web.common.cntrl;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.*;


/*
https://mkyong.com/spring-mvc/spring-3-mvc-and-rss-feed-example/
https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/rss-feed-view.html

* */

@Controller
public class RSSController {
    @RequestMapping(value="/rssfeed", method = RequestMethod.GET)
    public ModelAndView getFeedInRss(HttpServletResponse response) {

        response.setContentType("text/html;charset=UTF-8");
        ModelAndView mav = new ModelAndView();
        try{

            List<RSSMessage> items = new ArrayList<RSSMessage>();

            RSSMessage content  = new RSSMessage();
            content.setTitle("Spring Tutorial");
            content.setUrl("http://www.tutorialspoint/spring");
            content.setSummary("CDATA[Spring tutorial summary <ㅋㅋㅋ> '감사함둥");
            content.setCreatedDate(new Date());
            items.add(content);

            RSSMessage content2  = new RSSMessage();
            content2.setTitle("Spring MVC");
            content2.setUrl("http://www.tutorialspoint/springmvc");
            content2.setSummary("Spring MVC tutorial summary. @#$%^&** html <> !!@ .");
            content2.setCreatedDate(new Date());
            items.add(content2);
            mav.setViewName("rssViewer");
            mav.addObject("feedContent", items);
        }catch (Exception e){
            e.printStackTrace();
        }


        return mav;
    }

    @GetMapping(value = "/apitest")
    public String callapihttp() throws Exception {


        String url = "http://localhost:8089/rssfeed";
        // parsing할 url 지정 ( API 키 포함해서 이다 )


        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(url);
        doc.getDocumentElement().normalize();
        System.out.println("Root element : " + doc.getDocumentElement().getNodeName());

        // 파싱 방법 1
        NodeList list = doc.getElementsByTagName("item");
        ArrayList<Map<String,Object>> datas = new ArrayList<>();
        for(int i=0;i<list.getLength();i++) {
            Node child = list.item(i); //item(i) : nodelist에서 i번째 노드 추출. <book> 태그 한세트
            //<book>의 자식노드들 추출
            NodeList infos = child.getChildNodes(); //bookid, title,author, price -> infos 에 들어감.
            if(infos.getLength()==0) { //공란 읽지 않기 위해 추가한 if문
                continue;
            }
            Map<String,Object> map = new HashMap();
            for(int j=0;j<infos.getLength();j++) {
                Node info = infos.item(j);
                String tagName = info.getNodeName(); //getnodename():태그명 추출
                map.put(tagName,info.getTextContent());
                //System.out.println(vo);
            }
            datas.add(map);
        }

        System.out.println("### datas --> " + datas);

        System.out.println();
        System.out.println();

        // 파싱 방법 2
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        XPathExpression expr = xpath.compile("//channel/item");
        NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList child = nodeList.item(i).getChildNodes();
            for (int j = 0; j < child.getLength(); j++) {
                Node node = child.item(j);
                System.out.println("현재 노드 이름 : " + node.getNodeName());
                System.out.println("현재 노드 타입 : " + node.getNodeType());
                System.out.println("현재 노드 값 : " + node.getTextContent());
                System.out.println("현재 노드 네임스페이스 : " + node.getPrefix());
                System.out.println("현재 노드의 다음 노드 : " + node.getNextSibling());
                System.out.println("");
            }
        }

        return "jsonView";

    }

    // getTagValue 메소드 인데 , 이 부분은 TagName을 가져옴으로써 nlList에 저장을 시키는 부분

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null) {
            return null;
        }
        return nValue.getNodeValue();
    }



}