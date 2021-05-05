package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecipeDAO {
    private static final String FIND_ALL_RECIPE_QUERY = "SELECT * FROM recipe WHERE admin_id = ?";
    private static final String FIND_SINGLE_RECIPE_QUERY = "SELECT day_name.name as day_name, meal_name, recipe.id as id, recipe.name as name, recipe.ingredients as ingredients, recipe.created as created, recipe.updated as updated, recipe.preparation_time as preparation_time, recipe.preparation as preparation, recipe.admin_id as admin_id, recipe.description as description \n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE plan_id =?" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String FIND_RECIPE_BY_ID = "SELECT * FROM recipe WHERE id = ?";
    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe(name, ingredients, description, preparation_time, preparation, admin_id, created) VALUES (?,?,?,?,?,?,?);";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe where id = ?;";
    private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ?, ingredients = ?, description = ?, preparation_time = ?, preparation = ?, updated = ? WHERE id = ?;";


    public List<Recipe> find(int planId) {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_SINGLE_RECIPE_QUERY)
        ) {
            preparedStatement.setString(1, String.valueOf(planId));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("ingredients"),
                        resultSet.getString("description"),
                        resultSet.getString("created"),
                        resultSet.getString("updated"),
                        resultSet.getInt("preparation_time"),
                        resultSet.getString("preparation"),
                        resultSet.getInt("admin_id")
                );
                recipes.add(recipe);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (recipes.size() == 0) {
            throw new NotFoundException("Tabela day_name pusta!!!");
        } else {
            return recipes;
        }
    }

    public Recipe read(int recipeId) {
        Recipe recipe = new Recipe();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_RECIPE_BY_ID)) {
            preparedStatement.setInt(1, (recipeId));
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    recipe.setId(rs.getInt("id"));
                    recipe.setName(rs.getString("name"));
                    recipe.setIngredients(rs.getString("ingredients"));
                    recipe.setDescription(rs.getString("description"));
                    recipe.setCreated(rs.getString("created"));
                    recipe.setUpdated(rs.getString("updated"));
                    recipe.setPreparationTime(rs.getInt("preparation_time"));
                    recipe.setPreparationText(rs.getString("preparation"));
                    recipe.setAdminId(rs.getInt("admin_id"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return recipe;
    }

    public List<Recipe> findAll(int adminId) {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_RECIPE_QUERY)
        ) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe(
                        resultSet.getInt("recipe.id"),
                        resultSet.getString("recipe.name"),
                        resultSet.getString("recipe.ingredients"),
                        resultSet.getString("recipe.description"),
                        resultSet.getString("recipe.created"),
                        resultSet.getString("recipe.updated"),
                        resultSet.getInt("recipe.preparation_time"),
                        resultSet.getString("recipe.preparation"),
                        resultSet.getInt("recipe.admin_id")
                );
                recipes.add(recipe);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (recipes.size() == 0) {
            throw new NotFoundException("Tabela day_name pusta!!!");
        } else {
            return recipes;
        }
    }

    public Recipe create(Recipe recipe) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_RECIPE_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, recipe.getName());
            insertStm.setString(2, recipe.getIngredients());
            insertStm.setString(3, recipe.getDescription());
            insertStm.setString(4, String.valueOf(recipe.getPreparationTime()));
            insertStm.setString(5, recipe.getPreparationText());
            insertStm.setInt(6, recipe.getAdminId());
            insertStm.setString(7, currentTime);
            int result = insertStm.executeUpdate();
            if (result != 1) {
                throw new RuntimeException("Zwrocono: " + result);
            }
            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipe.setId(generatedKeys.getInt(1));
                    return recipe;
                } else {
                    throw new RuntimeException("Nie znaleziono klucza!!!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void delete(Integer recipeId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_QUERY)) {
            statement.setInt(1, recipeId);
            statement.executeUpdate();
            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Nie znaleziono przepisu!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RECIPE_QUERY)) {
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String updateTime = sdf.format(dt);

            statement.setInt(7, recipe.getId());
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescription());
            statement.setInt(4, recipe.getPreparationTime());
            statement.setString(5, recipe.getPreparationText());
            statement.setString(6, updateTime);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static final String NUMBER_OF_USER_RECIPES_QUERY = "SELECT COUNT(name) AS NumberOfRecipes FROM recipe WHERE admin_id = ?; ";

    public int numberOfRecipes(int adminId){
        int result = 0;
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(NUMBER_OF_USER_RECIPES_QUERY)
        ) {
            preparedStatement.setInt(1,adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("NumberOfRecipes");
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
