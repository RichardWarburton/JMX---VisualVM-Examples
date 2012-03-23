package com.insightfullogic.filecounter.client;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class ConsoleRemoteClient {

	public static void main(final String[] args) throws Exception {
		final JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
		final JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		final MBeanServerConnection conn = jmxc.getMBeanServerConnection();

		// Register for custom bean
		final FileChangeListener listener = new FileChangeListener();
		final ObjectName mbeanName = new ObjectName("com.insightfullogic.filecounter:type=OpenFileCounter");
		conn.addNotificationListener(mbeanName, listener, null, null);

		final GarbageCollectorMXBean scavenger = getGCBean(conn, "PS Scavenge");
		while (true) {
			Thread.sleep(500);
			System.out.println("Scavenger used: " + scavenger.getCollectionTime());
		}
	}

	static GarbageCollectorMXBean getGCBean(final MBeanServerConnection conn, final String forName) throws Exception {
		return ManagementFactory.newPlatformMXBeanProxy(conn, ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",name=" + forName,
				GarbageCollectorMXBean.class);
	}

}
