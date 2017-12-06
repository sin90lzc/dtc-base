package com.dtc.base.zk;

public enum CreateMode {

	PERSISTENT, PERSISTENT_SEQUENTIAL, EPHEMERAL, EPHEMERAL_SEQUENTIAL, PERSISTENT_WITH_TTL, PERSISTENT_SEQUENTIAL_WITH_TTL, CONTAINER;

	public org.apache.zookeeper.CreateMode toCuratorCreateMode() {
		switch (this) {
		case PERSISTENT:
			return org.apache.zookeeper.CreateMode.PERSISTENT;
		case PERSISTENT_SEQUENTIAL:
			return org.apache.zookeeper.CreateMode.PERSISTENT_SEQUENTIAL;
		case EPHEMERAL:
			return org.apache.zookeeper.CreateMode.EPHEMERAL;
		case EPHEMERAL_SEQUENTIAL:
			return org.apache.zookeeper.CreateMode.EPHEMERAL_SEQUENTIAL;
		case PERSISTENT_WITH_TTL:
			return org.apache.zookeeper.CreateMode.PERSISTENT_WITH_TTL;
		case PERSISTENT_SEQUENTIAL_WITH_TTL:
			return org.apache.zookeeper.CreateMode.PERSISTENT_SEQUENTIAL_WITH_TTL;
		case CONTAINER:
			return org.apache.zookeeper.CreateMode.CONTAINER;
		}
		return null;
	}

}
