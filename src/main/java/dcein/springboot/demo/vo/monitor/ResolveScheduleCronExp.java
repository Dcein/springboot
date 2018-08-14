package dcein.springboot.demo.vo.monitor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 10:40 2018/8/14
 */
@Getter@Setter@ToString
public class ResolveScheduleCronExp {

    /**
     * cron表达式一天中运行的时间间隔
     */
    private List<Date> dateList;


    /**
     * cron表达式执行的时间间隔
     */
    private long timeGaps;
}
