package ru.job4j.url.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Table(name = "Website")
@Entity
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Не может быть Null")
    private int id;
    @NotEmpty(message = "Не может быть пустым полем")
    private String login;
    @NotEmpty(message = "Не может быть пустым полем")
    private String site;
    @NotBlank(message = "Не может быть пустым полем")
    @Size(min = 2)
    private String password;
}