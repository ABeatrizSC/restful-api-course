package com.example.api.converter.mocks;

import com.example.api.data.vo.v1.BookVO;
import com.example.api.model.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {

    // Metodo para criar uma instância simulada de um Book com valores padrão (usando o índice 0)
    public Book mockEntity() {
        return mockEntity(0);
    }

    // Metodo para criar uma instância simulada de um BookVO com valores padrão (usando o índice 0)
    public BookVO mockVO() {
        return mockVO(0);
    }

    // Metodo para criar uma lista simulada de 14 Books com valores diferentes baseados no índice
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>(); // Cria uma nova lista de Books
        for (int i = 0; i < 14; i++) { // Itera 14 vezes, criando 14 instâncias simuladas de Book
            books.add(mockEntity(i)); // Adiciona cada instância de Book à lista
        }
        return books; // Retorna a lista de Books simulados
    }

    // Metodo para criar uma lista simulada de 14 BookVOs com valores diferentes baseados no índice
    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>(); // Cria uma nova lista de BookVOs
        for (int i = 0; i < 14; i++) { // Itera 14 vezes, criando 14 instâncias simuladas de BookVO
            books.add(mockVO(i)); // Adiciona cada instância de BookVO à lista
        }
        return books; // Retorna a lista de BookVOs simulados
    }

    // Metodo para criar uma instância simulada de Book com valores customizados pelo índice
    public Book mockEntity(Integer number) {
        Book book = new Book(); // Cria uma nova instância de Book
        book.setId(number.longValue()); // Define o ID do Book com base no índice
        book.setAuthor("Some Author" + number); // Define o autor com base no índice
        book.setLaunchDate(new Date()); // Define a data de lançamento como a data atual
        book.setPrice(25D); // Define o preço como 25
        book.setTitle("Some Title" + number); // Define o título com base no índice
        return book; // Retorna a instância simulada de Book
    }

    // Metodo para criar uma instância simulada de BookVO com valores customizados pelo índice
    public BookVO mockVO(Integer number) {
        BookVO book = new BookVO(); // Cria uma nova instância de BookVO
        book.setKey(number.longValue()); // Define a chave (Key) do BookVO com base no índice
        book.setAuthor("Some Author" + number); // Define o autor com base no índice
        book.setLaunchDate(new Date()); // Define a data de lançamento como a data atual
        book.setPrice(25D); // Define o preço como 25
        book.setTitle("Some Title" + number); // Define o título com base no índice
        return book; // Retorna a instância simulada de BookVO
    }
}
