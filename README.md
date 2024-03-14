# Biblioteca Dapi, para Requisições HTTP

Esta é uma biblioteca Java desenvolvida para simplificar a realização de requisições HTTP utilizando os métodos GET, POST, PUT e DELETE. Ela oferece uma interface fácil de usar para interagir com APIs RESTful e executar operações básicas de HTTP.

## Funcionalidades

- Realização de requisições HTTP utilizando os métodos GET, POST, PUT e DELETE.
- Suporte para personalização dos cabeçalhos HTTP.
- Possibilidade de envio de corpo de requisição em formato JSON.
- Compatibilidade com Java 1.5 e versões posteriores.

## Exemplo de Uso

```java
import com.example.http.DapiOnConnect;
import com.example.http.DapiRequestManager;
import com.example.http.DapiRequestResponse;

public class Main {
    public static void main(String[] args) {
        // Configuração da URL base
        String baseURL = "https://api.example.com";

        // Instanciação do gerenciador de requisições com a URL base
        final DapiRequestManager requestManager = new DapiRequestManager(baseURL);

        // Definição do manipulador de conexão para personalização dos cabeçalhos
        requestManager.setOnConnect(new DapiOnConnect() {
            public void accept(HttpsURLConnection connection) {
                connection.addRequestProperty("HEADER", "value"); // Adiciona cabeçalho personalizado
                // Por padrão, o cabeçalho "Content-Type" é "application/json", mas pode ser modificado aqui
            }
        });

        // Exemplo de requisição GET
        final DapiRequestResponse response = requestManager.get("/endpoint");
        System.out.println(response.getBody());

        // Exemplo de requisição POST com corpo JSON
        String jsonBody = "{\"key\": \"value\"}";
        final DapiRequestResponse response2 = requestManager.post("/endpoint", jsonBody);
        System.out.println(response2.getCode());
    }
}
```

## Como Utilizar

1. Faça o download do arquivo JAR da biblioteca em /output.
2. Adicione o arquivo JAR ao classpath do seu projeto Java.
3. Importe as classes necessárias no seu código.

```java
import com.example.http.DapiOnConnect;
import com.example.http.DapiRequestManager;
import com.example.http.DapiRequestResponse;
```

4. Utilize as instâncias de `DapiRequestManager` para realizar as requisições HTTP conforme necessário, configurando os manipuladores de conexão, se necessário.
