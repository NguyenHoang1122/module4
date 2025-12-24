// src/main/java/com/librarymanagement/service/BookService.java
package com.librarymanagement.service;

import com.librarymanagement.exception.InvalidBorrowCodeException;
import com.librarymanagement.exception.OutOfBookException;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.BorrowCode;
import com.librarymanagement.repository.IBookRepository;
import com.librarymanagement.repository.IBorrowCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IBorrowCodeRepository borrowCodeRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void remove(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public String borrowBook(Long id) throws OutOfBookException {
        Book book = bookRepository.findById(id).orElseThrow(OutOfBookException::new);

        if (book.getQuantity() == null || book.getQuantity() <= 0) {
            throw new OutOfBookException();
        }

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        String code;
        int attempts = 0;
        do {
            code = String.valueOf((int) (Math.random() * 90000) + 10000);
            attempts++;
        } while (borrowCodeRepository.findByCode(code).isPresent() && attempts < 5);

        BorrowCode borrowCode = new BorrowCode();
        borrowCode.setCode(code);
        borrowCode.setBook(book);
        borrowCode.setReturned(false);

        borrowCodeRepository.save(borrowCode);

        return code;
    }

    @Override
    public void returnBook(String code) throws InvalidBorrowCodeException {
        BorrowCode borrowCode = borrowCodeRepository.findByCode(code).orElseThrow(InvalidBorrowCodeException::new);

        if (borrowCode.isReturned()) {
            throw new InvalidBorrowCodeException();
        }

        borrowCode.setReturned(true);

        Book book = borrowCode.getBook();
        if (book.getQuantity() == null) {
            book.setQuantity(1);
        } else {
            book.setQuantity(book.getQuantity() + 1);
        }

        bookRepository.save(book);
        borrowCodeRepository.save(borrowCode);
    }
}
