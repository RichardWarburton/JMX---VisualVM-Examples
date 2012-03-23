/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insightfullogic.egvvmplugin;

import com.sun.tools.visualvm.application.Application;
import com.sun.tools.visualvm.core.ui.DataSourceView;
import com.sun.tools.visualvm.core.ui.DataSourceViewProvider;
import com.sun.tools.visualvm.core.ui.DataSourceViewsManager;

/**
 * @author richard
 */
public class TimeUsageViewProvider extends DataSourceViewProvider<Application> {

    private static TimeUsageViewProvider instance =  new TimeUsageViewProvider();
    
    @Override
    protected boolean supportsViewFor(Application x) {
        return true;
    }

    @Override
    protected DataSourceView createView(Application x) {
        return new TimeUsageView(x);
    }
    
    static void register() {
        DataSourceViewsManager.sharedInstance().addViewProvider(instance, Application.class);
    }
    
    static void unregister() {
        DataSourceViewsManager.sharedInstance().removeViewProvider(instance);
    }
    
}
