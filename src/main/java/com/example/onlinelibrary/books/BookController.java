package com.example.onlinelibrary.books;

import com.example.onlinelibrary.login.LoginService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> findBooks(@RequestHeader("X-CUSTOM-AUTH-TOKEN") String customAuthToken) {
        loginService.validateToken(customAuthToken);

        return ResponseEntity.ok(bookService.findBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findBookById(@PathVariable("id") Long id,
                                                @RequestHeader("X-CUSTOM-AUTH-TOKEN") String customAuthToken) {
        loginService.validateToken(customAuthToken);

        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO bookDTO,
                                              @RequestHeader("X-CUSTOM-AUTH-TOKEN") String customAuthToken) {
        loginService.validateToken(customAuthToken);

        return ResponseEntity.ok(bookService.createOrUpdateBook(bookDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id,
                                                      @RequestBody @Valid BookDTO bookDTO,
                                              @RequestHeader("X-CUSTOM-AUTH-TOKEN") String customAuthToken) {
        loginService.validateToken(customAuthToken);

        bookDTO.setId(id);
        return ResponseEntity.ok(bookService.createOrUpdateBook(bookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id,
                                           @RequestHeader("X-CUSTOM-AUTH-TOKEN") String customAuthToken) {
        loginService.validateToken(customAuthToken);

        bookService.deleteBook(id);

        return ResponseEntity.ok().build();
    }
}
