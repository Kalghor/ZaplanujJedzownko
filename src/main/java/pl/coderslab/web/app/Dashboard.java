package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDAO;
import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Admin;
import pl.coderslab.model.SingleMeal;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") != null) {
            Admin admin = (Admin) session.getAttribute("loggedIn");

            PlanDAO dao = new PlanDAO();
            int numberOfPlans = dao.numberOfPlans(admin.getId());
            request.setAttribute("numberOfPlans", numberOfPlans);

            RecipeDAO recipeDAO = new RecipeDAO();
            int numberOfRecipes = recipeDAO.numberOfRecipes(admin.getId());
            request.setAttribute("numberOfRecipes", numberOfRecipes);


            String adminName = admin.getFirstName();
            request.setAttribute("adminName", adminName);


            List<SingleMeal> listAllOfData = dao.loadPlan(admin.getId());
            String planName = dao.loadPLastPlanName(admin.getId());
            request.setAttribute("planName", planName);
            Map<String, List<SingleMeal>> map = loadLastPlan(listAllOfData);
            request.setAttribute("map", map);
            request.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } else {
            request.setAttribute("planList", Arrays.asList("Nie ma jeszcze utworzonego planu."));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public Map<String, List<SingleMeal>> loadLastPlan(List<SingleMeal> listAllOfData) {
        Map<String, List<SingleMeal>> map = new LinkedHashMap<>();
        for (SingleMeal singleMeal : listAllOfData) {
            switch (singleMeal.getDayName()) {
                case "poniedzia??ek": {
                    if (map.get("poniedzia??ek") == null) {
                        List<SingleMeal> list = new ArrayList<>();
                        list.add(singleMeal);
                        map.put("poniedzia??ek", list);
                    } else {
                        List<SingleMeal> list = map.get("poniedzia??ek");
                        list.add(singleMeal);
                        map.put("poniedzia??ek", list);
                    }
                    break;
                }
                case "wtorek": {
                    if (map.get("wtorek") == null) {
                        List<SingleMeal> list = new ArrayList<>();
                        list.add(singleMeal);
                        map.put("wtorek", list);
                    } else {
                        List<SingleMeal> list = map.get("wtorek");
                        list.add(singleMeal);
                        map.put("wtorek", list);
                    }
                    break;
                }
                case "??roda": {
                    if (map.get("??roda") == null) {
                        List<SingleMeal> list = new ArrayList<>();
                        list.add(singleMeal);
                        map.put("??roda", list);
                    } else {
                        List<SingleMeal> list = map.get("??roda");
                        list.add(singleMeal);
                        map.put("??roda", list);
                    }
                    break;
                }
                case "czwartek": {
                    if (map.get("czwartek") == null) {
                        List<SingleMeal> list = new ArrayList<>();
                        list.add(singleMeal);
                        map.put("czwartek", list);
                    } else {
                        List<SingleMeal> list = map.get("czwartek");
                        list.add(singleMeal);
                        map.put("czwartek", list);
                    }
                    break;
                }
                case "pi??tek": {
                    if (map.get("pi??tek") == null) {
                        List<SingleMeal> list = new ArrayList<>();
                        list.add(singleMeal);
                        map.put("pi??tek", list);
                    } else {
                        List<SingleMeal> list = map.get("pi??tek");
                        list.add(singleMeal);
                        map.put("pi??tek", list);
                    }
                    break;
                }
                case "sobota": {
                    if (map.get("sobota") == null) {
                        List<SingleMeal> list = new ArrayList<>();
                        list.add(singleMeal);
                        map.put("sobota", list);
                    } else {
                        List<SingleMeal> list = map.get("sobota");
                        list.add(singleMeal);
                        map.put("sobota", list);
                    }
                    break;
                }
                case "niedziela": {
                    if (map.get("niedziela") == null) {
                        List<SingleMeal> list = new ArrayList<>();
                        list.add(singleMeal);
                        map.put("niedziela", list);
                    } else {
                        List<SingleMeal> list = map.get("niedziela");
                        list.add(singleMeal);
                        map.put("niedziela", list);
                    }
                    break;
                }
            }
        }
        return map;
    }
}
