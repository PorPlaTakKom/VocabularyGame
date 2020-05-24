package Model.Member;

public class Person {
    private String Firstname;
    private String Lastname;

    public Person(String Firstname, String Lastname) {
        this.Firstname = Firstname;
        this.Lastname = Lastname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }
    
}
