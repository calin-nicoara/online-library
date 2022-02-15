package com.example.onlinelibrary.books;


import com.example.onlinelibrary.exceptions.NotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<BookDTO> findBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toBookDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDTO findBookById(final Long id) {
        return bookRepository.findById(id).map(BookMapper::toBookDTO)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + id));
    }

    public BookDTO createOrUpdateBook(final BookDTO bookDTO) {
        Book book = BookMapper.toBook(bookDTO);
        Book savedBook = bookRepository.save(book);

        return BookMapper.toBookDTO(savedBook);
    }

    public void deleteBook(final Long id) {
        bookRepository.deleteById(id);
    }
}
