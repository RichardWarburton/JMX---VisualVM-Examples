package com.insightfullogic.filecounter.beans;

import javax.management.MXBean;

@MXBean
public interface OpenFileCounterBean {

	void openFile(String s);

	void closeFile(String s);

	int getNumberOfOpenFiles();

	String[] currentlyOpenFiles();

}
