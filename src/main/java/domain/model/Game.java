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
}
