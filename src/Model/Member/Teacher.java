package Model.Member;

public class Teacher extends Account{
    
    public Teacher(String username, String password, Person person) {
        super(username, password, person);
        super.status = Status.Teacher;
    }

    @Override
    public String toString() {
        return "[Teacher] | " + person.getFirstname() + " " + person.getLastname();
    }
}
