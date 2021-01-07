package Instructor;

public class instructor {
    private String name,expertise,age,id;
    public instructor(String name,String id,String age,String expertise){
        this.age = age;
        this.id = id;
        this.name = name;
        this.expertise = expertise;
    }

    public String getName() {
        return name;
    }

    public String getExpertise() {
        return expertise;
    }

    public String getAge() {
        return age;
    }

    public String getId() {
        return id;
    }
}
