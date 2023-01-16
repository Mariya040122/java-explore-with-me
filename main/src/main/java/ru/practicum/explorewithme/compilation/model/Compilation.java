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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; //идентификатор

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "compilation")
    private List<Event> events = new ArrayList<>(); //список событий входящих в подборку


    @Column(name = "pinned", nullable = false)
    private Boolean pinned; //закреплена ли подборка на главной странице сайта

    @Column(name = "title", nullable = false)
    private String title; //заголовок подборки
}
