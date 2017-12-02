package com.shalu.letsbake.utils;

public class Recipe {

    public String name;
    public String servings;
    public Ingredient[] ingredients;
    public Steps[] steps;

    Recipe(String name, String servings) {
        this.name = name;
        this.servings = servings;
    }

    public static class Ingredient {
        public String quantity;
        public String measure;
        public String ingredient_name;

    }

    public static class Steps {
        public String shortDescription;
        public String description;
        public String videoURL;

    }
}