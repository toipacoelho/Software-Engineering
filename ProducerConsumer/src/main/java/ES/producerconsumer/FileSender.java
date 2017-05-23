package ES.producerconsumer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author toipacoelho
 */
public class FileSender {

    private final static String QUEUE_NAME = "Vital_jacket";

    public static void main(String[] argv) throws Exception {
        
        File f = new File("../VitalJacket_ECG.tsv");
        List<String> lines = FileUtils.readLines(f, "UTF-8");
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //String message = "Hello World!";
        
        for (String line : lines){
            channel.basicPublish("", QUEUE_NAME, null, line.getBytes("UTF-8"));
            TimeUnit.MILLISECONDS.sleep(800);
        }

        channel.close();
        connection.close();
    }
}
