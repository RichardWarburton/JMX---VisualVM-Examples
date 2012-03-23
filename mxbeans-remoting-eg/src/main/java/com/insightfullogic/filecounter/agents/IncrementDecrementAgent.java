package com.insightfullogic.filecounter.agents;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.insightfullogic.filecounter.beans.OpenFileCounter;

public class IncrementDecrementAgent {

	public static void main(final String[] args) throws Exception {
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		final ObjectName name = new ObjectName("com.insightfullogic.filecounter:type=OpenFileCounter");
		final OpenFileCounter mbean = new OpenFileCounter();
		mbs.registerMBean(mbean, name);

		final String filename = "/dev/null";

		System.out.println("Opening");
		mbean.openFile(filename);
		for (long l = 0; l < Long.MAX_VALUE; l++) {
			Thread.sleep(500);
			System.out.println("Closing");
			mbean.closeFile(filename);
			Thread.sleep(500);
			System.out.println("Opening");
			mbean.openFile(filename);
		}

		System.out.println("Waiting forever...");
		Thread.sleep(Long.MAX_VALUE);
	}

}
