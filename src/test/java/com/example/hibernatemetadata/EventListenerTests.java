package com.example.hibernatemetadata;

import com.example.hibernatemetadata.entities.Book;
import com.example.hibernatemetadata.repositories.BookRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.startsWith;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMockMvc
@Transactional
public class EventListenerTests {

    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Autowired
    BookRepository bookRepository;

    @Test
    public void updateDoesNotThrow() {
        Book forInsert = new Book();
        forInsert.setAuthor("X");
        Book inserted = bookRepository.save(forInsert);
        assert inserted.getId() != null;

        inserted.setAuthor("Y");
        bookRepository.saveAndFlush(inserted);
    }

    @Test
    public void updateThrows() {
        Book forInsert = new Book();
        forInsert.setName("foobar");
        Book inserted = bookRepository.save(forInsert);
        assert inserted.getId() != null;

        inserted.setName("newName");
        ex.expectMessage(startsWith("Boo"));
        bookRepository.saveAndFlush(inserted);
    }

    @Test
    public void deleteThrows() {
        Book forInsert = new Book();
        forInsert.setName("foobar");
        Book inserted = bookRepository.saveAndFlush(forInsert);
        assert inserted.getId() != null;

        ex.expectMessage(startsWith("Cannot delete"));
        bookRepository.delete(inserted);
        bookRepository.flush();
    }

}
