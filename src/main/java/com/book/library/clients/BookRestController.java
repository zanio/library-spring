package com.book.library.clients;

import com.book.library.assemblers.BookModelAssemblers;
import com.book.library.dao.BookDao;
import com.book.library.entities.Book;
import com.book.library.services.books.BookService;
import com.book.library.util.HelperClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/book")
public class BookRestController {
    private final BookModelAssemblers bookModelAssemblers;
    private final PagedResourcesAssembler<Book> bookPagedResourcesAssembler;
    private final BookService bookServiceImpl;

    public BookRestController(BookModelAssemblers bookModelAssemblers,
                              PagedResourcesAssembler<Book> bookPagedResourcesAssembler, BookService bookServiceImpl) {
        this.bookModelAssemblers = bookModelAssemblers;
        this.bookPagedResourcesAssembler = bookPagedResourcesAssembler;
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping(value = "{bookId}", produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getBookById(
            @PathVariable("bookId") @NotNull final String bookId
    ) {
        return HelperClass.generateResponse(
                "Service successfully retrieved " + "",
                HttpStatus.OK,
                bookModelAssemblers.toModel(
                        bookServiceImpl.findOne(Integer.parseInt(bookId))
                )
        );
    }

    @PostMapping(value = "", produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBook(
            @RequestBody @Valid final BookDao bookDao
    ) {
        return HelperClass.generateResponse(
                "Service successfully created " + "",
                HttpStatus.CREATED,
                bookModelAssemblers.toModel(
                        bookServiceImpl.create(bookDao)
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "{bookId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> updateBook(
            @PathVariable("bookId") @NotNull final String bookId,
            @RequestBody @NotNull final BookDao bookDao
    ) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return HelperClass.generateResponse(
                "Service successfully created " + "",
                HttpStatus.OK,
                bookModelAssemblers.toModel(
                        bookServiceImpl.update(bookDao,Integer.parseInt(bookId))
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/category", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> addBookCategory(
            @RequestParam("bookId") @NotNull final String bookId,
            @RequestParam("categoryId") @NotNull final String categoryId
    )  {
        return HelperClass.generateResponse(
                "Book successfully updated " + "",
                HttpStatus.OK,
                bookModelAssemblers.toModel(
                        bookServiceImpl.addBookToCategory(Integer.parseInt(bookId), Integer.parseInt(categoryId)
                )));

    }

    @GetMapping(value = "", produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getBooks(
            @PageableDefault(
                    sort = { "createdAt" },
                    direction = Sort.Direction.DESC
            ) final Pageable pageable
            ) {
        return HelperClass.generateResponse(
                "Books successfully retrieved " + "",
                HttpStatus.OK,
                bookPagedResourcesAssembler.toModel(
                        bookServiceImpl.listBooks(pageable),
                        bookModelAssemblers
                )
        );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "{bookId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteBook(
            @PathVariable("bookId") @NotNull final String bookId
    ) {
        bookServiceImpl.deleteBook(Integer.parseInt(bookId));

        return HelperClass.generateResponse(
                "Book item deleted successfully ",
                HttpStatus.OK,
                null
                );

    }
}
