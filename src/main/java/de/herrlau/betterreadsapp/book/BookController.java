package de.herrlau.betterreadsapp.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BookController {
    @Autowired
    BookRepository repository;

    private static final String coverUrlTemplate = "http://covers.openlibrary.org/b/id/%s-L.jpg";

    @GetMapping("/books/{bookId}")
    public ModelAndView getBook(@PathVariable String bookId, Model model) {
        Book book = repository
                .findById(bookId)
                .orElseThrow(BookNotFound::new);
        ModelAndView mav = new ModelAndView("book");
        mav.addObject("book", book);
        if (book.getCoverIds().size() > 0) {
            String coverUrl = String.format(coverUrlTemplate, book.getCoverIds().get(0));
            mav.addObject("coverUrl", coverUrl);
        }
        return mav;
    }

    private class BookNotFound extends RuntimeException {
    }
}
