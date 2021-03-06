package fudan.pbl.mm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Entity
public class User implements UserDetails {

    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    private int age;
    private String region;
    @Column(columnDefinition = "varchar(255) default 'male'")
    private String gender;
    private String email;
    @JsonIgnore
    private String password;
    private String fullname;
    private String headProfilePath;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Knowledge> knowledgeSet;

    @Column(columnDefinition = "int(11) default 0")
    private int modelId;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Authority> authorities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<GameRecord> gameRecords;

    private int knowledgeNum;
    private int gameNum;

    public User() {}
    public User(String username, String password, String fullname,
                int age, String region, String gender, Set<Authority> authorities) {
        this.username = username;
        this.password= password;
        this.fullname = fullname;
        this.age = age;
        this.region = region;
        this.gender = gender;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public String getHeadProfilePath() {
        return headProfilePath;
    }

    public void setHeadProfilePath(String headProfilePath) {
        this.headProfilePath = headProfilePath;
    }

    public void addToCells(Cell cell){
        if(cell == null) return;
        cell.setUser(this);
    }

    public void removeFromCells(Cell cell){
        if(cell == null) return;
        cell.setUser(null);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Set<GameRecord> getGameRecords() {
        return gameRecords;
    }

    public void setGameRecords(Set<GameRecord> gameRecords) {
        this.gameRecords = gameRecords;
    }
    public void addToGameRecords(GameRecord gameRecord){
        if(this.gameRecords == null) gameRecords = new HashSet<>();
        gameRecords.add(gameRecord);
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setKnowledgeSet(Set<Knowledge> knowledgeSet) {
        this.knowledgeSet = knowledgeSet;
    }

    public Set<Knowledge> getKnowledgeSet() {
        return knowledgeSet;
    }

    public int getGameNum() {
        return gameNum;
    }

    public int getKnowledgeNum() {
        return knowledgeNum;
    }

    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }

    public void setKnowledgeNum(int knowledgeNum) {
        this.knowledgeNum = knowledgeNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return (int)(id % Integer.MAX_VALUE);
    }
}
