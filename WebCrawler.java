
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

	// address
	private static final String URL = "https://www.jaist.ac.jp/index.html";
	
	
	public static String GetUrl(String inUrl) {
		
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(inUrl);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			String temp = "";
			while ((temp = reader.readLine()) != null) {
				// System.out.println(temp);
				sb.append(temp);
			}
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static List<String> GetMatcher(String str, String url) {
		List<String> result = new ArrayList<String>();
		Pattern p = Pattern.compile(url);// 获取网页地址
		Matcher m = p.matcher(str);
		while (m.find()) {
			System.out.println(m.group(1));
			result.add(m.group(1));
		}
		return result;
	}

	// 下载图片
	private static void Download(List<String> listSrc) throws IOException {
		//try {
			// 开始时间
			for(String url : listSrc){
				System.out.println("!!"+url);
			}
			Date begindate = new Date();
			for (String url : listSrc) {
				
				// 开始时间
				Date begindate2 = new Date();
				String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
				URL uri = new URL(url);
				InputStream in = uri.openStream();
				FileOutputStream fo = new FileOutputStream(new File(imageName));// 文件输出流
				byte[] buf = new byte[1024];
				int length = 0;
				System.out.println("开始下载:" + url);
				while ((length = in.read(buf, 0, buf.length)) != -1) {
					fo.write(buf, 0, length);
				}
				// 关闭流
				in.close();
				fo.close();
				System.out.println(imageName + "下载完成");
				// 结束时间
				Date overdate2 = new Date();
				double time = overdate2.getTime() - begindate2.getTime();
				System.out.println("耗时：" + time / 1000 + "s");
			}
			Date overdate = new Date();
			double time = overdate.getTime() - begindate.getTime();
			System.out.println("总耗时：" + time / 1000 + "s");
//		} catch (Exception e) {
//			System.out.println("下载失败");
//		}
	}

	public static void main(String[] args) throws Exception {
	
		String str = GetUrl(URL);
		List<String> ouput = GetMatcher(str, "src=\"(.+?)\"");

		
		Download(ouput);
	}

}