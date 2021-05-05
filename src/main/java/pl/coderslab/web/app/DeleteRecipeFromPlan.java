package pl.coderslab.web.app;

import pl.coderslab.dao.RecipePlanDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/app/recipe/plan/delete")
public class DeleteRecipeFromPlan extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planId = Integer.parseInt(request.getParameter("planId"));
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        RecipePlanDAO recipePlanDAO = new RecipePlanDAO();
        recipePlanDAO.delete(planId,recipeId);
        response.sendRedirect("/app/plan/details?id=" + planId);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
