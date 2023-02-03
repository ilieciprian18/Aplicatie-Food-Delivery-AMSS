package Classes;

public class User {
    protected String nume;
    protected String prenume;
    protected String email;
    protected String telefon;
    protected String gender;
    private DateOfBirth birthday;

    protected String username;
    protected int userType;

    public User(String nume, String prenume, String email, String telefon, String gender, DateOfBirth birthday, String username, int userType) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.telefon = telefon;
        this.gender = gender;
        this.birthday = birthday;
        this.username = username;
        this.userType = userType;
    }

    public User() {
        this.nume ="";
        this.prenume = "";
        this.email = "";
        this.telefon = "";
        this.gender = "";
        this.birthday = null;
        this.username = "";
        this.userType = -1;
    }


}
