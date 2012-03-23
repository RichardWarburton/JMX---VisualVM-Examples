package com.insightfullogic.filecounter.client;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationListener;

public class FileChangeListener implements NotificationListener {

	public void handleNotification(final Notification notification, final Object handback) {
		if (notification instanceof AttributeChangeNotification) {
			final AttributeChangeNotification acn = (AttributeChangeNotification) notification;
			System.out.println("File Counter now: " + acn.getNewValue());
		}
	}

}
