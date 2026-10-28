package dataObjects;

public class Account {
    private final String email;
    private final String password;
    private final String pid;

    public Account(String email, String password, String pid) {
        this.email = email;
        this.password = password;
        this.pid = pid;
    }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPid() { return pid; }


}
