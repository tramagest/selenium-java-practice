package dataObjects;

public enum Account {
    VALID("hobepe1432@ensproxy.com", "123456789", "123456789");

    private final String email;
    private final String password;
    private final String pid;

    Account(String email, String password, String pid) {
        this.email = email;
        this.password = password;
        this.pid = pid;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPid() { return pid; }
}