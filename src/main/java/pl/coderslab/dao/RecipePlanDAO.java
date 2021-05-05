package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.*;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecipePlanDAO {
    private static final String CREATE_RECIPE_PLAN_QUERY = "INSERT INTO recipe_plan(recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?,?,?,?,?)";
    private static final String FIND_PLAN_DETAILS_QUERY = "SELECT day_name.name as day_name, meal_name, p.name AS plan_name, p.description AS plan_description, recipe.id AS recipe_id, recipe.name AS recipe_name, recipe.description as recipe_description\n" +
            "FROM `recipe_plan`\n" +
            "         JOIN day_name on day_name.id = day_name_id\n" +
            "         JOIN plan p on recipe_plan.plan_id = p.id\n" +
            "         JOIN recipe on recipe.id = recipe_id\n" +
            "WHERE plan_id = ?\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String DELETE_RECIPE_FROM_PLAN_QUERY = "DELETE FROM recipe_plan WHERE plan_id = ? AND recipe_id = ?";

    public RecipePlan create(RecipePlan recipePlan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_RECIPE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setInt(1, recipePlan.getRecipeId());
            insertStm.setString(2, recipePlan.getMealName());
            insertStm.setInt(3, recipePlan.getDisplayOrder());
            insertStm.setInt(4, recipePlan.getDayNameId());
            insertStm.setInt(5, recipePlan.getPlanId());
            int result = insertStm.executeUpdate();
            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipePlan.setId(generatedKeys.getInt(1));
                    return recipePlan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SingleMeal> read(Integer planId) {
        List<SingleMeal> singleMeals = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PLAN_DETAILS_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SingleMeal singleMeal = new SingleMeal(
                            resultSet.getString("day_name"),
                            resultSet.getString("meal_name").toLowerCase(),
                            resultSet.getString("recipe_name").toLowerCase(),
                            resultSet.getString("recipe_description"),
                            resultSet.getString("plan_name"),
                            resultSet.getString("plan_description"),
                            resultSet.getInt("recipe_id")
                    );
                    singleMeals.add(singleMeal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return singleMeals;
    }

    public void delete(Integer planId, Integer recipeId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_FROM_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.setInt(2, recipeId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Nie znaleziono przepisu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
