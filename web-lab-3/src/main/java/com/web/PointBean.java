package com.web;

import com.web.data.Point;
import com.web.table.TabCreator;
import com.web.table.TabManager;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SlideEndEvent;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Named
@ApplicationScoped
@Getter
@Setter
public class PointBean implements Serializable {
    private int id;
    private double x = 0.0;
    private double y = 0.0;
    private double r = 1.0;
    private boolean check;
    private String clickTime;
    private String executionTime;
    private TabManager DBManager;
    private ArrayList<Point> points;

    public PointBean() {
    }

    @PostConstruct
    public void init() throws Exception {
        TabCreator tabCreator = new TabCreator();

        this.DBManager = new TabManager(
                tabCreator.dbProps.getProperty("db.url"),
                tabCreator.dbProps.getProperty("db.user"),
                tabCreator.dbProps.getProperty("db.password")
        );
    }

    public void checkPoint() {
        clickTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        long startTime = System.nanoTime();

        this.check = inGraph(x, y, r);

        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setR(r);
        point.setCheck(check);
        point.setClickTime(clickTime);
        executionTime = String.valueOf(System.nanoTime() - startTime);
        point.setExecutionTime(executionTime + "ns");
        DBManager.Add(point);

        PrimeFaces.current().executeScript(// 先重置画布和区域
                "draw();" +
                "drawPoint(" + x + ", " + y + ", " + r + ", " + check + ");"
        );
    }

    private static boolean inGraph(double x, double y, double r){
        if (x <= 0 && y <= 0){
            return y >= -r && x >= -r; //第三象限 正方形
        } else if (x >= 0 && y <= 0) {
            return y > -r && y >= x/2 - 2; //第四象限 三角形
        } else if (x <= 0 && y >= 0) {
            return x * x + y * y < r * r / 4; //第二象限 1/4圆形
        }else{
            return false; //第一象限 空
        }
    }

    public ArrayList<Point> getPoints() throws SQLException{
        points = DBManager.show();
        return points;
    }

    public void clearPoint() {
        DBManager.clearAll();
    }

    public void onSlideEnd(SlideEndEvent event) {
        FacesMessage message = new FacesMessage("Slide Ended", "Value: " + event.getValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onSlideEndR(SlideEndEvent event) {
        FacesMessage message = new FacesMessage("Slide Ended", "Value: " + event.getValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().executeScript("draw();");
    }



}
