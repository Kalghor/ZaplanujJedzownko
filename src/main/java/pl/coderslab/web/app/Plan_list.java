package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDAO;
import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/plan/list")
public class Plan_list extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("Plan") == null) {
            if (session.getAttribute("loggedIn") != null) {
                Admin admin = (Admin) session.getAttribute("loggedIn");
                PlanDAO planDAO = new PlanDAO();
                List<Plan> planes = planDAO.findAll(admin.getId());
                session.setAttribute("Plan", planes);
            } else {
                request.setAttribute("planList", "Nie ma jeszcze utworzonego planu.");
            }
        }
        getServletContext().getRequestDispatcher("/Plan_list.jsp")
                .forward(request, response);
    }
}