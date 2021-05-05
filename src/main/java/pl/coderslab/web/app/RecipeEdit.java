package pl.coderslab.web.app;

import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/app/recipe/edit")
public class RecipeEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recipeId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("loggedIn");
        RecipeDAO rd = new RecipeDAO();
        Recipe recipe = rd.read(recipeId);

        if (recipe.getAdminId() != admin.getId()) {
            response.sendRedirect("/app/recipe/list/");
        } else {
            request.setAttribute("recipe", recipe);
            getServletContext().getRequestDispatcher("/app/recipeEdit.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDAO rd = new RecipeDAO();
        Recipe recipe = new Recipe(request.getParameter("name"),
                request.getParameter("ingredients"), request.getParameter("description"),
                Integer.parseInt(request.getParameter("preparationTime")), request.getParameter("preparationText"),
                Integer.parseInt(request.getParameter("id")));
        rd.update(recipe);
        response.sendRedirect("/app/recipe/list/");
    }
}
