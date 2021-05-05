package pl.coderslab.model;



public class Plan {
    private int id;
    private String name;
    private String description;
    private int created;
    private int admin_id;

    public Plan(int created, String name, String description) {
       this.name = name;
       this.description = description;
       this.created = created;
   }

    public Plan(){

    }
    public Plan(int admin_id){
    this.admin_id = admin_id;
    }

    public Plan(int id, String name, String description, int created, int admin_id){
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.admin_id = admin_id;
    }

    public Plan(String name, String description, int id) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Plan(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCreated() {
        return created;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
    @Override
    public String toString() {
        return "Plan [id=" + id + ", Name=" + name + ", Description=" + description + ", Created=" + created + ", Admin_id=" + admin_id + "]";
    }
}

