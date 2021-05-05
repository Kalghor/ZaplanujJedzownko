package pl.coderslab.web.app;

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

@WebServlet("/app/recipe/list/")
public class Recipe_list extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("Recipe") == null) {
            if (session.getAttribute("loggedIn") != null) {
                Admin admin = (Admin) session.getAttribute("loggedIn");
                RecipeDAO re = new RecipeDAO();
                List<Recipe> recipes = re.findAll(admin.getId());
                session.setAttribute("Recipe", recipes);
            }
        }
        getServletContext().getRequestDispatcher("/Recipe_list.jsp")
                .forward(request, response);
    }
}


