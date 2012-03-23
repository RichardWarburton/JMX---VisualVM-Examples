package com.insightfullogic.filecounter.beans;

import java.util.Set;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationBroadcasterSupport;

import com.google.common.collect.Sets;

public class OpenFileCounter extends NotificationBroadcasterSupport implements OpenFileCounterBean {

	private final Set<String> openFiles = Sets.newHashSet();
	private int sequenceNumber;

	public synchronized void openFile(final String s) {
		openFiles.add(s);
		sendAttributeNotification();
	}

	public synchronized void closeFile(final String s) {
		openFiles.remove(s);
		sendAttributeNotification();
	}

	private void sendAttributeNotification() {
		sendNotification(new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(), "file opened or closed",
				"NumberOfOpenFiles", "int", openFiles.size() - 1, openFiles.size()));
	}

	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		final String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };

		final String name = AttributeChangeNotification.class.getName();
		final String description = "An attribute of this MBean has changed";
		final MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
		return new MBeanNotificationInfo[] { info };
	}

	public int getNumberOfOpenFiles() {
		return openFiles.size();
	}

	public String[] currentlyOpenFiles() {
		return openFiles.toArray(new String[] {});
	}

}
