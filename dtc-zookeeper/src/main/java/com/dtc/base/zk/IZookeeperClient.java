package com.dtc.base.zk;

import java.util.List;

public interface IZookeeperClient {

	/**
	 * 关闭client
	 */
	public void close();
	
	/**
	 * 创建路径并设置数据
	 * @param mode
	 * @param path
	 * @param data
	 */
	public void createForPath(CreateMode mode,String path,byte[] data);
	
	/**
	 * 创建路径
	 * @param mode
	 * @param path
	 */
	public void createForPath(CreateMode mode,String path);
	
	
	/**
	 * 获取路径下的node名称列表
	 * @param path
	 * @return
	 */
	public List<String> getChildren(String path);
	
	/**
	 * 获取路径下的数据
	 * @param path
	 * @return 以UTF-8编码方式编码node的数据返回
	 */
	public String getData(String path);
	
	/**
	 * 
	 * @param path
	 * @param data 以UTF-8编码的字符串
	 */
	public void setData(String path,String data);
	
	/**
	 * 往path node中写数据
	 * @param path
	 * @param data
	 */
	public void setRawData(String path,byte[] data);
	
	/**
	 * 获取path node中的原始字节数组
	 * @param path
	 * @return
	 */
	public byte[] getRawData(String path);
	
	/**
	 * 判断路径是否存在
	 * @param path
	 * @return
	 */
	public boolean isExists(String path);
}
