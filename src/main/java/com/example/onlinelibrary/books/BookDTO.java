package com.example.onlinelibrary.books;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDTO {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;
}
