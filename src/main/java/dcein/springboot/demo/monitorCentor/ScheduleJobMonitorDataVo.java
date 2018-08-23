package dcein.springboot.demo.monitorCentor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Auther: DingCong
 * @Description: 定时任务监控数据vo
 * @@Date: Created in 9:54 2018/8/21
 */
@Getter@Setter@ToString
public class ScheduleJobMonitorDataVo {

    private String jobName;

    private String jobClassPath;

    private String jobNodeName;

    private Character status;

    private String start;

    private String end;
}
