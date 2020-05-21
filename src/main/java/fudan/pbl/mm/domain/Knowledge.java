package fudan.pbl.mm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Knowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    @ManyToMany(mappedBy = "knowledgeSet")
    @JsonIgnore
    private Set<Pack> packSet;
    @ManyToMany(mappedBy = "knowledgeSet")
    @JsonIgnore
    private Set<User> userSet;

    public Knowledge(){}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Pack> getPackSet() {
        return packSet;
    }

    public void setPackSet(Set<Pack> packSet) {
        this.packSet = packSet;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knowledge knowledge = (Knowledge) o;
        return Objects.equals(content, knowledge.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
