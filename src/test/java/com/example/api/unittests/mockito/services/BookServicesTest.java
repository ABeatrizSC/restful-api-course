package com.example.api.unittests.mockito.services;

import com.example.api.converter.mocks.MockBook;
import com.example.api.data.vo.v1.BookVO;
import com.example.api.exceptions.RequiredObjectIsNullException;
import com.example.api.model.Book;
import com.example.api.repositories.BookRepository;
import com.example.api.services.BookServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    // Mock que gera dados de teste para Book
    MockBook input;

    // Injeta a classe de serviço real, mas suas dependências serão mockadas
    @InjectMocks
    private BookServices service;

    // Mocka o repositório que seria usado no serviço
    @Mock
    BookRepository repository;

    // Executa antes de cada teste, inicializando os mocks
    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockBook();  // Cria uma instância do mock
        MockitoAnnotations.openMocks(this);  // Inicializa os mocks
    }

    @Test
    void testFindById() {
        Book entity = input.mockEntity(1);  // Cria uma entidade de exemplo
        entity.setId(1L);  // Define o ID da entidade

        when(repository.findById(1L)).thenReturn(Optional.of(entity));  // Simula o comportamento do repositório

        var result = service.findById(1L);  // Chama o serviço para buscar a entidade
        assertNotNull(result);  // Verifica se o resultado não é nulo
        assertNotNull(result.getKey());  // Verifica se a chave (ID) foi definida
        assertNotNull(result.getLinks());  // Verifica se os links foram gerados

        // Verifica se o link gerado é o esperado
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());  // Verifica se o autor está correto
        assertEquals("Some Title1", result.getTitle());  // Verifica se o título está correto
        assertEquals(25D, result.getPrice());  // Verifica se o preço está correto
        assertNotNull(result.getLaunchDate());  // Verifica se a data de lançamento foi definida
    }

    @Test
    void testCreate() {
        Book entity = input.mockEntity(1);  // Cria uma entidade mockada
        entity.setId(1L);  // Define o ID

        Book persisted = entity;  // Simula o objeto salvo no banco
        persisted.setId(1L);  // Define o ID

        BookVO vo = input.mockVO(1);  // Cria um VO de exemplo
        vo.setKey(1L);  // Define a chave do VO

        when(repository.save(entity)).thenReturn(persisted);  // Simula o comportamento do repositório ao salvar

        var result = service.create(vo);  // Chama o serviço para criar a entidade

        assertNotNull(result);  // Verifica se o resultado não é nulo
        assertNotNull(result.getKey());  // Verifica se a chave foi definida
        assertNotNull(result.getLinks());  // Verifica se os links foram gerados

        // Verifica se o link gerado é o esperado
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());  // Verifica se o autor está correto
        assertEquals("Some Title1", result.getTitle());  // Verifica se o título está correto
        assertEquals(25D, result.getPrice());  // Verifica se o preço está correto
        assertNotNull(result.getLaunchDate());  // Verifica se a data de lançamento foi definida
    }

    // Teste de criação com livro nulo (espera uma exceção)
    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);  // Testa se a exceção é lançada quando o argumento é nulo
        });

        String expectedMessage = "It is not allowed to persist a null object!";  // Mensagem esperada
        String actualMessage = exception.getMessage();  // Mensagem real da exceção

        assertTrue(actualMessage.contains(expectedMessage));  // Verifica se a mensagem corresponde
    }

    @Test
    void testUpdate() {
        Book entity = input.mockEntity(1);  // Cria uma entidade de exemplo

        Book persisted = entity;  // Simula o objeto salvo
        persisted.setId(1L);  // Define o ID

        BookVO vo = input.mockVO(1);  // Cria um VO mockado
        vo.setKey(1L);  // Define a chave

        when(repository.findById(1L)).thenReturn(Optional.of(entity));  // Simula a busca no repositório
        when(repository.save(entity)).thenReturn(persisted);  // Simula a operação de salvar

        var result = service.update(vo);  // Chama o serviço para atualizar a entidade

        assertNotNull(result);  // Verifica se o resultado não é nulo
        assertNotNull(result.getKey());  // Verifica se a chave foi definida
        assertNotNull(result.getLinks());  // Verifica se os links foram gerados

        // Verifica se o link gerado é o esperado
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());  // Verifica se o autor está correto
        assertEquals("Some Title1", result.getTitle());  // Verifica se o título está correto
        assertEquals(25D, result.getPrice());  // Verifica se o preço está correto
        assertNotNull(result.getLaunchDate());  // Verifica se a data de lançamento foi definida
    }

    // Teste de atualização com livro nulo (espera uma exceção)
    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);  // Testa se a exceção é lançada quando o argumento é nulo
        });

        String expectedMessage = "It is not allowed to persist a null object!";  // Mensagem esperada
        String actualMessage = exception.getMessage();  // Mensagem real da exceção

        assertTrue(actualMessage.contains(expectedMessage));  // Verifica se a mensagem corresponde
    }

    @Test
    void testDelete() {
        Book entity = input.mockEntity(1);  // Cria uma entidade de exemplo
        entity.setId(1L);  // Define o ID

        when(repository.findById(1L)).thenReturn(Optional.of(entity));  // Simula a busca no repositório

        service.delete(1L);  // Chama o serviço para deletar a entidade
    }

    @Test
    void testFindAll() {
        List<Book> list = input.mockEntityList();  // Cria uma lista mockada de livros

        when(repository.findAll()).thenReturn(list);  // Simula a busca de todos os livros no repositório

        var people = service.findAll();  // Chama o serviço para buscar todos

        assertNotNull(people);  // Verifica se o resultado não é nulo
        assertEquals(14, people.size());  // Verifica se o tamanho da lista é correto

        var bookOne = people.get(1);  // Verifica o primeiro livro

        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());  // Verifica se a chave foi definida
        assertNotNull(bookOne.getLinks());  // Verifica se os links foram gerados

        // Verifica se o link gerado é o esperado
        assertTrue(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", bookOne.getAuthor());  // Verifica se o autor está correto
        assertEquals("Some Title1", bookOne.getTitle());  // Verifica se o título está correto
        assertEquals(25D, bookOne.getPrice());  // Verifica se o preço está correto
        assertNotNull(bookOne.getLaunchDate());  // Verifica se a data de lançamento foi definida
    }
}
