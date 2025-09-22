package web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.data.Point;
import web.exception.ValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/areaCheck")
public class AreaCheckServlet extends HttpServlet{
    private static final double X_MIN = -4.0;
    private static final double X_MAX = 4.0;
    private static final double Y_MIN = -3.0;
    private static final double Y_MAX = 3.0;
    private static final double R_MIN = 1.0;
    private static final double R_MAX = 4.0;


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long startTime = System.nanoTime();

        try{
            double x = Double.parseDouble(request.getParameter("x"));
            double y = Double.parseDouble(request.getParameter("y"));
            double r = Double.parseDouble(request.getParameter("r"));

            validateCoordinate(x, y, r);

            HttpSession session = request.getSession();
            List<Point> results = new ArrayList<>();
            session.setAttribute("results", results);

            Point point = new Point(x, y, r, check_point(x, y, r), startTime);
            results.add(point);
            session.setAttribute("results", results);
            request.setAttribute("x", x);
            request.setAttribute("y", y);
            request.setAttribute("r", r);
            request.setAttribute("check", point.isCheck());
            request.setAttribute("clickTime", point.getClickTime());
            request.setAttribute("executionTime", point.getExecutionTime());
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format for x, y, or r.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (ValidationException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

    }

    private static boolean check_point(double x, double y, double r){
        if (x <= 0 && y <= 0){
            return y < r && x < r; //第三象限 正方形
        } else if (x >= 0 && y >= 0) {
            return x < r/2 && y < r; //第一象限 三角形
        } else if (x >= 0 && y <= 0) {
            return x * x /4 + y * y /4 < r * r /4; //第四象限 1/4圆形
        }else{
            return false;
        }
    }

    private void validateCoordinate(double x, double y, double r){
        if (x < X_MIN || x > X_MAX) {
            throw new ValidationException("X must be between " + X_MIN + " and " + X_MAX);
        }
        if (y < Y_MIN || y > Y_MAX) {
            throw new ValidationException("Y must be between " + Y_MIN + " and " + Y_MAX);
        }
        if (r < R_MIN || r > R_MAX) {
            throw new ValidationException("R must be between " + R_MIN + " and " + R_MAX);
        }
    }


}
