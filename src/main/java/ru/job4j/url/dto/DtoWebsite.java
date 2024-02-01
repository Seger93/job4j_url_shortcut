package ru.job4j.url.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoWebsite {

    @NotEmpty(message = "Не может быть пустым полем")
    private String login;
    private boolean site;
    @NotBlank(message = "Не может быть пустым полем")
    @Size(min = 2)
    private String password;
}