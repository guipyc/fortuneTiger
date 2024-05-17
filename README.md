# Fortune Tiger

## Descrição do Projeto

Fortune Tiger é um jogo de caça-níqueis simples, onde jogadores podem girar os rolos para tentar ganhar prêmios. O projeto demonstra conceitos de Programação Orientada a Objetos (POO) em Java, incluindo encapsulamento, herança, polimorfismo e uso de exceções. Além disso, o projeto utiliza persistência de dados para salvar e carregar o estado dos jogadores.

## Requisitos Funcionais

1. **Gerenciamento de Jogadores (CRUD)**:
    - **Create**: Adicionar novos jogadores com nome e saldo inicial.
    - **Read**: Listar jogadores e verificar o saldo de um jogador específico.
    - **Update**: Atualizar o saldo dos jogadores conforme os resultados dos giros dos rolos.
    - **Delete**: Remover jogadores.

2. **Jogo de Caça-Níqueis**:
    - Girar os rolos e determinar se o jogador ganhou um prêmio com base nos símbolos alinhados.
    - Deduzir o valor da aposta do saldo do jogador.
    - Adicionar o prêmio ganho ao saldo do jogador.

3. **Persistência de Dados**:
    - Salvar os dados dos jogadores em um arquivo.
    - Carregar os dados dos jogadores de um arquivo ao iniciar o programa.

## Diagrama de Classes

![Diagrama de Classes](path/to/your/diagram.png)

## Estrutura do Projeto

```plaintext
FortuneTiger/
│
├── src/
│   ├── Jogador.java
│   ├── JogadorManager.java
│   ├── JogoFortuneTiger.java
│   ├── Persistencia.java
│   └── Main.java
│
├── jogadores.dat
└── README.md
