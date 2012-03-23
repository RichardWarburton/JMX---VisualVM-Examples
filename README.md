
Overview
========

This repo contains several examples all related to JMX and JVisualVM.  They shouldn't be used ina production environment
and are primarily hosted here as a self reference

mxbeans-remoting-eg
-------------------

This is a maven project with three related things in:

1. com.insightfullogic.filecounter.agents - A pair of agents generating some custom MXBean data
1. com.insightfullogic.filecounter.beans - custom beans
1. com.insightfullogic.filecounter.client - a commandline client for listening to information from these beans and an example jvm statistic.

If you want to run the agents and monitor their GC behaviour don't forget to pass the following properties to the JVM:

    -Dcom.sun.management.jmxremote.port = 9999
    -Dcom.sun.management.jmxremote.authenticate = false
    -Dcom.sun.management.jmxremote.ssl = false

EgVVMPlugin
-----------

This is an example netbeans project with a visualvm plugin in.  The plugin simply displays a graph with the amount of time spent in PS Scavenge in.
In order to monitor an application, you'll need to run it with the aforementioned parameters, otherwise you'll get a nasty exception in your face.
