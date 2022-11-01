# teste-carrinho-compras

# Estrutura do Documento
- [Introdução](#intodução)
- [Tecnologias](#tecnologias)
  - [Linguagem](#linguagem)
  - [Gerenciador de dependências](#gerenciador-de-dependências)
  - [Banco de dados](#banco-de-dados)
  - [Spring](#spring)
  - [Dependencia de conexão com oracle](#dependencia-de-conexão-com-oracle)
- [Como iniciar o projeto](#como-iniciar-o-projeto)
  - [Manualmente](#manualmente)
  - [Oracle com docker](#oracle-com-docker)
- [Caminho de Funcionalidades](#caminho-de-funcionalidades)
  - [Principais](#principais)
  - [Cliente](#cliente)
  - [Carrinho](#carrinho)
  - [Produto](#produto)
  - [Item](#item)
- [Rotina de testes](#rotina-de-testes)
- [Introdução](#introdução)
- [Classes](#classes)
- [Rest](#rest)
- [Factory](#factory)
  

## Intodução

Aplicação que visa simular o funcionamento de um carrinho de e-commerce, funcionalidades:
- adicionar clientes
- mudar nome de clientes
- adicionar carrinho para os clientes(cada cliente podeter apenas um carrinho) 
- deletar carrinho de cliente
- Ver total de todos os carrinhos
- adicionar itens no carrinho
- deletar itens 
- mudar preco itens

## Tecnologias

### Linguagem 
- Java Versão 17
### Gerenciador de dependências
- Maven
### Banco de dados
- oracle-xe-11g
### Spring
- spring booot
- jpa
- jbdc
### Dependencia de conexão com oracle
- ojdbc8

## Como iniciar o projeto

### Manualmente 
1. git clone https://github.com/PAlucas/teste-carrinho-compras.git
2. baixar oracle https://drive.google.com/drive/folders/1ChH_0sXlkww9113xv5vT-HjCJVVSpzqT?usp=sharing
(neste link tem o tutorial de instalação e o executável do oracle 11g)
3. no arquivo application.properties(path:src\main\resources\application.properties)
#spring.jpa.hibernate.ddl-auto = update | comentar essa linha
spring.jpa.hibernate.ddl-auto=create | descomentar essa linha
4. rodar aplicação spring mvn spring-boot:run(certificar que não tem nenhum processo na porta 8081 ou 1521(onde roda o oracle))

### Oracle com docker

Eu criei uma imagem com o banco de dados feito e com alguns registros, o que deixa mais fácil o teste da aplicação.
1. git clone https://github.com/PAlucas/teste-carrinho-compras.git
2. docker pull lucaslotti/bancodedados-carrinho:bd_carrinho
3. docker run -d --name carrinho_bd -p 8082:1521 lucaslotti/bancodedados-carrinho:bd_carrinho
4. no arquivo application.properties(path:src\main\resources\application.properties)
server.port=8081

spring.datasource.url= jdbc:oracle:thin:@//localhost:8082:1521/xe
spring.datasource.username=SYSTEM   
spring.datasource.password=oracle
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.database-platform=org.hibernate.dialect.Oracle12cDialect
spring.jpa.hibernate.use-new-id-generator-mappings=false
spring.jpa.properties.hibernate.format_sql = true
spring.jpa.hibernate.ddl-auto = update
#spring.jpa.hibernate.ddl-auto=create

5.rodar aplicação spring mvn spring-boot:run(certificar que não tem nenhum processo na porta 8081 ou 8082(onde roda o oracle))
## Caminho de Funcionalidades

### Principais
- POST localhost:8081/Produto - adiciona produto
- Body
{ "description": "Revista" }
![image](https://user-images.githubusercontent.com/44738000/199131322-f86b4df6-12ca-4c54-9de5-ee97fc341bf5.png)
- PATCH localhost:8081/Pedido/{idItem}?idCart={idCart} - altera preco 
- Body { "unitPrice": "12.90"}
![image](https://user-images.githubusercontent.com/44738000/199131863-54f10efa-1761-4a84-8526-fe97a9048f88.png)
- POST localhost:8081/Carrinho?idCliente={idClient} - cria carrinho
- Obs: se o cliente tiver um carrinho não vai ser criado um novo, vai retornar o carrinho que ele possui.
![image](https://user-images.githubusercontent.com/44738000/199132852-022331f6-b9ff-4696-a648-1df26e97e708.png)

### Cliente
- POST localhost:8081/Cliente - adiciona cliente
- Body
{ "nome": "Lucas Lotti" }
![image](https://user-images.githubusercontent.com/44738000/199133124-5241066e-a741-4059-8934-5b975b576978.png)

- GET localhost:8081/Cliente - mostra todos os clientes sem mostrar o id
![image](https://user-images.githubusercontent.com/44738000/199133236-731ac336-5bd0-4821-b32f-0864876efb4c.png)

- GET localhost:8081/Cliente/Id - mostra todos os clientes e os respectivos Ids
![image](https://user-images.githubusercontent.com/44738000/199133339-1b6c2199-ac2e-4c9e-9908-00fc3f82fc80.png)

- PATCH localhost:8081/Cliente/{client_id}
- body
{ "nome": "Lucas Lotti Trocado" }
![image](https://user-images.githubusercontent.com/44738000/199133538-695f597c-4a6f-401b-9851-a2760a6f8188.png)

- DELETE localhost:8081/Cliente/{client_id}
- Obs: se o cliente tiver carrinho deleta o carrinho, se o cliente tiver carrinho e itens deleta os itens e o carrinho.
![image](https://user-images.githubusercontent.com/44738000/199133664-a29be78b-459d-4ee7-b6c8-2d60833eef32.png)

### Carrinho
- POST localhost:8081/Carrinho?idCliente={idClient} - cria carrinho
- Obs: se o cliente tiver um carrinho não vai ser criado um novo, vai retornar o carrinho que ele possui
- Criando um carrinho novo:
![image](https://user-images.githubusercontent.com/44738000/199134044-560758e8-7106-4588-895f-43b5f6ac97aa.png)
- Carrinho que possui
![image](https://user-images.githubusercontent.com/44738000/199134177-b0151f31-32c2-47fd-8300-0a9fe7decc9b.png)

- GET localhost:8081/Carrinho/{idClient} - mostra itens dentro do carrinho do cliente
![image](https://user-images.githubusercontent.com/44738000/199134472-149077a7-d283-4e9e-8ce8-74448b172a7a.png)

- GET localhost:8081/Carrinho/Total - Mostra o valor total 
- Obs: retorna o valor total da soma de todos os carrinho aproximando o valor de dois.
![image](https://user-images.githubusercontent.com/44738000/199135117-e8318146-d916-4fd4-85a6-e09f5e294ae7.png)

- GET localhost:8081/Carrinho - mostra todos os carrinho id do carrinho e id do cliente
- obs: criado para facilitar no teste da aplicação
![image](https://user-images.githubusercontent.com/44738000/199135597-c9b30f8b-1cd4-4a34-8c3e-6d335a143e2d.png)

- DELETE localhost:8081/Carrinho?idCliente={idClient} - deletar carrinho do cliente especificado
- obs: ao deletar o carrinho todos os itens dentro dele serão deletados
![image](https://user-images.githubusercontent.com/44738000/199135751-df479a9c-3ab3-4851-9307-a0e009fcb8df.png)
- return true foi deletado, false não foi deletado

### Produto 
- POST localhost:8081/Produto - adiciona produto
- Body
{ "description": "Livro" }
![image](https://user-images.githubusercontent.com/44738000/199137281-1c6754c0-9a70-43a3-9d08-66a67a4de5e6.png)

- Get localhost:8081/Produto - lista de todos os produtos
- ![image](https://user-images.githubusercontent.com/44738000/199137438-e486a2af-d5ea-4516-9890-e4ab1f0b8a67.png)

### Item
- POST localhost:8081/Item?idCart={idShoppingCart} - insere novo item no carrinho que foi passado como parâmetro
- Body
{ "code": "10", "quantity": "4", "unitPrice": "10"}
![image](https://user-images.githubusercontent.com/44738000/199137939-1047678f-af44-4c3a-b7f9-48f6a5b10ce1.png)

- DELETE localhost:8081/Item/{itemId}?idCart={idShoppingCart} - delete item em determinado carrinho
- ![image](https://user-images.githubusercontent.com/44738000/199138250-40a45ba0-3137-406d-b9c9-ad97badd1b10.png)

- Get localhost:8081/Item - mostra todos os itens com todas informações(endpoint criado com o intuito de facilitar testes)
- ![image](https://user-images.githubusercontent.com/44738000/199138435-9bff8749-46bc-4ad5-b8d3-333c32134f5f.png)

- PATCH localhost:8081/Pedido/{idItem}?idCart={idCart} - altera preco de item
- Body { "unitPrice": "12.90"}
![image](https://user-images.githubusercontent.com/44738000/199138595-0f6a03b6-9ce6-4a5d-bb07-65e1ebfbee12.png)

## Rotina de testes

### Introdução
Separei os testes em 3 partes o testes em classes que estão desempenhando funcionalidades básicas, o testes rest que se referem a funções do jpa que são de buscar no banco de dados, de salvar no banco de dados e etc e por último os testes mock que são testes que avaliam o status e o retorno dos endpoints.

#### Classes 
Os testes ficam nessa pasta: src\test\java\br\com\geofusion\testecarrinhocompras\classes
-Testam as classes:
- Client
- Item
- Product
- ShoppingCart

#### Rest
Os testes ficam nessa pasta src\test\java\br\com\geofusion\testecarrinhocompras\Rest
-Testam as classes:
- ClientModel
- ClientService
- ClientDto
- ClientRepository
- ItemModel
- ItemService
- ItemDto
- ItemRepository
- ProductModel
- ProductService
- ProductDto
- Product
- ShoppingCartModel
- ShoppingCartService
- ShoppingCartDto
- ShoppingCartRepository

#### Factory
Esses são os testes mock sao os testes que envolvem mais funcionalidades do sistema e são os testes mais complexos, eles normalmente funcionam sem a aplicação estar rodando mas pode pedir para a aplicação rodar.
Os testes ficam nessa pasta src\test\java\br\com\geofusion\testecarrinhocompras\Factory.
-Testam principalmente as classes:
- ClientControllerFactory
- ItemControllerFactory
- ProductControllerFactory
- ShoppingCartControllerFactory















