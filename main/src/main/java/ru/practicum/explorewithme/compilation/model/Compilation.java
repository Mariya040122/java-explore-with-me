package ru.practicum.explorewithme.compilation.model;

import ru.practicum.explorewithme.event.model.Event;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "compilations")
public class Compilation {

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "compilation")
    List<Event> events = new ArrayList<>(); //список событий входящих в подборку

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id; //идентификатор

    @Column(name = "pinned", nullable = false)
    Boolean pinned; //закреплена ли подборка на главной странице сайта

    @Column(name = "title", nullable = false)
    String title; //заголовок подборки
}
