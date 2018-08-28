package dcein.springboot.demo.canal.canalMonitroHandle;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import dcein.springboot.demo.configuration.DruidStartConfig;
import dcein.springboot.demo.constants.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Auther: DingCong
 * @Description: candel数据订阅与消费处理中心类
 * @@Date: Created in 10:45 2018/8/28
 */
public class CanalHandleCenter {

    private static Logger logger = LoggerFactory.getLogger(CanalHandleCenter.class);

    /**
     * canal订阅与消费处理器
     */
    public static void SubscribeAndConsumer(){

        //step1.将要访问canal服务端的目标名,服务IP和服务端口号
        String canan_destination = SystemConstants.CANAL_DESTINATION;
        String canal_ip = SystemConstants.HJF_CANAL_IP;
        Integer canal_port = SystemConstants.CANAL_SERVER_PORT;

        //step2.从连接器工厂获取连接器
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(canal_ip,canal_port), canan_destination, "", "");

        //step2
        int batchSize = 1024;
        int emptyCount = 0;
        try {

            //创建连接
            connector.connect();
            logger.info("[====客户端与服务端建立链接成功！===]");

            //订阅：发消息给scanal-server 通过socket的方式。这边是判断，如果filter不为空，才发送订阅消息
            // canal服务端就像一个存放数据库解析日志的大容器,需要哪些数据去消费即可,不需要重启服务,后台可配置化
            connector.subscribe(".*\\..*"); //配置订阅的服务
            connector.rollback();

            //心跳链接
            while (true) {

                //获取订阅数据，建立连接和进行数据订阅之后，就可以开始进行binlog数据的获取了
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据

                //获取批次id
                long batchId = message.getId();
                int size = message.getEntries().size();

                //拿到message后，需要进行判断batchId，如果batchId=-1或者binlog大小为0
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    printEntry(message.getEntries());
                }

                // 提交确认
                connector.ack(batchId);
            }
        } finally {
            connector.disconnect();
        }

    }

    /**
     * 实体打印
     * @param entrys
     */
    private static void printEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------&gt; before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------&gt; after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    /**
     * 列打印
     * @param columns
     */
    private static void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    public static void main(String[] args) {
        SubscribeAndConsumer();
    }

}
