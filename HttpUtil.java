import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;


/**
 * @author phd
 * @version 1.0
 * @date 2020/10/29 14:18
 */
public class HttpUtil {

    private static final Logger logger = Logger.getLogger("HttpUtil");

    /**
     * @param url 请求路径
     * @param method 请求方法
     * @param contentType 参数类型
     * @param Params 请求数据体
     * @return response 就是返回结果
     * */
    public static String sendRequest(final String url, final String Params, final String contentType, final String method)
            throws IOException {
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        final StringBuilder strBuilder = new StringBuilder();
        String response = "";
        try {
            URL httpUrl = null; // HTTP URL类 用这个类来创建连接
            // 创建URL
            httpUrl = new URL(url);
            // 建立连接
            final HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            if ("POST".equals(method)) {
                conn.setRequestMethod("POST");
            } else {
                conn.setRequestMethod("GET");
            }
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charset", "utf-8");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setUseCaches(false);// 设置不要缓存
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(5000);// 读超时
            conn.setConnectTimeout(5000);// 连接超时
            conn.connect();
            // POST请求
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(Params);
            out.flush();
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String lines;
            while ((lines = reader.readLine()) != null) {
                strBuilder.append(lines);
            }
            response = strBuilder.toString();
            reader.close();
            // 断开连接
            conn.disconnect();
        } catch (final Exception e) {
            logger.warning("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
        return response;
    }

}
