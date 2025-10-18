package com.web.table;

import com.web.data.Point;

import java.sql.*;
import java.util.ArrayList;

public class TabManager {

    private final Connection connection;

    public TabManager(String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);

    }

    public ArrayList<Point> show() throws SQLException {
        ArrayList<Point> points = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM PointsWEB");
        while (result.next()) {
            points.add(getPoint(result));
        }
        return points;
    }

    private Point getPoint(ResultSet result) throws SQLException {
        Point point = new Point();
        point.setId(result.getInt("id"));
        point.setX(result.getDouble("x"));
        point.setY(result.getDouble("y"));
        point.setR(result.getDouble("r"));
        point.setCheck(result.getBoolean("checkResult"));
        point.setClickTime(result.getString("clickTime"));
        point.setExecutionTime(result.getString("executionTime"));
        return point;
    }

    public void Add(Point point) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO PointsWEB"
                    + "(x, y, r, checkResult, clickTime, executionTime) "
                    + "VALUES (?,?,?,?,?,?) RETURNING id");
            statement.setDouble(1, point.getX());
            statement.setDouble(2, point.getY());
            statement.setDouble(3, point.getR());
            statement.setBoolean(4, point.isCheck());
            statement.setString(5, point.getClickTime());
            statement.setString(6, point.getExecutionTime());
            ResultSet result = statement.executeQuery();
            result.next();
        } catch (SQLException e) {
            System.out.println("Элемент не добавлен, ошибка в sql");
        }
    }

    public void clearAll() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM PointsWEB");
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
