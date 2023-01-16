package ru.practicum.explorewithme.category.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "categories",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")}
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; //индивидуальный номер категории

    @Column(name = "name", nullable = false)
    private String name; //название категории
}
