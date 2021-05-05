package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDAO;
import pl.coderslab.dao.PlanDAO;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/plan/add")
public class Planadd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Plan plan = new Plan(request.getParameter("name"),request.getParameter("description"));
        HttpSession session = request.getSession();
        if(session.getAttribute("loggedIn") != null) {
            Admin admin = (Admin) session.getAttribute("loggedIn");
            PlanDAO pl = new PlanDAO();
            plan.setAdmin_id(admin.getId());
            pl.create(plan);
            response.sendRedirect("/app/plan/list");
        } else {
            request.setAttribute("planList", "Nie ma jeszcze utworzonego planu.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/Planadd.jsp").forward(request, response);
    }
}
