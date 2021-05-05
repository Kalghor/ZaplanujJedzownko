package pl.coderslab.model;

public class RecipePlan {
    private int id;
    private int recipeId;
    private String mealName;
    private int displayOrder;
    private int dayNameId;
    private int planId;

    public RecipePlan(int recipeId, String mealName, int displayOrder, int dayNameId, int planId) {
        this.recipeId = recipeId;
        this.mealName = mealName;
        this.displayOrder = displayOrder;
        this.dayNameId = dayNameId;
        this.planId = planId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public void setDayNameId(int dayNameId) {
        this.dayNameId = dayNameId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getMealName() {
        return mealName;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public int getDayNameId() {
        return dayNameId;
    }

    public int getPlanId() {
        return planId;
    }
}
