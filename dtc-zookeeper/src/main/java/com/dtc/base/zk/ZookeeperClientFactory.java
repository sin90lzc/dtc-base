package com.dtc.base.zk;

import com.dtc.base.zk.curator.CuratorZookeeperClient;

public class ZookeeperClientFactory {

	public static IZookeeperClient useCurator(ZookeeperConfig zookeeperConfig){
		return CuratorZookeeperClient.connect(zookeeperConfig);
	}
	
}
