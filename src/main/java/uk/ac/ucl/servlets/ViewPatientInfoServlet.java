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
import java.util.List;
import java.util.Map;

@WebServlet("/patientInfo")
public class ViewPatientInfoServlet extends HttpServlet {
    /**
     * Handles HTTP GET requests.
     *
     * By calling doPost, this allows search results to be bookmarked and refreshed
     * (since many browsers default to GET for URL-based navigation).
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
        try {
            // 1. Get the singleton instance of the Model.
            // The Model handles the actual data processing and data retrieval.
            Model model = ModelFactory.getModel();

            String patientID = request.getParameter("id");

            // 2. Retrieve the list of patient names from the model.
            Map<String, String> patientInfo = model.getPatientInfo(patientID);

            // 3. Add the data to the request object.
            // This makes the 'patientSummaries' list accessible to the JSP page for
            // rendering.
            if (patientInfo == null) {
                request.setAttribute("errorMessage", "Patient not found.");
            } else {
                request.setAttribute("patientInfo", patientInfo);
                request.setAttribute("columnNames", model.getColumnNames());
            }

            // 4. Invoke the JSP for display.
            // RequestDispatcher.forward() is used to send the request/response objects to
            // another resource (JSP).
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/patientInfo.jsp");
            dispatch.forward(request, response);
        } catch (IOException e) {
            // 5. Exception Handling.
            // If there is an issue loading the model or data, log the error and forward to
            // a dedicated error page.
            request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     * Redirects to doGet as viewing a list is typically an idempotent operation.
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
