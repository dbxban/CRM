package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter@Getter
public class Employee {
    private Long id;

    private String username;

    private String realname;
    private String password;

    private String tel;

    private String email;

    private Department dept; //关联关系

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;

    private Boolean state;

    private boolean admin;
    //many2many
    private List<Role> roles = new ArrayList<>();

}