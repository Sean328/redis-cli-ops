package com.xxl.domain;

/**
 * @author lixin
 * @date 2018-12-26 11:49
 **/
public class ThreadPoolEntity extends BaseDomain{
    private static final long serialVersionUID = 5956825511806241294L;
    private String name ;

    private String course;

    public String getName() {
        return name;
    }

    public ThreadPoolEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getCourse() {
        return course;
    }

    public ThreadPoolEntity setCourse(String course) {
        this.course = course;
        return this;
    }
}
