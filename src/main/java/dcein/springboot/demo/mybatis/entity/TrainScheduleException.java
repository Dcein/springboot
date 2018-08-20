package dcein.springboot.demo.mybatis.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "train_schedule_exception")
public class TrainScheduleException implements Serializable {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "SCHEDULE_NAME")
    private String scheduleName;

    @Column(name = "SCHEDULE_EXCEPTION_TIME")
    private String scheduleExceptionTime;

    @Column(name = "SERVER_IP")
    private String serverIp;
    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
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
     * @return SCHEDULE_EXCEPTION_TIME
     */
    public String getScheduleExceptionTime() {
        return scheduleExceptionTime;
    }

    /**
     * @param scheduleExceptionTime
     */
    public void setScheduleExceptionTime(String scheduleExceptionTime) {
        this.scheduleExceptionTime = scheduleExceptionTime;
    }

    public void setServerIp(String serverIp){this.serverIp = serverIp;}

    public String getServerIp(){ return serverIp;}
}