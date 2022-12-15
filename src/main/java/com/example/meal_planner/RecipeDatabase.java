package com.example.meal_planner;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RecipeDatabase {

    HashMap<Integer, Recipe> recipes = new HashMap<>();
    List<Integer> idList;
    @Autowired
    DataSource dataSource;


    public List<String> getSqlKeys() {
        List<String> keyList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("Select recipe_name from recipe");) {
            while (rs.next()) {
                keyList.add(rs.getString("recipe_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;
        return keyList;
    }

    public void loadAllRecipes() {


        var valus = getSqlKeys();

        for (int i = 0; i < valus.size(); i++) {
            recipes.put(i + 1, getRecepiFromSQLDataBase(valus.get(i)));

        }

/*        recipes.put(1, getRecepiFromSQLDataBase("Biff på primus"));
        recipes.put(2, getRecepiFromSQLDataBase("Burger med baconmarmolade"));
        recipes.put(3, getRecepiFromSQLDataBase("Eksotisk levergryte"));
        recipes.put(4, getRecepiFromSQLDataBase("Grillspyd med laksefilet"));
        recipes.put(5, getRecepiFromSQLDataBase("Kylling filet"));
        recipes.put(6, getRecepiFromSQLDataBase("Kyllingklubber i airfrier"));
        recipes.put(7, getRecepiFromSQLDataBase("LammeWok"));
        recipes.put(8, getRecepiFromSQLDataBase("Lammekoteletter i form"));
        recipes.put(9, getRecepiFromSQLDataBase("Omelett i ovn"));
        recipes.put(10, getRecepiFromSQLDataBase("Pasta"));
        recipes.put(11, getRecepiFromSQLDataBase("Pizza med reinsdyrbiff"));
        recipes.put(12, getRecepiFromSQLDataBase("Pølsegryte"));
        recipes.put(13, getRecepiFromSQLDataBase("Svinecarret"));
        recipes.put(14, getRecepiFromSQLDataBase("Svinekam"));*/


        idList = new ArrayList<>(recipes.keySet());
    }

    public HashMap<Integer, Recipe> getAllRecipes() {
        return recipes;
    }


    private Recipe getRecepiFromSQLDataBase(String recipe_name) {

        List<Ingredient> ingredientList = new ArrayList<>();


        String sql = String.format("Select ingredient_name, amount, unit from recipeingredient where recipe_name = '?'", recipe_name);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("Select ingredient_name, amount, unit from recipeingredient where recipe_name = ?");)
        //Statement stmt=conn.createStatement();
        //ResultSet rs = stmt.executeQuery(sql)) {
        {
            stmt.setString(1, recipe_name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ingredientList.add(rsIngredient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String instructions = null;
        String sql2 = String.format("Select instructions from recipe where recipe_name = '?';", recipe_name);


        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("Select instructions from recipe where recipe_name =?");
        ) {
            stmt.setString(1, recipe_name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                instructions = rs.getString("instructions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Recipe(recipe_name, ingredientList, instructions);


    }

    private Ingredient rsIngredient(ResultSet rs) throws SQLException {
        return new Ingredient(rs.getString("ingredient_name"),
                rs.getDouble("amount"),
                rs.getString("unit"));
        //, rs.getInt("PRICE"));
    }

    public Recipe getRecipeById ( int id){
        return getAllRecipes().get(id);
    }
}
