package util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * HTTP
 */
public class HttpUtil {

    private static final int SIZE = 1024 * 1024;

    private final static int TIME_OUT = 15;

    public final static String CHARSET_UTF8 = "UTF-8";

    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31";

    public static final String JSON_CONTENT_TYPE = "application/json";

    /**
     * post请求
     * @author wenjl
     * @return
     * @throws IOException
     */
    public static String post(String apiUrl, String jsonData) throws Exception {
        StringBuilder result = new StringBuilder(600*1024);
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setAllowUserInteraction(false);
        con.setUseCaches(false);
        con.setConnectTimeout(TIME_OUT * 1000);
        con.setReadTimeout(TIME_OUT * 1000);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", JSON_CONTENT_TYPE+";charset=" + CHARSET_UTF8);
        con.setRequestProperty("User-Agent", USER_AGENT);
        // 输入参数
        con.getOutputStream().write(jsonData.getBytes(CHARSET_UTF8));
        BufferedReader in = null;
        try{
            in = new BufferedReader(new InputStreamReader(con.getInputStream(),CHARSET_UTF8), SIZE);
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    result.append(line);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }finally{
            if(in != null){
                in.close();
            }
            con.disconnect();//关闭连接
        }
        return result.toString();
    }

    /**
     * 获取二进制文件
     * @author kz
     * @param reqUrl
     * @param host
     * @param referer
     * @return
     * @throws IOException
     */
    public static byte[] getByte(String reqUrl, String host, String referer) throws IOException {
        URL url = new URL(reqUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setAllowUserInteraction(false);
        con.setUseCaches(false);
        con.setConnectTimeout(TIME_OUT * 1000);
        con.setReadTimeout(TIME_OUT * 1000);
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Host", host);
        con.setRequestProperty("Accept-Encoding", "identity");
        //防盗链
        if(StringUtils.isNotEmpty(referer)){
            con.setRequestProperty("Referer", referer);
        }
        InputStream in = null;
        ByteArrayOutputStream outstream = null;
        try {
            in = con.getInputStream();
            outstream = new ByteArrayOutputStream();
            //批量输出
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                outstream.write(buffer, 0, len);
            }
            outstream.flush();
        } catch (Exception e) {
        }finally{
            if(outstream != null){
                outstream.close();
            }
            if(in != null){
                in.close();
            }
        }
        // 关闭流一定要记得。
        return outstream.toByteArray();
    }

}
