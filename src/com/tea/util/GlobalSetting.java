package com.tea.util;




import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GlobalSetting {
	/**
	 * ͼƬURL��root
	 */
	private static String imageRootUrl;

	static{
		Properties properties = new Properties();
		InputStream is = null;
		try {
			
			is = GlobalSetting.class.getClassLoader().getResourceAsStream("/com/tea/util/globalSettings.properties");
			
			
			properties.load(is);
			
			imageRootUrl = (String)properties.get("ImageUrl");
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("properties error(图片地址错误):" + e.getMessage());
		}
		finally{
			if(null != is){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ��ȡͼƬ�ĸ�·��
	 * @return
	 */
	public static String getImageRootUrl(){
		return imageRootUrl;
	}
}
