package com.example.meal_planner.data_mover;

import com.example.meal_planner.Ingredient;
import com.example.meal_planner.MealPlannerApplication;
import com.example.meal_planner.Recipe;
import com.example.meal_planner.RecipeDatabase;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;

/**
 * Moves all data to new SQL database.
 */

public class DataBaseMover {

    private final String targetPath;
    private PrintStream p;
    private final HashMap<Integer, Recipe> allRecipes;
    private RecipeDatabase rdb = new RecipeDatabase();

    public DataBaseMover() {
        targetPath = MealPlannerApplication.class.getResource("/data.sql").getPath();
        allRecipes = rdb.getAllRecipes();

        setOutPrintToFile();
        moveAllRecipesToNewDatabase();
        moveAllIngredientToNewDatabase();
        moveAllRecipeIngredients();
        setOutPrintToConsole();

    }

    private void setOutPrintToFile() {

        try {
            File file = new File(targetPath);

            p = new PrintStream(file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
            e.printStackTrace();
        }

        System.setOut(p);

    }

    private void setOutPrintToConsole() {

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

    }

    private void moveAllRecipesToNewDatabase() {

        for (Recipe r : allRecipes.values()) {
            System.out.printf("INSERT INTO RECIPE (recipe_name, instructions) VALUES ('%s', '%s');%n", r.getName(), r.getInstructions());
        }

    }

    private void moveAllIngredientToNewDatabase() {
        System.out.println();

        for (Recipe r : allRecipes.values()) {
            for (Ingredient i : r.getIngredients())
                System.out.printf("INSERT INTO INGREDIENT (ingredient_name) VALUES ('%s');%n", i.getName());
        }

    }

    private void moveAllRecipeIngredients() {
        System.out.println();

        DecimalFormat df = new DecimalFormat("#.00",
                DecimalFormatSymbols.getInstance(Locale.US));

        for (Recipe r : allRecipes.values()) {
            for (Ingredient i : r.getIngredients())
                System.out.printf("INSERT INTO RECIPEINGREDIENT (recipe_name, ingredient_name, amount, unit) VALUES ('%s', '%s', '%s', '%s');%n", r.getName(), i.getName(), df.format(i.getAmount()), i.getUnit());
        }

    }
}
