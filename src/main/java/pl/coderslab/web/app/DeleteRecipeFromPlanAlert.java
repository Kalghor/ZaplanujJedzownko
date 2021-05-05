package pl.coderslab.web.app;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/app/recipe/plan/delete/alert")
public class DeleteRecipeFromPlanAlert extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planId = Integer.parseInt(request.getParameter("planId"));
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        request.setAttribute("planId", planId);
        request.setAttribute("recipeId", recipeId);
        getServletContext().getRequestDispatcher("/confirmDeleteRecipeFromPlan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
