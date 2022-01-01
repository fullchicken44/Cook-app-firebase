package com.example.asm3;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    private String name, instruction, image;
    private List<String> ingredient;
    private List<Integer> rate;

    public Dish() {
        this.name = null;
        this.image = null;
        this.instruction = null;
        this.ingredient = new ArrayList<>();
        this.rate = new ArrayList<>();
    }

    public Dish(String name, String image, String instruction, List<String> ingredient, List<Integer> rate) {
        this.name = name;
        this.image = image;
        this.instruction = instruction;
        this.ingredient = ingredient;
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public List<String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<String> ingredient) {
        this.ingredient = ingredient;
    }

    public List<Integer> getRate() {
        return rate;
    }

    public void setRate(List<Integer> rate) {
        this.rate = rate;
    }
}
