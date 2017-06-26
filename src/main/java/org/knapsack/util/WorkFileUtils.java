package org.knapsack.util;

import org.apache.commons.lang.StringUtils;

import java.io.File;


/** 项目内常用处理File对象
 * @author yangkun
 *
 */
public class WorkFileUtils {

	private WorkFileUtils(){}
	
	/**
	 * 获得项目classes文件夹的绝对路径对象
	 * 
	 * @return File对象
	 */
	public static File getClassFile() {
		return new File(Thread.currentThread().getContextClassLoader().getResource("").getFile());
	}
	
	/**
	 * 获得项目ROOT根目录对象
	 * 
	 * @return
	 */
	public static File getRootFile() {
		return getClassFile().getParentFile().getParentFile();
	}
	
	/**
	 * 获得项目指定的资源路径的绝对路径，包括处理 classpath:
	 * /WEB-INF/demo.txt = d:/project/name/WEB-INF/demo.tx
	 * classpath:demo.txt   = d:/project/name/WEB-INF/classes/demo.txt
	 * @param resource 指定的路径
	 * @return
	 */
	public static File getFile(String resource) {
		resource = StringUtils.trimToEmpty(resource);
		String path = resource.toLowerCase().indexOf("classpath:") == 0 ? getClassFile().getPath() + "/" + resource.substring(10): getRootFile()+ (resource.indexOf("/") == 0 ? resource : "/"+ resource);
		return resource.isEmpty() ? null : new File(path);
	}

	
}
