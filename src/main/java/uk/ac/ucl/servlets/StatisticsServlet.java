package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;
import java.util.Map;

@WebServlet("/statisticsResult")
public class StatisticsServlet extends HttpServlet {
    /**
     * Handles HTTP GET requests.
     *
     * @param request  the HttpServletRequest object that contains the request the
     *                 client has made of the servlet
     * @param response the HttpServletResponse object that contains the response the
     *                 servlet sends to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the
     *                          servlet handles the GET request
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String column = request.getParameter("column");
        try {
            Model model = ModelFactory.getModel();
            request.setAttribute("columnNames", model.getColumnNames());

            if (column != null) {
                Map<String, Integer> columnStatistics = model.getColumnStatistics(column);
                request.setAttribute("columnStatistics", columnStatistics);
            } else {
                request.setAttribute("errorMessage", "Error: Parameter column is null.");
            }

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/statisticsResult.jsp");
            dispatch.forward(request, response);
        } catch (IOException e) {
            request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * @param request  the HttpServletRequest object that contains the request the
     *                 client has made of the servlet
     * @param response the HttpServletResponse object that contains the response the
     *                 servlet sends to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the
     *                          servlet handles the POST request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
