package com.example.onlinelibrary.books;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookDTO {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;
}
