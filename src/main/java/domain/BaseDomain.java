package domain;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author lixin
 * @date 2018-12-26 11:45
 **/
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = 4246378860356621733L;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
