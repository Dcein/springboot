package dcein.springboot.demo.mybatis.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "train_schedule")
public class TrainSchedule implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SCHEDULE_NAME")
    private String scheduleName;

    @Column(name = "SCHEDULE_EXECUTE_TIME")
    private String scheduleExecuteTime;

    @Column(name = "SERVER_IP")
    private String serverIp;

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return SCHEDULE_NAME
     */
    public String getScheduleName() {
        return scheduleName;
    }

    /**
     * @param scheduleName
     */
    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    /**
     * @return SCHEDULE_EXECUTE_TIME
     */
    public String getScheduleExecuteTime() {
        return scheduleExecuteTime;
    }

    /**
     * @param scheduleExecuteTime
     */
    public void setScheduleExecuteTime(String scheduleExecuteTime) {
        this.scheduleExecuteTime = scheduleExecuteTime;
    }

    public void setServerIp(String serverIp){this.serverIp = serverIp;}

    public String getServerIp(){ return serverIp;}

}