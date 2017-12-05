package com.shalu.letsbake.utils;

public class Recipe {

    public String name;
    public String servings;
    public String image;
    public Ingredient[] ingredients;
    public Steps[] steps;

    Recipe(String name, String servings, String image) {
        this.name = name;
        this.servings = servings;
        this.image = image;
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
        public String thumbnailURL;

    }
}