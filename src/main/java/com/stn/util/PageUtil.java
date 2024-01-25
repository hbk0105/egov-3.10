package com.stn.util;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Getter
public class PageUtil {

    /**
     * 전체개수
     */
    private long totalCount;

    /**
     * 한페이지에 나오는 개수
     */
    private long pageSize = 10;

    /**
     * 페이지블럭 개수
     */
    private long pageBlockSize = 10;

    /**
     *  현재 페이지 번호
     */
    private long pageIndex;

    /**
     * 전체페이지 블럭 개수
     */
    private long totalBlockCount;

    /**
     * 시작페이지 번호
     */
    private long startPage;

    /**
     * 종료페이지 번호
     */
    private long endPage;

    /**
     * 페이지 이동시 전달되는 파라미터(쿼리스트링)
     */
    private String queryString;

    private long mysqlStartNumber;

    /**
     * PageUtil
     *
     * @param totalCount 전체 수
     * @param pageSize 한페이지에 나오는 개수
     * @param pageIndex 현재 페이지 번호
     * @param queryString 페이지 이동시 전달되는 파라미터(쿼리스트링)
     * @return PageUtil
     */
    public PageUtil(long totalCount, long pageSize, long pageIndex, String queryString) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.queryString = queryString;
        this.mysqlStartNumber = (pageIndex - 1) * pageSize;
    }

    /**
     * PageUtil
     *
     * @param totalCount 전체 수
     * @param pageSize 한페이지에 나오는 개수
     * @param pageIndex 현재 페이지 번호
     * @param req HttpServletRequest
     * @return PageUtil
     */
    public PageUtil(long totalCount, long pageSize, long pageIndex, HttpServletRequest req) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.queryString = mapToString(reqToMap(req));
        this.mysqlStartNumber = (pageIndex - 1) * pageSize;
    }

    public PageUtil(long totalCount, long pageIndex, String queryString, long pageSize, long pageBlockSize) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.queryString = queryString;
        this.pageBlockSize = pageBlockSize;
        this.pageIndex = pageIndex;
        this.mysqlStartNumber = (pageIndex - 1) * pageSize;
    }

    public String pager() {

        init();

        StringBuilder sb = new StringBuilder();

        long previousPageIndex = startPage > 1 ? startPage - 1 : 1;
        long nextPageIndex = endPage < totalBlockCount ? endPage + 1 : totalBlockCount;

        String addQueryString = "";
        if (queryString != null && queryString.length() > 0) {
            addQueryString = "&" + queryString;
        }

        if(endPage == 0 && totalBlockCount == 0) endPage = 1;

        if(previousPageIndex > 1){
            sb.append(String.format("<a href='?pageIndex=%d%s'>&lt;&lt;</a>", 1, addQueryString));
            sb.append(System.lineSeparator());
            sb.append(String.format("<a href='?pageIndex=%d%s'>&lt;</a>", previousPageIndex, addQueryString));
            sb.append(System.lineSeparator());
        }

        for(long i = startPage; i<= endPage; i++) {
            if (i == pageIndex) {
                sb.append(String.format("<a class='on' href='?pageIndex=%d%s'><strong>%d</strong></a>", i, addQueryString, i));
            } else {
                sb.append(String.format("<a href='?pageIndex=%d%s'>%d</a>", i, addQueryString, i));
            }
            sb.append(System.lineSeparator());
        }

        if(totalBlockCount > pageIndex && totalBlockCount > pageBlockSize){
            sb.append(String.format("<a href='?pageIndex=%d%s'>&gt;</a>", nextPageIndex, addQueryString));
            sb.append(System.lineSeparator());
            sb.append(String.format("<a href='?pageIndex=%d%s'>&gt;&gt;</a>", totalBlockCount, addQueryString));
            sb.append(System.lineSeparator());
        }


        return sb.toString();
    }

    private void init() {

        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageSize < 1) {
            pageSize = 1;
        }

        totalBlockCount = totalCount / pageSize + (totalCount % pageSize > 0 ? 1 : 0);

        startPage = ((pageIndex - 1) / pageBlockSize) * pageBlockSize + 1;

        endPage = startPage + pageBlockSize - 1;
        if (endPage > totalBlockCount) {
            endPage = totalBlockCount;
        }
    }

    /**
     *
     * @param req
     * @return HashMap
     */
    public static HashMap<String,String> reqToMap(HttpServletRequest req){
        HashMap<String,String> param = new HashMap<String,String>();
        Enumeration<?> parameter = req.getParameterNames();
        while(parameter.hasMoreElements()){
            String key = (String)parameter.nextElement();
            String value = req.getParameter(key);
            if(StringUtils.isNotBlank(value) && !key.equals("pageIndex")) {
                param.put(key, value);
            }
        }
        return param;
    }

    /**
     * @param obj
     * @return String
     */
    // TODO: Map To String
    @SuppressWarnings("rawtypes")
    public static String mapToString(HashMap<String,String> obj){
        Set keySet = obj.keySet();
        Iterator keys = keySet.iterator();
        String parameters = "";
        while (keys.hasNext()) {
            String paramKey = (String) keys.next();
            String paramValue = (String) obj.get(paramKey);
            if (parameters.equals("")) {
                parameters += paramKey + "=" + paramValue;
            } else {
                parameters += "&" + paramKey + "=" + paramValue;
            }
        }

        return parameters;
    }
}