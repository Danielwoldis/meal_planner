CREATE TABLE RECIPE (
    recipe_name VARCHAR(30) PRIMARY KEY ,
    instructions VARCHAR(1000)
);

CREATE TABLE INGREDIENT (
    ingredient_name VARCHAR(30) PRIMARY KEY
);

CREATE TABLE RECIPEINGREDIENT(
    recipe_name VARCHAR(50),
    ingredient_name VARCHAR(50),
    amount double,
    unit VARCHAR(50),
    PRIMARY KEY (recipe_name, ingredient_name)
);