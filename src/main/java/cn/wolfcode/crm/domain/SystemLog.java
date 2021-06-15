package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter@Getter
public class SystemLog {
    private Long id;

    private Long opUser_id;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    private Date opTime;

    private String opIp;

    private String function;

    private String params;

}