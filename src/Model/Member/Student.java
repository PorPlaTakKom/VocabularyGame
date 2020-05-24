package Model.Member;

public class Student extends Account{
    
    public Student(String username, String password, Person person) {
        super(username, password, person);
        super.status = Status.Student;
    }

    @Override
    public String toString() {
        return "[Student] | " + person.getFirstname() + " " + person.getLastname(); 
    }
       
}
