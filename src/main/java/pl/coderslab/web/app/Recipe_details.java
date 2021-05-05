package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDAO;
import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/recipe/details")
public class Recipe_details extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
            RecipeDAO re = new RecipeDAO();
            Recipe recipe = re.read(recipeId);
            request.setAttribute("Result", recipe);
        getServletContext().getRequestDispatcher("/Recipe_details.jsp")
                .forward(request, response);
    }
}



