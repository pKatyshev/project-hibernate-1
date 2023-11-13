package com.game.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;


@NamedQuery(name = "Player_GetAllCount",
        query = "SELECT count(p) FROM Player p")

@Getter
@Setter
@ToString
@NoArgsConstructor

@Entity
@Table(name = "player", schema = "rpg")
public class Player {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "race", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Race race;

    @Column(name = "profession", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Profession profession;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Column(name = "banned", nullable = false)
    private Boolean banned;

    @Column(name = "level", nullable = false)
    private Integer level;

    public Player(Long id, String name, String title, Race race, Profession profession, Date birthday, Boolean banned, Integer level) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.banned = banned;
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Player player = (Player) o;
        return id != null && Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}