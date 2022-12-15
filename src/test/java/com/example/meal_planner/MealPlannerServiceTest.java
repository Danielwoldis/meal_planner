package com.example.meal_planner;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MealPlannerServiceTest {


    @Autowired
    RecipeDatabase recipeDatabase;

    @Autowired
    MealPlanService mealPlanService;


    @org.junit.Test
    public void shouldGetRecipeById() {
        recipeDatabase.loadAllRecipes();
        //Recipe recipe = recipeDatabase.getAllRecipes().get(1);

        Recipe recipe = recipeDatabase.getRecipeById(1);

        Assert.assertEquals("message..", "Biff på primus", recipe.getName());
    }



    @org.junit.Test
    public void shouldGetRecipeByIdFromMealPlanService() {
        //recipeDatabase.loadAllRecipes();
        //Recipe recipe = recipeDatabase.getAllRecipes().get(1);

        Recipe recipe = mealPlanService.getRecipeById(1);

        Assert.assertEquals("message..", "Biff på primus", recipe.getName());
    }




    /*
    @Test
    public void isDublicate() {
        MealPlanService mealPlanService = new MealPlanService();
        int[] array1 = {1, 2, 3, 4, 5, 6, 7};
        assertTrue(mealPlanService.isDublicate(array1, 3));
        assertTrue(mealPlanService.isDublicate(array1, 7));

        assertFalse(mealPlanService.isDublicate(array1,9) );


    }
    @Test
    public void numberOfpages()
    {
        MealPlanService mealPlanService = new MealPlanService();

        assertEquals(3,mealPlanService.numberOfPages(5) ) ;
        assertEquals(2,mealPlanService.numberOfPages(9) ) ;

    }
    @Test
    public void getRecipeById()
    {
        MealPlanService mealPlanService = new MealPlanService() ;
        assertEquals("Pizza med reinsdyrbiff",mealPlanService.getRecipeById(1).getName() );
        assertEquals("Grillspyd med laksefilet",mealPlanService.getRecipeById(8).getName() );
        assertEquals("Svinekam",mealPlanService.getRecipeById(13).getName() );

    }

     */
}
