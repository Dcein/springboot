package dcein.springboot.demo.monitorCentor;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import dcein.springboot.demo.constants.SystemConstants;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @Auther: DingCong
 * @Description: 数据库表变动监听类 -- canal监听核心
 * @@Date: Created in 11:11 2018/8/23
 */
public class SimpleCanalClientExample {

    public static void main(String args[]) throws UnknownHostException {

        //step1.创建链接,连接canal服务端
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress(SystemConstants.CANAL_IP,SystemConstants.CANAL_SERVER_PORT), SystemConstants.CANAL_DESTINATION, "", "");

        //step2.获取指定数量的数据和通信最小次数值
        int batchSize = 1000;
        int emptyCount = 0;
        try {

            //创建连接
            connector.connect();
            System.out.println("连接成功");
            connector.subscribe(".*\\..*");
            connector.rollback();

            //循环总量:和canal服务端通信次数
            int totalEmptyCount = 1200;

            //判断通信次数
            while (emptyCount < totalEmptyCount) {

                //获取本次通信信息
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据

                //获取批次id
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
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

    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }


}
