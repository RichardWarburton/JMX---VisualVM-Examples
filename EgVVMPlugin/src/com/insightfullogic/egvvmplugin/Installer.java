/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insightfullogic.egvvmplugin;

import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        TimeUsageViewProvider.register();
    }

    @Override
    public void uninstalled() {
        TimeUsageViewProvider.unregister();
    }
    
}
