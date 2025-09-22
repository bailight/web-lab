package web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.data.Point;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<Point> results = new ArrayList<>();
        session.setAttribute("results", results);

        String xText = request.getParameter("x");
        String yText = request.getParameter("y");
        String rText = request.getParameter("r");


        if (xText != null && yText != null && rText != null) {
            request.getRequestDispatcher("/areaCheck").forward(request, response);
        } else {
            request.getRequestDispatcher("/").forward(request, response);
        }
    }


}
