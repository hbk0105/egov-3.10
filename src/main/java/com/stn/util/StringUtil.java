package com.stn.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	protected Logger log = LogManager.getLogger(this.getClass());

    /**
    * 지정한문자의 앞부분 일부만 출력하는 함수
    * @param str
    * @param len
    * @param str2
    * @return
    * @throws Exception
    */
    public static String getStrCut(String str, int len, String str2) {

        try {
            if (len > str.length()) return str;
            else return str.substring(0, len - 1) + str2;
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    /**
    * 문자열의 넙값 검사를 한다.
    * <BR>문자열이 null 또는 white space인 경우에는 "참"을 반환한다.
    */
    public static boolean isNull(String str) {
        return (str == null || "null".equals(str) || "".equals(str));
    }

    /**
    * 문자열의 넙값 검사를 한다.
    * <BR>문자열이 null 또는 white space인 경우에는 "거짓"을 반환한다.
    */
    public static boolean isNotNull(String str) {
        return !(str == null || "".equals(str));
    }

    /**
    * 문자열이 null인 경우에는 whiteSpace를 반환한다.
    */
    public static String nullToEmptyString(String string) {
        return isNull(string) ? "" : string;
    }

    /**
    * 문자열이 null인 경우에는 지정한 문자 반환한다.
    */
    public static String nullToEmptyString(String string, String str) {
        return isNull(string) ? str : string;
    }

    /**
     * 대용량 데이터컴럼을 데이터베이스로 부터 꺼내온다
     * DAOUtil.getClobText(rs.getCharacterStream("content"));
     *
     * @param r
     * @return
     * @throws IOException
     */
    public static String getClobText(Reader r) {

    	Logger log = LogManager.getLogger();

        Reader rd = null;
        StringBuffer sb = new StringBuffer();
        char[] buf = new char[1024];
        int readcnt;
        try {
            rd = r;

            while ((readcnt = rd.read(buf, 0, 1024)) != -1) {
                sb.append(buf, 0, readcnt);
            }
        } catch (IOException e) {
            return "";
        } catch (Exception e) {
            return "";
        } finally {
            try {
                rd.close();
            } catch (Exception e) {
            	log.error(e.getMessage());
            }
        }

        return sb.toString();
    }

    /**
    * 목록의 제목이 max보다 클경우 max 크기만큼만 잘라서 반환한다.
    */
    public synchronized static String formatTitle(String s, int max) {
        if (s.length() <= max) return s;

        String tmp = null;
        byte bTmp[] = null;
        String rets = "";

        for (int i = 0, k = 0; i < s.length(); i++) {
            tmp = s.substring(i, i + 1);
            bTmp = tmp.getBytes();
            if (bTmp.length > 1) {
                rets += tmp;
                k += 2;
            } else {
                rets += tmp;
                k++;
            }

            if (max <= k) break;
        }

        return rets + "...";
    }

    /**
    * 문자열을 변환한다.
    */
    public static String replace(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();

        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }

        result.append(str.substring(s));
        return result.toString();
    }

    /**
    * 문자열 해당코드로 변환한다..
    */
    private static String encodeText(String str, String encode, String charsetName) {
        String result = null;

        try {

        	//null일경우 truu 반환
        	if(isNull(str)){
        		result = new String(str.getBytes(encode), charsetName);
        	}

            //result = isNull(str) ? null : new String(str.getBytes(encode), charsetName);
        } catch (UnsupportedEncodingException e) {
        	return null;
        }

        return result;
    }

    /**
    * 문자열을 euc-kr에서 8859_1로 디코딩 한다.
    */
    public static String decode(String str) {
        return encodeText(str, "euc-kr", "8859_1");
    }

    /**
    * Date 객체의 날짜를 pattern의 형태로 반환한다.
    */
    public static String getPatternDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
    * 문자열을 8859_1에서 euc-kr로 인코딩 한다.
    */
    public static String encode(String str) {
        return encodeText(str, "8859_1", "euc-kr");
    }

    /**
     * 원본문자에서 패턴으로 불리하여 인텍스에 해당하는 값 추출
     * 사용처 : 전화번호,주민번호, 우편번호등...
     * @param org
     * @param pattern
     * @param idx
     * @return
     */
    public static String getTokenStr(String org, String pattern, int idx) {
        String[] arr = null;
        try {
            if (arr == null) arr = org.split(pattern);
            return arr[idx];
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * HTML 처리를 해준다
     * @param value
     * @return
     */
    public static String getContent(String value) {
    	if (value == null || value.trim().equals("")) {
			return "";
		}

		String returnValue = value;

		returnValue = returnValue.replaceAll("&", "&amp;");
		returnValue = returnValue.replaceAll("<", "&lt;");
		returnValue = returnValue.replaceAll(">", "&gt;");
		returnValue = returnValue.replaceAll("\"", "&#34;");
		returnValue = returnValue.replaceAll("\'", "&#39;");
		return returnValue;
    }

    /**
     * HTML 처리를 해준다
     * @param strContent
     * @return
     */
    public static String setContent(String strContent) {
        String htmlstr = strContent;
        //if(strContent!=null){
        //	htmlstr = strContent.replaceAll("\n", "<br>");
        //}
        String convert = new String(replaceStr(htmlstr, "<", "&lt;"));
        convert = replaceStr(convert, ">", "&gt;");
        convert = replaceStr(convert, "\"", "&quot;");
        convert = replaceStr(convert, "&nbsp;", "&amp;nbsp;");
        return convert;
    }

    /**
     * Preview용 HTML 처리를 해준다
     * @param strContent
     * @return
     */
    public static String getContentPreview(String strContent) {

        String convert = new String(replaceStr(strContent, "&lt;", "<"));
        convert = replaceStr(convert, "&gt;", ">");
        convert = replaceStr(convert, "&quot;", "\"");
        convert = replaceStr(convert, "&amp;nbsp;", "&nbsp;");
        convert = convert.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

        convert = convert.replaceAll("\n", "<br>");

        return convert;
    }

    /**
     *
     * @param strContent
     * @return
     */
    public static String getContent2(String strContent) {

        String convert = new String(replaceStr(strContent, "&lt;", "<"));
        convert = replaceStr(convert, "&gt;", ">");
        convert = replaceStr(convert, "&quot;", "\"");
        convert = replaceStr(convert, "&amp;nbsp;", "&nbsp;");
        convert = replaceStr(convert, "&amp;", "&");
        //convert = convert.replaceAll("\r\n", "<br>");

        return convert;
    }

    /**
     *
     * @param strContent
     * @return
     */
    public static String convertXssToHtml(String strContent) {

        String convert = new String(replaceStr(strContent, "&lt;", "<"));
        convert = replaceStr(convert, "&gt;", ">");
        convert = replaceStr(convert, "&quot;", "\"");
        convert = replaceStr(convert, "&amp;nbsp;", "&nbsp;");
        convert = replaceStr(convert, "&amp;", "&");
        //convert = convert.replaceAll("\r\n", "<br>");

        return convert;
    }

    /**
     * 스트링내의 임의의 문자열을 새로운 문자열로 대치하는 메소드
     *
     * @param source    스트링
     * @param before    바꾸고자하는 문자열
     * @param after     바뀌는 문자열
     * @return 변경된 문자열
     */
    public static String replaceStr(String source, String before, String after) {
        int i = 0;
        int j = 0;
        StringBuffer sb = new StringBuffer();

        while ((j = source.indexOf(before, i)) >= 0) {
            sb.append(source.substring(i, j));
            sb.append(after);
            i = j + before.length();
        }

        sb.append(source.substring(i));
        return sb.toString();
    }

    public static String numberCommaFormat(int price) {
        DecimalFormat df = new DecimalFormat("#,##0");

        return df.format(price);
    }

    /*   public static String stringFirstUpper(String data){
       	String transString = data.substring(0,1);
       	transString = transString.toUpperCase();
       	transString += data.substring(1);

       	return transString;
       }*/
    public static String getContentReplace(String strContent) {
        String htmlstr = strContent;
        String convert = new String(replaceStr(htmlstr, ":",""));
        convert = convert.replace("*","");
        convert = convert.replace("?","");
        return convert;
    }

    /**
     *  해당 문자열이 숫자인지 체크
     *  출처: https://zzznara2.tistory.com/430
     * @param s
     * @return
     */
	public static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
     * 비속어 사용 필터링.
     *
     * @param data
     * @return
     */
	private static String abuseStr = "10새,10새기,10새리,10세리,10쉐이,10쉑,10스,10쌔,10쌔기,10쎄,10알,10창,10탱,18것,18넘,18년,18노,18놈,18뇬,18럼,18롬"
			+ ",18새,18새끼,18색,18세끼,18세리,18섹,18쉑,18스,18아,ㄱㅐ,ㄲㅏ,ㄲㅑ,ㄲㅣ,ㅅㅂㄹㅁ,ㅅㅐ,ㅆㅂㄹㅁ,ㅆㅍ,ㅆㅣ,ㅆ앙,ㅍㅏ,凸,갈보,갈보년"
			+ ",강아지,같은년,같은뇬,개같은,개구라,개년,개놈,개뇬,개대중,개독,개돼중,개랄,개보지,개뻥,개뿔,개새,개새기,개새끼,개새키,개색기,개색끼"
			+ ",개색키,개색히,개섀끼,개세,개세끼,개세이,개소리,개쑈,개쇳기,개수작,개쉐,개쉐리,개쉐이,개쉑,개쉽,개스끼,개시키,개십새기,개십새끼,개쐑"
			+ ",개씹,개아들,개자슥,개자지,개접,개좆,개좌식,개허접,걔새,걔수작,걔시끼,걔시키,걔썌,걸레,게색기,게색끼,광뇬,구녕,구라,구멍,그년,그새끼"
			+ ",냄비,놈현,뇬,눈깔,뉘미럴,니귀미,니기미,니미,니미랄,니미럴,니미씹,니아배,니아베,니아비,니어매,니어메,니어미,닝기리,닝기미,대가리,뎡신"
			+ ",도라이,돈놈,돌아이,돌은놈,되질래,뒈져,뒈져라,뒈진,뒈진다,뒈질,뒤질래,등신,디져라,디진다,디질래,딩시,따식,때놈,또라이,똘아이,똘아이,뙈놈"
			+ ",뙤놈,뙨넘,뙨놈,뚜쟁,띠바,띠발,띠불,띠팔,메친넘,메친놈,미췬,미췬,미친,미친넘,미친년,미친놈,미친새끼,미친스까이,미틴,미틴넘,미틴년,미틴놈"
			+ ",바랄년,병자,뱅마,뱅신,벼엉신,병쉰,병신,부랄,부럴,불알,불할,붕가,붙어먹,뷰웅,븅,븅신,빌어먹,빙시,빙신,빠가,빠구리,빠굴,빠큐,뻐큐,뻑큐,뽁큐"
			+ ",상넘이,상놈을,상놈의,상놈이,새갸,새꺄,새끼,새새끼,새키,색끼,생쑈,세갸,세꺄,세끼,섹스,쇼하네,쉐,쉐기,쉐끼,쉐리,쉐에기,쉐키,쉑,쉣,쉨,쉬발"
			+ ",쉬밸,쉬벌,쉬뻘,쉬펄,쉽알,스패킹,스팽,시궁창,시끼,시댕,시뎅,시랄,시발,시벌,시부랄,시부럴,시부리,시불,시브랄,시팍,시팔,시펄,신발끈,심발끈"
			+ ",심탱,십8,십라,십새,십새끼,십세,십쉐,십쉐이,십스키,십쌔,십창,십탱,싶알,싸가지,싹아지,쌉년,쌍넘,쌍년,쌍놈,쌍뇬,쌔끼,쌕,쌩쑈,쌴년,썅,썅년"
			+ ",썅놈,썡쇼,써벌,썩을년,썩을놈,쎄꺄,쎄엑,쒸벌,쒸뻘,쒸팔,쒸펄,쓰바,쓰박,쓰발,쓰벌,쓰팔,씁새,씁얼,씌파,씨8,씨끼,씨댕,씨뎅,씨바,씨바랄,씨박"
			+ ",씨발,씨방,씨방새,씨방세,씨밸,씨뱅,씨벌,씨벨,씨봉,씨봉알,씨부랄,씨부럴,씨부렁,씨부리,씨불,씨붕,씨브랄,씨빠,씨빨,씨뽀랄,씨앙,씨파,씨팍,씨팔"
			+ ",씨펄,씸년,씸뇬,씸새끼,씹같,씹년,씹뇬,씹보지,씹새,씹새기,씹새끼,씹새리,씹세,씹쉐,씹스키,씹쌔,씹이,씹자지,씹질,씹창,씹탱,씹알,씹팔,씹할,씹헐"
			+ ",아가리,아갈,아갈이,아갈통,아구창,아구통,아굴,얌마,양넘,양년,양놈,엄창,엠병,여물통,염병,엿같,옘병,옘빙,오입,왜년,왜놈,욤병,육갑,은년,을년"
			+ ",이년,이새끼,이새키,이스끼,이스키,임마,자슥,잡것,잡넘,잡년,잡놈,저년,저새끼,접년,젖밥,조까,조까치,조낸,조또,조랭,조빠,조쟁이,조지냐,조진다"
			+ ",조찐,조질래,존나,존나게,존니,존만,존만한,좀물,좁년,좆,좁밥,좃까,좃또,좃만,좃밥,좃이,좃찐,좆같,좆까,좆나,좆또,좆만,좆밥,좆이,좆찐,좇같,좇이"
			+ ",좌식,주글,주글래,주데이,주뎅,주뎅이,주둥아리,주둥이,주접,주접떨,죽고잡,죽을래,죽통,쥐랄,쥐롤,쥬디,지랄,지럴,지롤,지미랄,짜식,짜아식,쪼다"
			+ ",쫍빱,찌랄,창녀,캐년,캐놈,캐스끼,캐스키,캐시키,탱구,팔럼,퍽큐,호로,호로놈,호로새끼,호로색,호로쉑,호로스까이,호로스키,후라들,후래자식,후레"
			+ ",후뢰,씨ㅋ발,ㅆ1발,씌발,띠발,띄발,뛰발,띠ㅋ발,뉘뮈";
	private static String[] abuseArr = abuseStr.split(",");
    public static String abusefilter(String data) {
    	String returnStr = data;
    	for(int i=0; i<abuseArr.length; i++) {
    		returnStr = returnStr.replaceAll(abuseArr[i], "****");
    	}
    	return returnStr;
    }

    /**
     * XSS 방지 처리.
     *
     * @param data
     * @return
     */
    public static String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }

        String ret = data;

        ret = ret.replaceAll("(?i)<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("(?i)</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

        ret = ret.replaceAll("(?i)<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("(?i)</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

        ret = ret.replaceAll("(?i)<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("(?i)</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

        ret = ret.replaceAll("(?i)<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("(?i)</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

        ret = ret.replaceAll("(?i)<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("(?i)</(F|f)(O|o)(R|r)(M|m)", "&lt;form");


        ret = ret.trim();

        return ret;
    }
    public static String unscript2(String data) {
        String ret = data;
        // ret = ret.replaceAll("(?i)\\b(a|A)(l|L)(e|E)(r|R)(t|T)\\b", "");

        // 2023-06-01 웹 취약점 조치
        ret = ret.replaceAll("<(I|i)(F|f)(A|a)(M|m)(E|e)", "&lt;iframe");
        ret = ret.replaceAll("</(I|i)(F|f)(A|a)(M|m)(E|e)", "&lt;iframe");

        ret = ret.replaceAll("(&#x61|&#x61;)(&#x6c|&#x6c;)(&#x65|&#x65;)(&#x72|&#x72;)(&#x74|&#x74;)", ""); // alert
        ret = ret.replaceAll("(&#x41|&#x41;)(&#x4c|&#x4c;)(&#x45|&#x45;)(&#x52|&#x52;)(&#x54|&#x54;)", ""); // ALERT

        ret = ret.replaceAll("(&#x6a|&#x6a;)(&#x61|&#x61;)(&#x76|&#x76;)(&#x61|&#x61;)(&#x73|&#x73;)(&#x63|&#x63;)(&#x72|&#x72;)(&#x69|&#x69;)(&#x70|&#x70;)(&#x74|&#x74;)", ""); // javascript
        ret = ret.replaceAll("(&#x4a|&#x4a;)(&#x41|&#x41;)(&#x56|&#x56;)(&#x41|&#x41;)(&#x53|&#x53;)(&#x43|&#x43;)(&#x52|&#x52;)(&#x49|&#x49;)(&#x50|&#x50;)(&#x54|&#x54;)", ""); // JAVASCRIPT

        ret = ret.replaceAll("(&#60|&#60;|<)", "&lt;");
        ret = ret.replaceAll("(&#62|&#62;|>)", "&gt;");

        /* 2024-06-21 XSS 취약점 - on으로 시작하는 이벤트 조치 */
        //ret = ret.replaceAll("(?i)\\s*on\\w+\\s*=\\s*(\"[^\"]*\"|'[^']*'|[^\\s>]+)", "");
        ret = htmlXssFileter(ret);

        return ret;
    }


    public static String htmlXssFileter(String htmlString) {
        // 정규 표현식을 사용하여 XSS 공격에 취약한 요소들을 제거합니다.
        Pattern pattern = Pattern.compile("(?i)<(script|style|svg|details|applet|audio|basefont|bgsound|blink|body|embed|frame|frameset|head|html|ilayer|img|layer|link|meta|object|plaintext|style|title|video|xml)[^>]*>[\\s\\S]*?</\\1>|javascript:|data:|on\\w+\\s*=\\s*(['\"]?)\\s*");
        Matcher matcher = pattern.matcher(htmlString);
        String sanitizedHtml = matcher.replaceAll("");

        return sanitizedHtml;
    }

    public static String clobToString(Clob data) {
        StringBuffer strOut = new StringBuffer();
        String str = "";
        BufferedReader br = null;
        int i = 0;
        try {
            if (data == null) {
                return "";
            }

            br = new BufferedReader(data.getCharacterStream());
            while ((str = br.readLine()) != null) {
                if(i > 0){
                    strOut.append("\r\n");
                }
                strOut.append(str);
                i++;
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

        return strOut.toString();
    }
}
