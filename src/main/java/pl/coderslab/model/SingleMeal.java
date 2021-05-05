package pl.coderslab.model;

public class SingleMeal {
    private int recipeId;
    private String dayName;
    private String mealName;
    private String recipeName;
    private String recipeDescription;
    private String planName;
    private String planDescription;

    public SingleMeal(String dayName, String mealName, String recipeName, String recipeDescription, String planName, int recipeId) {
        this.dayName = dayName;
        this.mealName = mealName;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.planName = planName;
        this.recipeId = recipeId;
    }

    public SingleMeal(String dayName, String mealName, String recipeName, String recipeDescription, String planName, String planDescription, int recipeId) {
        this.dayName = dayName;
        this.mealName = mealName;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.planName = planName;
        this.planDescription = planDescription;
        this.recipeId = recipeId;

    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getPlanName() {
        return planName;
    }

    public String getDayName() {
        return dayName;
    }

    public String getMealName() {
        return mealName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    @Override
    public String toString() {
        return "SingleMeal{" +
                "dayName='" + dayName + '\'' +
                ", mealName='" + mealName + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", recipeDescription='" + recipeDescription + '\'' +
                ", planName='" + planName + '\'' +
                '}';
    }
}
