package com.web.data;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Point implements Serializable {
    private int id;
    private double x;
    private double y;
    private double r;
    private boolean check;
    private String clickTime;
    private String executionTime;

}
