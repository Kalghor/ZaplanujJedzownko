package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDAO;
import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/recipe/add")
public class AddRecipeForm extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/addRecipeForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeName = request.getParameter("name");
        String description = request.getParameter("description");
        String preparation = request.getParameter("preparation");
        int preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
        String ingredients = request.getParameter("name");
        if(recipeName != null && description != null && preparation != null && ingredients != null){
            HttpSession session = request.getSession();
            if(session.getAttribute("loggedIn") != null) {
                Admin admin = (Admin) session.getAttribute("loggedIn");
                Recipe recipe = new Recipe(recipeName,ingredients,description,preparationTime,preparation, admin.getId());
                RecipeDAO dao = new RecipeDAO();
                dao.create(recipe);
            } else {
                request.setAttribute("planList", "Nie ma jeszcze utworzonego planu.");
            }
//            Recipe recipe = new Recipe(recipeName,ingredients,description,preparationTime,preparation,1);
//            RecipeDAO dao = new RecipeDAO();
//            dao.create(recipe);
        }
        response.sendRedirect("/app/plan/list");
    }
}
