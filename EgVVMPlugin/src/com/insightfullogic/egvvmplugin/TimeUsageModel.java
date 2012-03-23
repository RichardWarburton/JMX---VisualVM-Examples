/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insightfullogic.egvvmplugin;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import org.openide.util.Exceptions;

/**
 *
 * @author richard
 */
public class TimeUsageModel extends Thread {

    private static final Logger LOGGER = Logger.getLogger(TimeUsageModel.class.getName());
    
    private List<TimeUsageListener> listeners;
    private MBeanServerConnection conn;

    public TimeUsageModel(MBeanServerConnection conn) {
        listeners = new ArrayList<TimeUsageListener>();
        this.conn = conn;
    }

    public void addListener(TimeUsageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void run() {
        try {
            // Register for custom bean
            String name = ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",name=PS Scavenge";
            final GarbageCollectorMXBean scavenger = ManagementFactory.newPlatformMXBeanProxy(conn, name, GarbageCollectorMXBean.class);

            while (true) {
                Thread.sleep(500);
                fireUpdate(scavenger.getCollectionTime());
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void fireUpdate(long tally) {
        for (TimeUsageListener timeUsageListener : listeners) {
            LOGGER.log(Level.INFO, "COLLECTION TIME: "+tally);
            timeUsageListener.update(tally);
        }
    }
}
