package es.dashboard;

import es.consumer.Consumer;
import es.consumer.Observer;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
import org.apache.log4j.Logger;

/**
 * Live updating line chart.
 *
 * @author coelho
 */
@ManagedBean
@SessionScoped
public class LiveLineChartBean implements Serializable {

    final static Logger logger = Logger.getLogger(LiveLineChartBean.class);

    private LineChartModel liveLineModel;
    //private LinkedList<ConsumerRecord<String, String>> events = null;
    private ConcurrentLinkedDeque<ConsumerRecord<String, String>> events = null;
    private int max = 0;
    private boolean alert = false;
    private static final long serialVersionUID = 1L;
    private Observer obs;

    @PostConstruct
    public void init() {
        //consumer
        obs = new Observer();
        Consumer.getInstance().register(obs);
        events = obs.getList();
        //live chart
        liveLineModel = new LineChartModel();
        liveLineModel.setTitle("Life Jacket ECG");
        //liveLineModel.setLegendPosition("e");
        //series list add
        LineChartSeries life_jacket = new LineChartSeries();
        life_jacket.setLabel("Life Jacket");
        liveLineModel.addSeries(life_jacket);
        life_jacket.setShowMarker(false);
        life_jacket.set(0, 0);
        logger.debug("init chart");
        //logger.info(events.getLast().toString());
    }

    public LineChartModel getLiveLineModel() {
        logger.debug("getLiveLineModel()");

        setAlert(testAlert());

        logger.info("trigger: " + isAlert());

        int size = 0;

        Axis xAxis = liveLineModel.getAxis(AxisType.X);
        Axis yAxis = liveLineModel.getAxis(AxisType.Y);

        for (ChartSeries series : liveLineModel.getSeries()) {
            if (series.getLabel().equals("Life Jacket")) {
                for (ConsumerRecord<String, String> record : events) {
                    if (record.value().equals("ECG")) {
                    } else {
                        double value = Double.parseDouble(record.value());
                        series.set(size, (int) value);
                        size++;
                    }
                }
            }
        }

        xAxis.setMin(0);
        xAxis.setMax(size);
        yAxis.setMin(0);
        yAxis.setMax(250);

        return liveLineModel;
    }

    public ConcurrentLinkedDeque<ConsumerRecord<String, String>> getEvents() {
        return events;
    }

    public void updateEvents() {
        events = obs.getList();
    }

    public List<String> getEventsProcessed() {
        ArrayList<String> array = new ArrayList<>();

        events.forEach(event -> {
            /*
            MsgUtil util = new MsgUtil();
            String message = util.process(event.);
             */
            array.add(event.value());
        });
        return array;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    private boolean testAlert() {
        int i = 1;
        boolean result = false;
        
        if (!events.isEmpty() && events.size()>20) {
            for (Iterator<ConsumerRecord<String, String>> itr = events.iterator(); itr.hasNext();) {
                
                logger.debug("value:" + itr.next().value() + " offset: " + itr.next().offset());
                
                if (i < 20) {
                    i++;
                } else {
                    i = 0;
                    break;
                }
                
                
                if (events.peek().value().equals("ECG")) {
                } else {
                    if (Double.parseDouble(itr.next().value()) > 200) {
                        return true;
                    }
                }
            }

        }
        return result;
    }

}
