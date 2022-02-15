package com.example.onlinelibrary.books;

public final class BookMapper {

    private BookMapper() {}

    public static BookDTO toBookDTO(final Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }

    public static Book toBook(final BookDTO bookDTO) {
        return Book.builder()
                .id(bookDTO.getId())
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .build();
    }
}
