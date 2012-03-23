/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insightfullogic.egvvmplugin;

import com.sun.tools.visualvm.application.Application;
import com.sun.tools.visualvm.charts.ChartFactory;
import com.sun.tools.visualvm.charts.SimpleXYChartDescriptor;
import com.sun.tools.visualvm.charts.SimpleXYChartSupport;
import com.sun.tools.visualvm.core.ui.DataSourceView;
import com.sun.tools.visualvm.core.ui.components.DataViewComponent;
import com.sun.tools.visualvm.core.ui.components.DataViewComponent.DetailsView;
import com.sun.tools.visualvm.tools.jmx.JmxModel;
import com.sun.tools.visualvm.tools.jmx.JmxModelFactory;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import org.openide.util.Utilities;

/**
 *
 * @author richard
 */
public class TimeUsageView extends DataSourceView implements TimeUsageListener {

    private static final Logger LOGGER = Logger.getLogger(TimeUsageView.class.getName());
    
    private static final String IMAGE_PATH = "com/sun/tools/visualvm/coredump/resources/coredump.png"; // NOI18N
    
    private SimpleXYChartSupport chart;
    
    public TimeUsageView(Application app) {
        super(app, "GC Time Usage Graph", new ImageIcon(Utilities.loadImage(IMAGE_PATH, true)).getImage(), 60, false);
    }
    
    @Override
    protected DataViewComponent createComponent() {
                //Data area for master view:
        JEditorPane generalDataArea = new JEditorPane();
        generalDataArea.setBorder(BorderFactory.createEmptyBorder(14, 8, 14, 8));

        //Master view:
        DataViewComponent.MasterView masterView = new DataViewComponent.MasterView
                ("PS Scavenge Graph View", null, generalDataArea);

        //Configuration of master view:
        DataViewComponent.MasterViewConfiguration masterConfiguration = 
                new DataViewComponent.MasterViewConfiguration(false);
        
        DataViewComponent dvc = new DataViewComponent(masterView, masterConfiguration);

        JPanel panel = new JPanel();
        
        SimpleXYChartDescriptor description = SimpleXYChartDescriptor.decimal(0, 20, 100, 1d, false, 1000);
        description.setChartTitle("Time used by PS Scavenge");
        description.setDetailsItems(new String[2]);
        description.addLineItems("Time used");
        description.setDetailsItems(new String[]{"Time current"});
        description.setXAxisDescription("<html>At Time</html>");
        description.setYAxisDescription("<html>Time used by PS Scavenge</html>");
        chart = ChartFactory.createSimpleXYChart(description);

        panel.setLayout(new BorderLayout());
        panel.add(chart.getChart(),BorderLayout.CENTER);
        
        DetailsView dv = new DetailsView("PS Scavenge Time Usage", "lalal", DataViewComponent.TOP_LEFT, panel, null);
        dvc.addDetailsView(dv, DataViewComponent.TOP_LEFT);
        
        initTimeUsageModel();
        
        return dvc;
    }
    
    private void initTimeUsageModel() {
        final JmxModel jmxModel = JmxModelFactory.getJmxModelFor((Application)super.getDataSource());
        final MBeanServerConnection conn = jmxModel.getMBeanServerConnection();
        final TimeUsageModel model = new TimeUsageModel(conn);
        model.addListener(this);
        model.start();
    }

    @Override
    public void update(long tally) {
        if (chart != null) {
            long[] values = { tally };
            chart.addValues(System.currentTimeMillis(), values);
        }
    }
    
}
