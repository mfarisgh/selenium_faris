package com.emeff23.stc.common;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)

public @interface JiraThis {

    //To be used as annotation across preferred Test methods
    boolean isCreateIssue();

}
