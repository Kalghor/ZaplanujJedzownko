package pl.coderslab.web.app;

import pl.coderslab.dao.*;
import pl.coderslab.model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/app/recipe/plan/add")
public class AddRecipeToPlan extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DayNameDAO dayNameDAO = new DayNameDAO();
        List<DayName> listOfDay = dayNameDAO.findAll();
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") != null) {
            Admin admin = (Admin) session.getAttribute("loggedIn");
            PlanDAO planDAO = new PlanDAO();
            List<Plan> planList = planDAO.findAll(admin.getId());
            request.setAttribute("planList", planList);
            RecipeDAO recipeDAO = new RecipeDAO();
            List<Recipe> recipeList = recipeDAO.findAll(admin.getId());

            request.setAttribute("listOfDays", listOfDay);
            request.setAttribute("recipeList", recipeList);
        } else {
            request.setAttribute("planList", Arrays.asList("Nie ma jeszcze utworzonego planu."));
        }
        getServletContext().getRequestDispatcher("/addRecipeToPlan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String planName = request.getParameter("planName");
        String mealName = request.getParameter("mealName");
//        if (searchRecipeName(mealName)) {
//            response.sendRedirect("/app/recipe/plan/add?WrongMealName=1");

//            request.setAttribute("WrongMealName", "1");
//            getServletContext().getRequestDispatcher("/app/recipe/plan/add").forward(request, response);
//        } else {
        String mealNumber = request.getParameter("mealNumber");
        String recipeName = request.getParameter("recipeName");
        String day = request.getParameter("day");
        int planId = getPlanId(request, planName);
        List<Integer> dayNameId = getDayId(day);
        int recipeId = 0;

        HttpSession session = request.getSession();
        recipeId = getRecipeId(recipeName, session);

        RecipePlanDAO recipePlanDAO = new RecipePlanDAO();
        RecipePlan recipePlan = new RecipePlan(recipeId, mealName, dayNameId.get(1), dayNameId.get(0), planId);
        recipePlanDAO.create(recipePlan);
        response.sendRedirect("/app/recipe/plan/add");
//        }
    }

    private int getPlanId(HttpServletRequest request, String planName) {
        int planId = 0;
        PlanDAO planDAO = new PlanDAO();
        List<Plan> planList = new ArrayList<>();
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") != null) {
            Admin admin = (Admin) session.getAttribute("loggedIn");
            planList = planDAO.findAll(admin.getId());
        }
        for (Plan p : planList) {
            if (p.getName().equals(planName)) {
                planId = p.getId();
            }
        }
        return planId;
    }

    private int getRecipeId(String recipeName, HttpSession session) {
        List<Recipe> recipeList = new ArrayList<>();
        int recipeId = 0;

        if (session.getAttribute("loggedIn") != null) {
            Admin admin = (Admin) session.getAttribute("loggedIn");
            RecipeDAO recipeDAO = new RecipeDAO();
            recipeList = recipeDAO.findAll(admin.getId());
            for (Recipe r : recipeList) {
                if (r.getName().toLowerCase().equals(recipeName.toLowerCase())) {
                    recipeId = r.getId();
                }
            }
        }
        return recipeId;
    }

    private boolean searchRecipeName(String recipeName, HttpSession session) {
        boolean found = false;
        RecipeDAO recipeDAO = new RecipeDAO();
        if (session.getAttribute("loggedIn") != null) {
            Admin admin = (Admin) session.getAttribute("loggedIn");
            List<Recipe> recipeList = recipeDAO.findAll(admin.getId());
            for (Recipe r : recipeList) {
                if (!r.getName().toLowerCase().equals(recipeName.toLowerCase())) {
                    found = true;
                }
            }
        }
        return found;
    }

    private List<Integer> getDayId(String day) {
        List<Integer> dayIdAndDisplayOrder = new ArrayList<>();
        DayNameDAO dayNameDAO = new DayNameDAO();
        List<DayName> dayList = dayNameDAO.findAll();
        for (DayName d : dayList) {
            if (d.getName().equals(day)) {
                dayIdAndDisplayOrder.add(d.getId());
                dayIdAndDisplayOrder.add(d.getDisplayOrder());
            }
        }
        return dayIdAndDisplayOrder;
    }
}