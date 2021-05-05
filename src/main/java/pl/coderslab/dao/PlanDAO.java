package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.model.SingleMeal;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    private static final String LASTEST_PLAN = "SELECT day_name.name as day_name, meal_name, recipe_id, recipe.name as recipe_name, recipe.description as recipe_description, p.name as plan_name\n" +
            "FROM `recipe_plan`\n" +
            "    JOIN plan p on recipe_plan.plan_id = p.id\n" +
            "         JOIN day_name on day_name.id=day_name_id\n" +
            "         JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "        recipe_plan.plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?)\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String CREATE_PLAN_QUERY = "INSERT INTO scrumlab.plan(name, description, admin_id, created) VALUES (?,?,?, NOW())";
    private static final String FIND_PLAN_QUERY = "SELECT * from scrumlab.plan where id = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	scrumlab.plan SET name = ? , description = ?  WHERE	id = ?;";
    private static final String FIND_ALL_PLAN_QUERY = "SELECT * FROM scrumlab.plan WHERE admin_id = ? ORDER BY created";
    private static final String NUMBER_OF_ALL_USER_PLAN_QUERY = "SELECT COUNT(name) AS NumberOfPlans FROM plan WHERE admin_id = ?; ";
    private static final String PLAN_NAME_QUERY = "SELECT plan.name AS planName FROM plan WHERE created = (SELECT MAX(created) FROM plan  WHERE admin_id = ?); ";


    public String loadPLastPlanName(int userId) {
        String planName = "";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(PLAN_NAME_QUERY)
        ) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    planName = resultSet.getString("planName");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planName;
    }

    public List<SingleMeal> loadPlan(int userId) {
        List<SingleMeal> meals = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
        ) {
            PreparedStatement ps = connection.prepareStatement(LASTEST_PLAN);
            ps.setString(1, String.valueOf(userId));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                SingleMeal singleMeal = new SingleMeal(
                        resultSet.getString("day_name"),
                        resultSet.getString("meal_name"),
                        resultSet.getString("recipe_name"),
                        resultSet.getString("recipe_description"),
                        resultSet.getString("plan_name"),
                        resultSet.getInt("recipe_id")
                );
                meals.add(singleMeal);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meals;
    }

    public Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setInt(3, plan.getAdmin_id());
            int result = insertStm.executeUpdate();
            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public Plan read(Integer planid) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PLAN_QUERY)
        ) {
            statement.setInt(1, planid);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("Name"));
                    plan.setDescription(resultSet.getString("Description"));
                    plan.setAdmin_id(resultSet.getInt("Admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;
    }

    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.setInt(3, plan.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Plan> findAll(int adminId) {
        List<Plan> plans = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PLAN_QUERY)
        ) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Plan plan = new Plan(
                        resultSet.getInt("plan.id"),
                        resultSet.getString("plan.name"),
                        resultSet.getString("plan.description"),
                        resultSet.getInt("plan.created"),
                        resultSet.getInt("plan.admin_id")
                );
                plans.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }

    public List<Plan> findAll() {
        List<Plan> plans = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PLAN_QUERY)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Plan plan = new Plan(
                        resultSet.getInt("plan.id"),
                        resultSet.getString("plan.name"),
                        resultSet.getString("plan.description"),
                        resultSet.getInt("plan.created"),
                        resultSet.getInt("plan.admin_id")
                );
                plans.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }

    public int numberOfPlans(int adminId) {
        int result = 0;
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(NUMBER_OF_ALL_USER_PLAN_QUERY)
        ) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("NumberOfPlans");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}