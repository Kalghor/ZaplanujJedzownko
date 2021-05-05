package pl.coderslab.model;

public class Recipe {

    private int id;
    private String name;
    private String ingredients;
    private String description;
    private String created;
    private String updated;
    private int preparationTime;
    private String preparationText;
    private int adminId;

    public Recipe(int id, String name, String ingredients, String description, String created, String updated, int preparationTime, String preparationText, int adminId) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.preparationTime = preparationTime;
        this.preparationText = preparationText;
        this.adminId = adminId;
    }

    public Recipe(String name, String ingredients, String description, int preparationTime, String preparationText, int adminId) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.preparationTime = preparationTime;
        this.preparationText = preparationText;
        this.adminId = adminId;
    }

    public Recipe() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setPreparationText(String preparationText) {
        this.preparationText = preparationText;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public String getPreparationText() {
        return preparationText;
    }

    public int getAdminId() {
        return adminId;
    }
}
