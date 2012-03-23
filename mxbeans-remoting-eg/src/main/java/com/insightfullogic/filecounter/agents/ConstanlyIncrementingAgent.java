package com.insightfullogic.filecounter.agents;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.insightfullogic.filecounter.beans.OpenFileCounter;

public class ConstanlyIncrementingAgent {

	public static void main(final String[] args) throws Exception {
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		final ObjectName name = new ObjectName("com.insightfullogic.filecounter:type=OpenFileCounter");
		final OpenFileCounter mbean = new OpenFileCounter();
		mbs.registerMBean(mbean, name);

		final String filename = "/dev/null";

		mbean.openFile(filename);
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			final String s = filename + i;
			System.out.println(s);
			mbean.openFile(s);
			Thread.sleep(500);
		}

		System.out.println("Waiting forever...");
		Thread.sleep(Long.MAX_VALUE);
	}

}
