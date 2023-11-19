package comp5017;
public class Employee implements IEmployee {
    private final String name;
    private final String affiliation;
    public Employee(String name, String affiliation) {
        this.name = name;
        this.affiliation = affiliation;
    }

    public String getName() {
        return name;
    }
    public String getAffiliation() {
        return affiliation;
    }
    public String toString() {
        return "Name: " + name + "\nAffiliation: " + affiliation;
    }
}
