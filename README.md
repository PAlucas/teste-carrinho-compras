# teste-carrinho-compras

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

## Caminho de Funcionalidades

### Pricipais
- POST localhost:8081/Produto - adiciona produto
- Body
{ "description": "Revista" }
![image](https://user-images.githubusercontent.com/44738000/199131322-f86b4df6-12ca-4c54-9de5-ee97fc341bf5.png)
- PATCH localhost:8081/Pedido/{idItem}?idCart={idCart} - altera preco 
- Body {
    "Preco Total": "232.20",
    "Preco Unidade": "12.90",
    "Produto": "Produto Teste",
    "Quantidade": "18"
}
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












