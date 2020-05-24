package Model.Member;

public abstract class Account {

    private String username;
    private String password;
    public Person person;
    protected Status status;

    public Account(String username, String password, Person person) {
        this.username = username;
        this.password = password;
        this.person = person;
        this.status = null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return person.getFirstname();
    }

    public String getLastname() {
        return person.getLastname();
    }

    public Status getStatus() {
        return status;
    }

}
