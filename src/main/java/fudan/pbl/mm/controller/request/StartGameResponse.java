package fudan.pbl.mm.controller.request;

import fudan.pbl.mm.domain.Pack;
import fudan.pbl.mm.domain.User;

import java.util.Set;

public class StartGameResponse {
    private Pack pack;
    private Set<User> users;
    private User newUser;

    public StartGameResponse(){}
    public StartGameResponse(Pack pack, Set<User> users, User newUser){
        this.pack = pack;
        this.users = users;
        this.newUser = newUser;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Pack getPack() {
        return pack;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
}
