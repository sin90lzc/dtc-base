package com.dtc.base.zk.curator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import com.dtc.base.zk.CreateMode;
import com.dtc.base.zk.IZookeeperClient;
import com.dtc.base.zk.ZookeeperConfig;
import com.dtc.base.zk.exception.DTCZookeeperException;
import com.dtc.base.zk.exception.ZookeeperErrorCode;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;

public class CuratorZookeeperClient implements IZookeeperClient {

	private ZookeeperConfig zookeeperConfig;

	private CuratorFramework client;

	private CuratorZookeeperClient(ZookeeperConfig zookeeperConfig) {
		this.zookeeperConfig = zookeeperConfig;
		Builder builder = CuratorFrameworkFactory.builder().connectString(zookeeperConfig.getServerList())
				.retryPolicy(new ExponentialBackoffRetry(zookeeperConfig.getBaseSleepTimeMilliseconds(),
						zookeeperConfig.getMaxRetries(), zookeeperConfig.getMaxSleepTimeMilliseconds()))
				.namespace(zookeeperConfig.getNamespace());
		client = builder.build();
		client.start();
		try {
			client.blockUntilConnected(zookeeperConfig.getMaxSleepTimeMilliseconds() * zookeeperConfig.getMaxRetries(),
					TimeUnit.MILLISECONDS);
			if (!client.getZookeeperClient().isConnected()) {
				throw new KeeperException.OperationTimeoutException();
			}
		} catch (final Exception ex) {
			throw new DTCZookeeperException(ZookeeperErrorCode.ZOOKEEPER_INIT_ERR, ex);
		}

	}

	public static IZookeeperClient connect(ZookeeperConfig zookeeperConfig) {
		CuratorZookeeperClient client = new CuratorZookeeperClient(zookeeperConfig);
		return client;
	}

	@Override
	public void close() {
		client.close();
	}

	@Override
	public void createForPath(CreateMode mode, String path, byte[] data) {
		try {
			client.create().creatingParentsIfNeeded().withMode(mode.toCuratorCreateMode()).forPath(path, data);
		} catch (Exception e) {
			throw new DTCZookeeperException(ZookeeperErrorCode.ZOOKEEPER_CREATE_PATH_ERR, e);
		}
	}

	@Override
	public void createForPath(CreateMode mode, String path) {
		createForPath(mode, path, null);
	}

	@Override
	public List<String> getChildren(String path) {
		try {
			return client.getChildren().forPath(path);
		} catch (Exception e) {
			throw new DTCZookeeperException(ZookeeperErrorCode.ZOOKEEPER_GETCHILDREN_ERR, e);
		}
	}

	@Override
	public String getData(String path) {
		byte[] rawData = getRawData(path);
		if (rawData == null || rawData.length == 0) {
			return null;
		}
		return new String(rawData, Charsets.UTF_8);
	}

	@Override
	public void setData(String path, String data) {
		if (Strings.isNullOrEmpty(data)) {
			return;
		}
		setRawData(path, data.getBytes(Charsets.UTF_8));
	}

	@Override
	public void setRawData(String path, byte[] data) {
		try {
			client.setData().forPath(path, data);
		} catch (Exception e) {
			throw new DTCZookeeperException(ZookeeperErrorCode.ZOOKEEPER_SETDATA_ERR, e);
		}
	}

	@Override
	public byte[] getRawData(String path) {
		try {
			return client.getData().forPath(path);
		} catch (Exception e) {
			throw new DTCZookeeperException(ZookeeperErrorCode.ZOOKEEPER_GETDATA_ERR, e);
		}
	}

	@Override
	public boolean isExists(String path) {
		try {
			Stat stat = client.checkExists().forPath(path);
			if(stat==null){
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new DTCZookeeperException(ZookeeperErrorCode.ZOOKEEPER_CHECKEXISTS_ERR, e);
		}
	}

}
