package com.web.common.cntrl;

import java.net.*;
import java.io.*;

/**************************************************************************
#
#   LstSummaryMeeting.java
#
#   This Java program HTTP POSTs a WebEx XML request function  
#   which queries for the calling user's meetings.  The WebEx XML Service
#   then returns an XML response document listing each meeting.
#
#   Author: Phillip Liu, phillipl@webex.com                                             
#   Date:   1/05/2005													
#																		
#   (c) Copyright 2005 WebEx Communications								
#																		
*****************************************************************************/


public class LstSummaryMeeting
{
    public static void main(String[] args) throws Exception
    {
    	/*
    	
    	 예약된 미팅 리스트
    	 https://developer.cisco.com/docs/webex-xml-api-reference-guide/#!lstsummarymeeting/lstsummarymeeting
    	  
    	 LstOpenSession
		모든 호스트가 현재 사이트에서 진행중인 모든 서비스 유형의 모든 세션을 검색 할 수 있습니다. 
		사용자는 특정 서비스 유형의 열린 세션을 나열 할 수 있습니다. 서비스 유형이 지정되지 않으면 리턴 된 세션은 해당 서비스 유형에 따라 그룹화됩니다.
    	  https://developer.cisco.com/docs/webex-xml-api-reference-guide/#!lstopensession/lstopensession
    	  
    	  GetMeeting
		GetMeeting 지정된 회의에 대한 자세한 정보를 반환합니다.
    	  https://developer.cisco.com/docs/webex-xml-api-reference-guide/#!getmeeting/getmeeting
    	  
    	Lstmeetingattendee 역사
		미팅 주최자 또는 사이트 관리자가 사이트에서 이전에 호스팅 된 미팅 세션에 대한 자세한 참석자 정보를 요청할 수 있습니다.
		참고 호스트는 자신의 미팅 참석자 정보에만 액세스 할 수 있지만 사이트 관리자는 자신의 사이트에서 이전에 호스팅 된 모든 미팅의 참석자 정보에 액세스 할 수 있습니다.
    	https://developer.cisco.com/docs/webex-xml-api-reference-guide/#!lstmeetingattendeehistory/lstmeetingattendeehistory
    	  
    	  
    	LstsupportattendeeHistory
		지원 세션 호스트 또는 사이트 관리자가 사이트에서 이전에 호스팅 된 지원 세션에 대한 자세한 참석자 정보를 요청할 수 있습니다.
		참고 사이트 관리자는 사이트에서 호스팅되는 모든 지원 세션의 참석자 정보를 검색 할 수 있지만 일반 호스트는 자신의 지원 세션의 참석자 정보 만 검색 할 수 있습니다.  
    	https://developer.cisco.com/docs/webex-xml-api-reference-guide/#!lstsupportattendeehistory/lstsupportattendeehistory
    	
    	LstMeetingAttendee
		사이트에서 호스팅 된 세션의 참석자 정보를 검색합니다. 세션은 미팅 센터, 교육 센터, 이벤트 센터, 영업 센터 또는 원격 회의 전용 세션을 포함한 모든 WebEx 세션 유형 중 하나 일 수 있습니다. 
		https://developer.cisco.com/docs/webex-xml-api-reference-guide/#!lstmeetingattendee/lstmeetingattendee 
		
    	*/
    	// http://sangs.my.webex.com/WBXService/XMLService
    	// https://developer.cisco.com/docs/webex-xml-api-reference-guide/#lstsummarysession/lstsummarysession
        String siteName = "sangs";                  // WebEx site name
        String xmlURL = "WBXService/XMLService";    // XML API URL
        String siteID = "sangs";                     // Site ID
        //String partnerID = "C4441e71794bcca3e62d0a3196e60afabe22ec8c6a1e5681c944a8fc4f359f26d";                  // Partner ID
        String webExID = "hbk0105@sangs.co.kr";                // Host username
        String password = "Sangs#1234";               // Host password
        String email = "hbk0105@sangs.co.kr";
        String xmlServerURL = "https://"+siteName+".webex.com/"+xmlURL;
        
        // HTTPS 요청 소스
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        // connect to XML server
        URL urlXMLServer = new URL(xmlServerURL);
        
        // URLConnection supports HTTPS protocol only with JDK 1.4+
        URLConnection urlConnectionXMLServer = urlXMLServer.openConnection();
        urlConnectionXMLServer.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
        urlConnectionXMLServer.setDoOutput(true);
        
		String meetinKey = "572967137";
        //String meetinKey = "574880518";
        // send request
	    PrintWriter out = new PrintWriter(urlConnectionXMLServer.getOutputStream());
	    String reqXML = " <?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
	    
	    // 미팅 리스트 
	 
	       
	    reqXML += "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
        reqXML += " xmlns:serv=\"http://www.webex.com/schemas/2002/06/service\"";
        reqXML += " xsi:schemaLocation=\"http://www.webex.com/schemas/2002/06/service\">\r\n";
        reqXML += "<header>\r\n";
        reqXML += "<securityContext>\r\n";
        reqXML += "<webExID>" + webExID + "</webExID>\r\n";
        reqXML += "<password>" + password + "</password>\r\n";
        reqXML += " <siteName>"+siteName+"</siteName>\r\n";
        reqXML += "</securityContext>\r\n";
        reqXML += "</header>\r\n";
        reqXML += "<body>\r\n";
        reqXML += "<bodyContent xsi:type=\"java:com.webex.service.binding.meeting.LstsummaryMeeting\">";
        reqXML += "<listControl>";
        reqXML += "<startFrom>1</startFrom>";
        reqXML += "<maximumNum>5</maximumNum>";
        reqXML += "<listMethod>OR</listMethod>";
        reqXML += "</listControl>";
        reqXML += "<order>";
        reqXML += "<orderBy>STARTTIME</orderBy>";
        reqXML += "</order>";
        reqXML += "</bodyContent>\r\n";
        reqXML += "</body>\r\n";
        reqXML += "</serv:message>\r\n";
	    
	    
	    
	    
	    
	    //참석자 정보  
	   /*
	    reqXML += " <serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n";
	    reqXML += " <header>\r\n";
	    reqXML += " <securityContext>\r\n";
	    reqXML += " <webExID>"+webExID+"</webExID>\r\n";
	    reqXML += " <password>"+password+"</password>\r\n";
	    reqXML += " <siteName>"+siteName+"</siteName>\r\n";
	    //reqXML += " <partnerID>9999</partnerID>\r\n";
	    reqXML += " <email>"+email+"</email>\r\n";
	    reqXML += " </securityContext>\r\n";
	    reqXML += " </header>\r\n";
	    
	    reqXML += " <body>\r\n";
	    reqXML += " <bodyContent xsi:type=\"java:com.webex.service.binding.history.LstmeetingattendeeHistory\">\r\n";
	    reqXML += " <startTimeScope>\r\n";
	    reqXML += " <sessionStartTimeStart>09/16/2019 00:00:00</sessionStartTimeStart>\r\n";
	    reqXML += " <sessionStartTimeEnd>09/16/2019 23:59:59</sessionStartTimeEnd>\r\n";
	    reqXML += " </startTimeScope>\r\n";
	    
	    reqXML += " <endTimeScope>\r\n";
	    reqXML += " <sessionEndTimeStart>09/16/2019 00:00:00</sessionEndTimeStart>\r\n";
	    reqXML += " <sessionEndTimeEnd>09/16/2019 23:59:59</sessionEndTimeEnd>\r\n";
	    reqXML += " </endTimeScope>\r\n";
	    
	    reqXML += " <listControl>\r\n";
	    reqXML += " <serv:startFrom>1</serv:startFrom>\r\n";
	    reqXML += " <serv:maximumNum>100</serv:maximumNum>\r\n";
	    reqXML += " <serv:listMethod>OR</serv:listMethod>\r\n";
	    reqXML += " </listControl>\r\n";
	    

	    reqXML += " <order>\r\n";
	    reqXML += " <orderBy>CONFID</orderBy>\r\n";
	    reqXML += " <orderAD>ASC</orderAD>\r\n";
	    reqXML += " </order>\r\n";
	    
	    reqXML += " <inclAudioOnly>TRUE</inclAudioOnly>\r\n";
	    
	    reqXML += " </bodyContent>\r\n";
	    reqXML += " </body>\r\n";
	    reqXML += " </serv:message>\r\n";
	    
	    
	    
	    */
	    
	    
	   // String reqXML = " <?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n";
	    /*
	    reqXML += " <serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:serv=\"http://www.webex.com/schemas/2002/06/service\">\r\n";
	    reqXML += " <header>\r\n";
	    reqXML += " <securityContext>\r\n";
	    reqXML += " <webExID>"+webExID+"</webExID>\r\n";
	    reqXML += " <password>"+password+"</password>\r\n";
	    reqXML += " <siteName>"+siteName+"</siteName>\r\n";
	    reqXML += " <partnerID>9999</partnerID>\r\n";
	    reqXML += " <email>genierose247@naver.com</email>\r\n";
	    reqXML += " </securityContext>\r\n";
	    reqXML += " </header>\r\n";
	    
	    reqXML += " <body>\r\n";
	    reqXML += " <bodyContent xsi:type=\"java:com.webex.service.binding.attendee.LstMeetingAttendee\">\r\n";
	    reqXML += " <meetingKey>573547590</meetingKey>\r\n";
	    reqXML += " </bodyContent>\r\n";
	    reqXML += " </body>\r\n";
	    reqXML += " </serv:message>\r\n";
	    
	    
	    // SUCCESS - CODE [ 회의 정보 ] 
	    
	    reqXML += " <serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:serv=\"http://www.webex.com/schemas/2002/06/service\">\r\n";
	    reqXML += " <header>\r\n";
	    reqXML += " <securityContext>\r\n";
	    reqXML += " <siteName>"+siteName+"</siteName>\r\n";
	    reqXML += " <webExID>"+webExID+"</webExID>\r\n";
	    reqXML += " <password>"+password+"</password>\r\n";
	    reqXML += " </securityContext>\r\n";
	    reqXML += " </header>\r\n";
	    
	    reqXML += " <body>\r\n";
	    reqXML += " <bodyContent xsi:type=\"java:com.webex.service.binding.meeting.GetMeeting\">\r\n";
	    reqXML += " <meetingKey>570285373</meetingKey>\r\n";
	    reqXML += " </bodyContent>\r\n";
	    reqXML += " </body>\r\n";
	    reqXML += " </serv:message>\r\n";
	    
	    
	    // 회의 삭제 [ SUCCESS ]
	   /* reqXML += " <serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n";
	    reqXML += " <header>\r\n";
	    reqXML += " <securityContext>\r\n";
	    reqXML += " <siteName>"+siteName+"</siteName>\r\n";
	    reqXML += " <webExID>"+webExID+"</webExID>\r\n";
	    reqXML += " <password>"+password+"</password>\r\n";
	    reqXML += " </securityContext>\r\n";
	    reqXML += " </header>\r\n";
	    
	    reqXML += " <body>\r\n";
	    reqXML += " <bodyContent xsi:type=\"java:com.webex.service.binding.meeting.DelMeeting\">\r\n";
	    reqXML += " <meetingKey>575672485</meetingKey>\r\n";
	    reqXML += " </bodyContent>\r\n";
	    reqXML += " </body>\r\n";
	    reqXML += " </serv:message>\r\n";
	   */
	   
	   
	   // SUCCESS - CODE
	    /*
	    reqXML += " <serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n";
	    reqXML += " <header>\r\n";
	    reqXML += " <securityContext>\r\n";
	    reqXML += "  <siteName>"+siteName+"2</siteName>\r\n";
	    reqXML += " </securityContext>\r\n";
	    
	    
	    reqXML += " </header>\r\n";
	    reqXML += " <body>\r\n";
	    reqXML += " <bodyContent xsi:type=\"java:com.webex.service.binding.ep.GetAPIVersion\"></bodyContent>\r\n";
	    reqXML += " </body>\r\n";
	    reqXML += " </serv:message>\r\n";
	    
	  */
	   
	   
	   // 미팅 생성 프로세스  -SUCCESS
/*	    
	    reqXML += " <serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n";
	    reqXML += " <header>\r\n";
	    reqXML += " <securityContext>\r\n";
	    reqXML += " <siteName>"+siteName+"</siteName>\r\n";
	    reqXML += " <webExID>"+webExID+"</webExID>\r\n";
	    reqXML += " <password>"+password+"</password>\r\n";
	    reqXML += " </securityContext>\r\n";
	    reqXML += "  </header>\r\n";
	    
	    reqXML += " <body>\r\n";
	   
	    reqXML += " <bodyContent xsi:type=\"java:com.webex.service.binding.meeting.CreateMeeting\">\r\n";
	    
	    // 미팅 비밀번호
	    reqXML += " <accessControl>\r\n";
	    reqXML += " <meetingPassword>1234</meetingPassword>\r\n";
	    reqXML += " </accessControl>\r\n";
	    
	    reqXML += " <metaData>\r\n";
	    reqXML += " <confName>미팅 변경2222  Meeting</confName>\r\n";
	    reqXML += " <meetingType>3</meetingType>\r\n";
	    reqXML += " <agenda>bkbkbkbk</agenda>\r\n";
	    reqXML += " </metaData>\r\n";
	    
	    // 참석자 정보 등록
	    reqXML += " <participants>\r\n";
	    reqXML += " 	<maxUserNumber>4</maxUserNumber>\r\n";
	    reqXML += " 		<attendees>\r\n";
	    reqXML += " 			<attendee>\r\n";
	    reqXML += " 				<person>\r\n";
	    reqXML += " 					<name>bkbk</name>\r\n";
	    reqXML += " 					<email>adsfdas@naver.com</email>\r\n";
	    reqXML += " 				</person>\r\n";
	    reqXML += "    				<emailInvitations>true</emailInvitations>\r\n"; // 초대 이메일 발송
	    reqXML += " 			</attendee>\r\n";

	    reqXML += " 		</attendees>\r\n";
	    reqXML += " </participants>\r\n";
	    
		// 참석자 정보 등록 끝
	    
	    reqXML += " <enableOptions>\r\n";
	    reqXML += "  	<chat>true</chat>\r\n";
	    reqXML += "  	<poll>true</poll>\r\n";
	    reqXML += "  	<audioVideo>true</audioVideo>\r\n";
	    reqXML += "  	<supportE2E>true</supportE2E>\r\n";
	    reqXML += "  	<autoRecord>true</autoRecord>\r\n";
	    reqXML += " </enableOptions>\r\n";
	    
	    // 미팅 시간
	    reqXML += " <schedule>\r\n";
	    reqXML += " 	<startDate>09/17/2019 15:57:00</startDate>\r\n";
	    reqXML += "  	<openTime>1000</openTime>\r\n";
	    reqXML += "  	<joinTeleconfBeforeHost>true</joinTeleconfBeforeHost>\r\n";
	    reqXML += "  	<duration>20</duration>\r\n";
	    reqXML += "  	<timeZoneID>50</timeZoneID>\r\n"; // timeZone 50 - 한국
	    reqXML += " </schedule>\r\n";
	    
	    // 회사 정보
	    reqXML += " <telephony>\r\n";
	    reqXML += " 	<telephonySupport>CALLIN</telephonySupport>\r\n";
	    reqXML += "  	<extTelephonyDescription>Call 1-800-555-1234, Passcode 98765</extTelephonyDescription>\r\n";
	    reqXML += " </telephony>\r\n";
	    
	    reqXML += "  </bodyContent>\r\n";
	    reqXML += "  </body>\r\n";
	    reqXML += "  </serv:message>\r\n";
	   
	    */
	    
	    // 미팅 수정 TEST 필요
	   /*
	    reqXML += " <serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n";
	    reqXML += " <header>\r\n";
	    reqXML += " <securityContext>\r\n";
	    reqXML += " <webExID>"+webExID+"</webExID>\r\n";
	    reqXML += " <password>"+password+"</password>\r\n";
	    reqXML += " <siteName>"+siteName+"</siteName>\r\n";
	    //reqXML += " <partnerID>"+partnerID+"</partnerID>\r\n";
	    reqXML += " <email>"+email+"</email>\r\n";
	    reqXML += " </securityContext>\r\n";
	    reqXML += " </header>\r\n";
	    
	    reqXML += " <body>\r\n";
	   
	    reqXML += " <bodyContent xsi:type=\"java:com.webex.service.binding.meeting.SetMeeting\">\r\n";
	    
	    reqXML += " <metaData>\r\n";
	    reqXML += " <confName>BKBKBKzzzzz re name222</confName>\r\n";
	    reqXML += " <meetingType>3</meetingType>\r\n";
	    reqXML += " <agenda>bkbkbkbk</agenda>\r\n";
	    reqXML += " </metaData>\r\n";
	    
	    // 참석자 정보 등록
	    reqXML += " <participants>\r\n";
	    reqXML += " 	<maxUserNumber>4</maxUserNumber>\r\n";
	    reqXML += " 		<attendees>\r\n";
	    reqXML += " 			<attendee>\r\n";
	    reqXML += " 				<person>\r\n";
	    reqXML += " 					<name>gmail</name>\r\n";
	    reqXML += " 					<email>ououou25@gmail.com</email>\r\n";
	    reqXML += " 				</person>\r\n";
	    reqXML += "    				<emailInvitations>true</emailInvitations>\r\n"; // 변경 이메일 발송
	    reqXML += " 			</attendee>\r\n";
	    
	    reqXML += " 			<attendee>\r\n";
	    reqXML += " 				<person>\r\n";
	    reqXML += " 					<name>name</name>\r\n";
	    reqXML += " 					<email>adsfdas@naver.com</email>\r\n";
	    reqXML += " 				</person>\r\n";
	    reqXML += "    					<emailInvitations>true</emailInvitations>\r\n"; 
	    reqXML += " 			</attendee>\r\n";
	    
	    reqXML += " 		</attendees>\r\n";
	    reqXML += " </participants>\r\n";
	    // 참석자 정보 등록 끝	    
	    
	    reqXML += " <enableOptions>\r\n";
	    reqXML += "  	<chat>true</chat>\r\n";
	    reqXML += "  	<poll>true</poll>\r\n";
	    reqXML += "  	<audioVideo>true</audioVideo>\r\n";
	    reqXML += "  	<supportE2E>true</supportE2E>\r\n";
	    reqXML += "  	<autoRecord>true</autoRecord>\r\n";
	    reqXML += " </enableOptions>\r\n";
	    
	    // 미팅 시간
	    reqXML += " <schedule>\r\n";
	    reqXML += " 	<startDate>09/16/2019 16:30:00</startDate>\r\n";
	    reqXML += "  	<openTime>1000</openTime>\r\n";
	    reqXML += "  	<joinTeleconfBeforeHost>true</joinTeleconfBeforeHost>\r\n";
	    reqXML += "  	<duration>20</duration>\r\n";
	    reqXML += "  	<timeZoneID>50</timeZoneID>\r\n"; // timeZone 50 - 한국
	    reqXML += " </schedule>\r\n";
	    
	    
	    reqXML += " <telephony>\r\n";
	    reqXML += " 	<telephonySupport>CALLIN</telephonySupport>\r\n";
	    reqXML += "  	<extTelephonyDescription>Call 1-800-555-1234, Passcode 98765</extTelephonyDescription>\r\n";
	    reqXML += " </telephony>\r\n";
	    
	    // 초대 취소 메일 발송 
	    reqXML += "<remind>\r\n";
	    reqXML += "    <enableReminder>true</enableReminder>\r\n";
	    reqXML += "    <emails>\r\n";
	    reqXML += "        <email>adsfdas@naver.com</email>\r\n";
	    reqXML += "    </emails>\r\n";
	    reqXML += "</remind>\r\n";
	    
	    // 관리자 메일 받기 [ 변경 ]
	    reqXML += "<attendeeOptions>\r\n";
	    reqXML += "    <emailInvitations>true</emailInvitations>\r\n";
	    reqXML += "</attendeeOptions>\r\n";
	    
	    reqXML += " <meetingkey>575933816</meetingkey>\r\n";
	    
	    
	    reqXML += "  </bodyContent>\r\n";
	    reqXML += "  </body>\r\n";
	    reqXML += "  </serv:message>\r\n";
	   */
	    
    
	    out.println(reqXML);
	    out.close();
	    System.out.println("XML Request POSTed to " + xmlServerURL + "\n");        
	    System.out.println(reqXML+"\n");
	    
	    // read response
	    BufferedReader in = new BufferedReader(new
	        InputStreamReader(urlConnectionXMLServer.getInputStream()));
	    String line;
	    String respXML = "";
	    while ((line = in.readLine()) != null) {
	        respXML += line.trim();
	    }
	    in.close();
	    
	    // output response
	    respXML = URLDecoder.decode(respXML,"UTF-8");  
	    System.out.println("\nXML Response\n");        
	    System.out.println(respXML);
	    
	    
	    CommonXmlParsing.parsing(respXML, "meeting");
	    
	    
    
	    
    }
}