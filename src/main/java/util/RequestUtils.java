package util;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 请求工具类
 *
 * @author kz
 */
public class RequestUtils {

    public static String[] keywords = {"mobile", "android",
            "symbianos", "iphone", "wp\\d*", "windows phone",
            "mqqbrowser", "nokia", "samsung", "midp-2",
            "untrusted/1.0", "windows ce", "blackberry", "ucweb",
            "brew", "j2me", "yulong", "coolpad", "tianyu", "ty-",
            "k-touch", "haier", "dopod", "lenovo", "huaqin", "aigo-",
            "ctc/1.0", "ctc/2.0", "cmcc", "daxian", "mot-",
            "sonyericsson", "gionee", "htc", "zte", "huawei", "webos",
            "gobrowser", "iemobile", "wap2.0", "WAPI"};

    /**
     * 获取完整的请求链接
     *
     * @return
     */
    public static String getCompleteRequestUrl(HttpServletRequest request) {
        StringBuilder completeUrl = new StringBuilder();
        completeUrl.append(request.getScheme()).append("://").append(request.getServerName()).append(":")
                .append(request.getServerPort()).append(request.getContextPath()).append(request.getServletPath())
                .append("?");
        if (isGetMethod(request)) {
            completeUrl.append(request.getQueryString());
        }
        return completeUrl.toString();
    }

    /**
     * 判断是否为wap请求
     *
     * @param request
     * @return
     */
    public static boolean isWapRequest(HttpServletRequest request) {
        String agent = request.getHeader("user-agent");
        String agentcheck = agent.trim().toLowerCase();
        Pattern pf = Pattern.compile("wp\\d*");
        Matcher mf = pf.matcher(agentcheck);
        boolean isWap = false;
        if (agentcheck != null && (agentcheck.indexOf("windows nt") == -1 && agentcheck
                .indexOf("Ubuntu") == -1)
                || (agentcheck.indexOf("windows nt") > -1 && mf.find())) {
            for (int i = 0; i < keywords.length; i++) {
                Pattern p = Pattern.compile(keywords[i]);
                Matcher m = p.matcher(agentcheck);
                //排除 苹果桌面系统 和ipad 、iPod  
                if (m.find() && agentcheck.indexOf("ipad") == -1
                        && agentcheck.indexOf("ipod") == -1
                        && agentcheck.indexOf("macintosh") == -1) {
                    isWap = true;
                    break;
                }
            }
        }
        return isWap;
    }

    /**
     * 获取http请求IP
     *
     * @return
     */
    public static String getHttpIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 是否为get请求
     *
     * @param request
     * @return
     */
    public static boolean isGetMethod(HttpServletRequest request) {
        if (StringUtils.equals(request.getMethod(), "GET")) {
            return true;
        }
        return false;
    }

    /**
     * 获取未带参数链接
     *
     * @param request
     * @return
     */
    public static String getRequestUrlNotWithParam(HttpServletRequest request) {
        StringBuilder requestUrl = new StringBuilder();
        requestUrl.append(getRoot(request)).append(request.getServletPath());
        return requestUrl.toString();
    }

    public static String getRoot(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName()
                + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort())
                + request.getContextPath();
    }

    /**
     * 是否为微信浏览器
     *
     * @param request
     * @return
     */
    public static boolean isWeixin(HttpServletRequest request) {
        String ua = request.getHeader("user-agent")
                .toLowerCase();
        if (ua.indexOf("micromessenger") > 0) {
            return true;
        }
        return false;
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (cookie != null && name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
