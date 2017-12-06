package com.dtc.base.zk;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class ZookeeperConfig {

	private String serverList;

	private int baseSleepTimeMilliseconds = 1000;

	private int maxRetries = 3;

	private int maxSleepTimeMilliseconds = 3000;

	private String namespace;

	private long sessionTimeoutMilliseconds = 3000;

	private long connectionTimeoutMilliseconds = 60000;
	
	public static ZookeeperConfig.Builder builder(){
		return new ZookeeperConfig.Builder();
	}
	
	public static class Builder{
		
		private ZookeeperConfig config;
		
		public Builder(){
			this.config=new ZookeeperConfig();
		}
		
		public Builder baseSleepTimeMilliseconds(int baseSleepTimeMilliseconds){
			config.baseSleepTimeMilliseconds=baseSleepTimeMilliseconds;
			return this;
		}
		
		public Builder maxRetries(int maxRetries){
			config.maxRetries=maxRetries;
			return this;
		}
		
		public Builder maxSleepTimeMilliseconds(int maxSleepTimeMilliseconds){
			config.maxSleepTimeMilliseconds=maxSleepTimeMilliseconds;
			return this;
		}
		
		public Builder namespace(String namespace){
			config.namespace=CharMatcher.anyOf("/").trimFrom(namespace);
			return this;
		}
		
		public Builder sessionTimeoutMilliseconds(long sessionTimeoutMilliseconds){
			config.sessionTimeoutMilliseconds=sessionTimeoutMilliseconds;
			return this;
		}
		
		public Builder connectionTimeoutMilliseconds(long connectionTimeoutMilliseconds){
			config.connectionTimeoutMilliseconds=connectionTimeoutMilliseconds;
			return this;
		}
		
		private Builder serverList(String serverList){
			config.serverList=serverList;
			return this;
		}
		
		private ZookeeperConfig build(){
			Preconditions.checkArgument(!Strings.isNullOrEmpty(config.serverList), "zookeeper config must provides serverList");
//			Preconditions.checkArgument(!Strings.isNullOrEmpty(config.namespace), "zookeeper config must provides namespace");
			return config;
		}
		
		public ZookeeperConfig build(String serverList){
			this.serverList(serverList);
//			this.namespace(namespace);
			return this.build();
		}
		
		
	}

	public String getServerList() {
		return serverList;
	}

	public int getBaseSleepTimeMilliseconds() {
		return baseSleepTimeMilliseconds;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public int getMaxSleepTimeMilliseconds() {
		return maxSleepTimeMilliseconds;
	}

	public String getNamespace() {
		return namespace;
	}

	public long getSessionTimeoutMilliseconds() {
		return sessionTimeoutMilliseconds;
	}

	public long getConnectionTimeoutMilliseconds() {
		return connectionTimeoutMilliseconds;
	}
	

}
