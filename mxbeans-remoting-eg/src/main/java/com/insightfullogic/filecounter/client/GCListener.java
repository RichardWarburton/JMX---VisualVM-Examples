package com.insightfullogic.filecounter.client;

import javax.management.Notification;
import javax.management.NotificationListener;

public class GCListener implements NotificationListener {

	public void handleNotification(final Notification notification, final Object handback) {
		System.out.println("GC: " + notification);
	}

}
