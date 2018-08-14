package dcein.springboot.demo.vo.monitor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Auther: DingCong
 * @Description: 解析轮询任务Cron表达式vo
 * @@Date: Created in 14:21 2018/8/12
 */
@Setter
@Getter
@ToString
public class ScheduleAnnotationAttr {

    /**
     * cron表达式
     */
    private String expression;

    /**
     * 方法名
     */
    private String methodName;

}
