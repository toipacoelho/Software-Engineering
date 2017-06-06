package es.dashboard;

import es.consumer.Consumer;
import es.consumer.Observer;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
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

/**
 * Live updating line chart.
 *
 * @author coelho
 */
@ManagedBean
@SessionScoped
public class LiveLineChartBean implements Serializable {

    private LineChartModel liveLineModel;
    //private LinkedList<ConsumerRecord<String, String>> events = null;
    private ConcurrentLinkedDeque<ConsumerRecord<String, String>> events = null;
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
    }

    public LineChartModel getLiveLineModel() {
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
    
    public void warn() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Watch out."));
    }
    
    public void saveMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage("Successful",  "Your message: ") );
        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }

}
