package ucunix;

import java.util.List;

public class User {
    
    private final String username;
    private final List<String> groups;
    
    public User(String username, List<String> groups) {
        this.username = username;
        this.groups = groups;
    }
    
    public String getUserName() {
        return username;
    }
    
    public List<String> getGroups() {
        return groups;
    }
}
