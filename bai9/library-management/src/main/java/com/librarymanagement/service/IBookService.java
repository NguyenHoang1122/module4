package com.librarymanagement.service;

import com.librarymanagement.exception.InvalidBorrowCodeException;
import com.librarymanagement.exception.OutOfBookException;
import com.librarymanagement.model.Book;

public interface IBookService extends IGenerateService<Book> {
    String borrowBook(Long id) throws OutOfBookException;

    void returnBook(String code) throws InvalidBorrowCodeException;
}
