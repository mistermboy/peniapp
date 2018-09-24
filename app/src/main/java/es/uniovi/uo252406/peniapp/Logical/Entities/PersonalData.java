package es.uniovi.uo252406.peniapp.Logical.Entities;

public class PersonalData {

    private String name;
    private String description;

    public PersonalData(String name,String description){
        this.name=name;
        this.description=description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
