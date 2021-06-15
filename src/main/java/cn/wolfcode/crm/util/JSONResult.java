package cn.wolfcode.crm.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter
@NoArgsConstructor
public class JSONResult {
    private boolean success = true;
    private String msg ;

    public JSONResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public static JSONResult mark(String msg) {
        return new JSONResult(false, msg);
    }
    public static JSONResult success() {
        return new JSONResult();
    }

}
