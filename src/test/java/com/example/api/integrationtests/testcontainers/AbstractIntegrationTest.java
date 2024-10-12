package com.example.api.integrationtests.testcontainers;

// Importa classes necessárias para inicialização do contexto de aplicação e manipulação de propriedades de ambiente.
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

// Anota a classe para indicar que o Spring deve usar o `Initializer` especificado ao configurar o contexto de teste.
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    // Classe estática interna que inicializa o contexto da aplicação.
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        // Cria uma instância de MySQLContainer que irá simular um banco de dados MySQL 8.0.29 dentro de um container.
        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.29");

        // Metodo privado que inicia o container MySQL utilizando Startables para iniciar em paralelo, se necessário.
        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();  // Inicia o container do MySQL.
        }

        // Metodo que cria um mapa de propriedades contendo a configuração de conexão com o banco de dados no container.
        private static Map<String, String> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", mysql.getJdbcUrl(),       // Configura a URL de conexão JDBC do container MySQL.
                    "spring.datasource.username", mysql.getUsername(), // Configura o nome de usuário do banco de dados.
                    "spring.datasource.password", mysql.getPassword()  // Configura a senha do banco de dados.
            );
        }

        // Metodo que inicializa o contexto da aplicação.
        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();  // Inicia o container do MySQL.

            // Obtém o ambiente configurável do contexto de aplicação.
            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            // Cria uma nova fonte de propriedades com as configurações de conexão do container.
            MapPropertySource testcontainers = new MapPropertySource(
                    "testcontainers",
                    (Map) createConnectionConfiguration());

            // Adiciona as propriedades do container ao início da lista de propriedades do ambiente.
            environment.getPropertySources().addFirst(testcontainers);
        }
    }
}
