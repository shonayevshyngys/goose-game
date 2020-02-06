package domain.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "finished_at")
    private Date finished_at;

    @Column(name = "winner")
    private String username;

    public Game() {
    }

    public Game(Date created_at, Date finished_at) {
        this.created_at = created_at;
        this.finished_at = finished_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(Date finished_at) {
        this.finished_at = finished_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
