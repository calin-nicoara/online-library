package com.example.onlinelibrary.books;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book {

    @Id
    @GeneratedValue(generator = "BOOK_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="BOOK_SEQ_GEN", sequenceName = "BOOK_SEQ", allocationSize = 1)
    private Long id;

    private String title;

    private String author;
}
