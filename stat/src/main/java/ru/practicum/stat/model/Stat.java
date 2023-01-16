package ru.practicum.stat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SqlResultSetMapping(
        name = "StatResult",
        classes = {
                @ConstructorResult(
                        targetClass = Stat.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "app", type = String.class),
                                @ColumnResult(name = "uri", type = String.class),
                                @ColumnResult(name = "hits", type = BigInteger.class)
                        })})
public class Stat {
    @Id
    @GeneratedValue(generator = "sequence")
    private Long id;
    private String app;//Название сервиса
    private String uri;//URI сервиса
    private BigInteger hits;//Количество просмотров
}
