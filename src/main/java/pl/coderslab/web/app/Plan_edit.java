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

@WebServlet("/app/plan/edit")
public class Plan_edit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PlanDAO pl = new PlanDAO();
        Plan plan = new Plan(
                request.getParameter("Name"),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("id")));
        pl.update(plan);
        response.sendRedirect("/app/plan/list");
    }




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planId = Integer.parseInt(request.getParameter("id"));

        PlanDAO pl = new PlanDAO();
        Plan plan = pl.read(planId);



        request.setAttribute("plan", plan);
        getServletContext().getRequestDispatcher("/Plan_edit.jsp").forward(request, response);


    }
}
