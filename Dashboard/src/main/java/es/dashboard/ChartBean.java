
package es.dashboard;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author toipacoelho
 */

@ManagedBean
public class ChartBean implements Serializable {
 
    private LineChartModel lineModel1;
    private final static String QUEUE_NAME = "Vital_jacket";
    List<String> points;
     
    @PostConstruct
    public void init() {
        createLineModels();
    }
 
    public LineChartModel getLineModel1() {
        return lineModel1;
    }
      
    private void createLineModels() {
        lineModel1 = initLinearModel();
        lineModel1.setTitle("Linear Chart");
        lineModel1.setLegendPosition("e");
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
    }
     
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
        
        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);
 
        model.addSeries(series1);
         
        return model;
    }
    
    public void consume(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Received '" + message + "'");
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (IOException ex) {
            Logger.getLogger(ChartBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(ChartBean.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
     
 
}
