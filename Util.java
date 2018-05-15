
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

public class Util {

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

	

	// 获取html内容
	public static String getHTML(String srcUrl) throws Exception {
		URL url = new URL(srcUrl);
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line = null;
		StringBuffer buffer = new StringBuffer();
		while ((line = br.readLine()) != null) {
			buffer.append(line);
			buffer.append("\n");
		}
		br.close();
		isr.close();
		is.close();
		return buffer.toString();
	}

	// 获取image url地址
//	public static List<String> getImageURL(String html) {
//		Matcher matcher = Pattern.compile(IMGURL_REG).matcher(html);
//		List<String> list = new ArrayList<>();
//		while (matcher.find()) {
//			list.add(matcher.group());
//		}
//		return list;
//	}
//
//	// 获取image src地址
//	public static List<String> getImageSrc(List<String> listUrl) {
//		List<String> listSrc = new ArrayList<String>();
//		for (String img : listUrl) {
//			Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(img);
//			while (matcher.find()) {
//				listSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
//			}
//		}
//		return listSrc;
//	}

	// 下载图片
	private static void Download(List<String> listImgSrc) {
		try {
			// 开始时间
			Date begindate = new Date();
			
			for (String url : listImgSrc) {
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
		} catch (Exception e) {
			System.out.println("下载失败");
		}
	}
	 public static void sop(Object obj){  
	        System.out.println(obj);  
	    }  
	public static void download(String url,String path){  
        File file= null;  
        File dirFile=null;  
        FileOutputStream fos=null;  
        HttpURLConnection httpCon = null;  
        URLConnection  con = null;  
        URL urlObj=null;  
        InputStream in =null;  
        byte[] size = new byte[1024];  
        int num=0;  
        try {  
            String downloadName= url.substring(url.lastIndexOf("/")+1);  
            dirFile = new File(path);  
            if(!dirFile.exists()){  
                if(dirFile.mkdir()){  
                    if(path.length()>0){  
                        sop("creat document file \""+path.substring(0,path.length()-1)+"\" success...\n");  
                    }  
                }  
            }else{  
            file = new File(path+downloadName);  
            fos = new FileOutputStream(file);  
            if(url.startsWith("http")){  
                urlObj = new URL(url);  
                con = urlObj.openConnection();  
                httpCon =(HttpURLConnection) con;  
                in = httpCon.getInputStream();  
                while((num=in.read(size)) != -1){  
                    for(int i=0;i<num;i++)   
                       fos.write(size[i]);  
                }  
            }  
            }  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally{  
            try {  
                fos.close();  
                in.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  

	public static void main(String[] args) throws Exception {
		String html = getHTML(URL);
	//	List<String> listUrl = getImageURL(html);
		/*
		 * for(String img : listUrl){ System.out.println(img); }
		 */
	//	List<String> listSrc = getImageSrc(listUrl);
		String str = GetUrl(URL);
		List<String> ouput = GetMatcher(str, "src=\"([\\w\\s./:]+?)\"");
		/*
		 * for(String img : listSrc){ System.out.println(img); }
		 */

		Download(ouput);
	}

}