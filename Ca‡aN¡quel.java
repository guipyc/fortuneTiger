import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Enumeração que representa os diferentes símbolos do caça-níquel
enum Simbolo {
    TIGRINHO,
    PEIXE,
    ALGA,
    CONCHA,
    ESTRELA_DO_MAR
}

// Enumeração que representa os diferentes tipos de prêmios que podem ser ganhos
enum Premio {
    JACKPOT,
    GRANDE_PREMIO,
    PREMIO_MEDIO,
    PREMIO_PEQUENO,
    SEM_PREMIO
}

// Classe que representa um rolo do caça-níquel
class Reel {
    private static final Simbolo[] SIMBOLOS = Simbolo.values();
    private Random random;

    public Reel() {
        this.random = new Random();
    }

    // Gira o rolo e retorna um símbolo aleatório
    public Simbolo girar() {
        return SIMBOLOS[random.nextInt(SIMBOLOS.length)];
    }
}

// Classe para gerar prêmios com base nos símbolos do caça-níquel
class PrizeGenerator {
    // Gera o prêmio com base nos símbolos dos rolos
    public Premio gerarPremio(Simbolo[][] rolos) {
        // Lógica para determinar o prêmio
        Random random = new Random();
        int numeroAleatorio = random.nextInt(100);

        if (numeroAleatorio < 1) { // 1% de chance
            return Premio.JACKPOT;
        } else if (numeroAleatorio < 5) { // 4% de chance
            return Premio.GRANDE_PREMIO;
        } else if (numeroAleatorio < 15) { // 10% de chance
            return Premio.PREMIO_MEDIO;
        } else if (numeroAleatorio < 40) { // 25% de chance
            return Premio.PREMIO_PEQUENO;
        } else { // 60% de chance
            return Premio.SEM_PREMIO;
        }
    }
}

// Classe que representa um jogador
class Jogador {
    private String nome;
    private String senha;
    private double saldo;
    private ArrayList<String> historico;

    public Jogador(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.saldo = 0;
        this.historico = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    // Método para depositar dinheiro na conta do jogador
    public void depositar(double valor) {
        saldo += valor;
        historico.add("Depósito de $" + valor);
    }

    // Método para sacar dinheiro da conta do jogador
    public void sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            historico.add("Saque de $" + valor);
        } else {
            throw new IllegalArgumentException("Saldo insuficiente para saque.");
        }
    }

    // Método para fazer uma aposta
    public void fazerAposta(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            historico.add("Aposta de $" + valor);
        } else {
            throw new IllegalArgumentException("Saldo insuficiente para fazer a aposta.");
        }
    }

    // Método para adicionar prêmio ao saldo do jogador
    public void adicionarPremio(double premio) {
        saldo += premio;
        historico.add("Prêmio de $" + premio);
    }

    // Método para imprimir o histórico de jogadas do jogador
    public void imprimirHistorico() {
        System.out.println("Histórico de jogadas de " + nome + ":");
        for (String jogada : historico) {
            System.out.println(jogada);
        }
    }

    // Método para verificar se a senha fornecida é correta
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }
}

// Classe principal que gerencia o jogo de caça-níquel
public class CaçaNíquel {
    private ArrayList<Jogador> jogadores;
    private Reel[] rolos;
    private PrizeGenerator prizeGenerator;
    private Jogador jogadorLogado;

    // Construtor que inicializa os jogadores, rolos e o gerador de prêmios
    public CaçaNíquel() {
        this.jogadores = new ArrayList<>();
        this.rolos = new Reel[3]; // 3 rolos para simplificar
        for (int i = 0; i < rolos.length; i++) {
            rolos[i] = new Reel();
        }
        this.prizeGenerator = new PrizeGenerator();
        this.jogadorLogado = null;
    }

    // Método para adicionar um novo jogador
    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    // Método para remover um jogador
    public void removerJogador(String nome) {
        jogadores.removeIf(j -> j.getNome().equals(nome));
    }

    // Método para buscar um jogador pelo nome
    public Jogador buscarJogador(String nome) {
        for (Jogador jogador : jogadores) {
            if (jogador.getNome().equals(nome)) {
                return jogador;
            }
        }
        return null;
    }

    // Método para listar todos os jogadores
    public void listarJogadores() {
        System.out.println("Lista de jogadores:");
        for (Jogador jogador : jogadores) {
            System.out.println(jogador.getNome());
        }
    }

    // Método para efetuar login de um jogador
    public void fazerLogin(String nome, String senha) {
        for (Jogador jogador : jogadores) {
            if (jogador.getNome().equals(nome) && jogador.verificarSenha(senha)) {
                jogadorLogado = jogador;
                System.out.println("Login realizado com sucesso para o jogador: " + jogador.getNome());
                return;
            }
        }
        System.out.println("Nome de usuário ou senha incorretos.");
    }

    // Método para efetuar logout do jogador logado
    public void fazerLogout() {
        jogadorLogado = null;
        System.out.println("Logout realizado com sucesso.");
    }

    // Verifica se há um jogador logado
    public boolean isJogadorLogado() {
        return jogadorLogado != null;
    }

    // Gira os rolos do caça-níquel e retorna os resultados
    public Simbolo[][] girar() {
        if (!isJogadorLogado()) {
            System.out.println("Faça login para jogar.");
            return null;
        }

                Simbolo[][] resultados = new Simbolo[3][3];
        for (int i = 0; i < rolos.length; i++) {
            for (int j = 0; j < 3; j++) {
                resultados[i][j] = rolos[i].girar();
            }
        }

        // Após girar, atualiza o saldo do jogador logado
        jogadorLogado.fazerAposta(1); // Aposta de $1 por giro
        System.out.println("Saldo atual: $" + jogadorLogado.getSaldo());

        // Imprime os resultados
        System.out.println("Resultado do giro:");
        for (int i = 0; i < resultados.length; i++) {
            for (int j = 0; j < resultados[i].length; j++) {
                System.out.print(resultados[i][j] + " ");
            }
            System.out.println();
        }

        // Verifica os prêmios ganhos
        Premio premio = verificarPremio(resultados);
        if (premio != Premio.SEM_PREMIO) {
            System.out.println("Parabéns! Você ganhou o prêmio: " + premio);
            switch (premio) {
                case JACKPOT:
                    jogadorLogado.adicionarPremio(100); // Adiciona $100 ao saldo
                    break;
                case GRANDE_PREMIO:
                    jogadorLogado.adicionarPremio(50); // Adiciona $50 ao saldo
                    break;
                case PREMIO_MEDIO:
                    jogadorLogado.adicionarPremio(20); // Adiciona $20 ao saldo
                    break;
                case PREMIO_PEQUENO:
                    jogadorLogado.adicionarPremio(10); // Adiciona $10 ao saldo
                    break;
                default:
                    break;
            }
            System.out.println("Novo saldo: $" + jogadorLogado.getSaldo());
        } else {
            System.out.println("Infelizmente, você não ganhou nenhum prêmio neste giro.");
        }

        return resultados;
    }

    // Verifica os prêmios ganhos
    public Premio verificarPremio(Simbolo[][] resultados) {
        // Verifica se todos os símbolos em uma linha são iguais
        for (int i = 0; i < resultados.length; i++) {
            if (resultados[i][0] == resultados[i][1] && resultados[i][1] == resultados[i][2]) {
                switch (resultados[i][0]) {
                    case TIGRINHO:
                        return Premio.JACKPOT;
                    case PEIXE:
                        return Premio.GRANDE_PREMIO;
                    case ALGA:
                        return Premio.PREMIO_MEDIO;
                    case CONCHA:
                        return Premio.PREMIO_PEQUENO;
                    default:
                        break;
                }
            }
        }

        // Verifica se todos os símbolos em uma coluna são iguais
        for (int j = 0; j < 3; j++) {
            if (resultados[0][j] == resultados[1][j] && resultados[1][j] == resultados[2][j]) {
                switch (resultados[0][j]) {
                    case TIGRINHO:
                        return Premio.JACKPOT;
                    case PEIXE:
                        return Premio.GRANDE_PREMIO;
                    case ALGA:
                        return Premio.PREMIO_MEDIO;
                    case CONCHA:
                        return Premio.PREMIO_PEQUENO;
                    default:
                        break;
                }
            }
        }

        // Verifica se há três símbolos iguais na diagonal principal
        if (resultados[0][0] == resultados[1][1] && resultados[1][1] == resultados[2][2]) {
            switch (resultados[0][0]) {
                case TIGRINHO:
                    return Premio.JACKPOT;
                case PEIXE:
                    return Premio.GRANDE_PREMIO;
                case ALGA:
                    return Premio.PREMIO_MEDIO;
                case CONCHA:
                    return Premio.PREMIO_PEQUENO;
                default:
                    break;
            }
        }

        // Verifica se há três símbolos iguais na diagonal secundária
        if (resultados[0][2] == resultados[1][1] && resultados[1][1] == resultados[2][0]) {
            switch (resultados[0][2]) {
                case TIGRINHO:
                    return Premio.JACKPOT;
                case PEIXE:
                    return Premio.GRANDE_PREMIO;
                case ALGA:
                    return Premio.PREMIO_MEDIO;
                case CONCHA:
                    return Premio.PREMIO_PEQUENO;
                default:
                    break;
            }
        }

        return Premio.SEM_PREMIO;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CaçaNíquel caçaNíquel = new CaçaNíquel();

        // Menu de opções
        int opcao;
        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Criar novo jogador");
            System.out.println("2. Remover jogador");
            System.out.println("3. Buscar jogador");
            System.out.println("4. Listar jogadores");
            System.out.println("5. Fazer login");
            System.out.println("6. Fazer logout");
            System.out.println("7. Girar");
            System.out.println("8. Ver saldo");
            System.out.println("9. Depositar");
            System.out.println("10. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do jogador: ");
                    String nomeJogador = scanner.nextLine();
                    System.out.print("Digite a senha do jogador: ");
                    String senha = scanner.nextLine();
                    Jogador novoJogador = new Jogador(nomeJogador, senha);
                    caçaNíquel.adicionarJogador(novoJogador);
                    System.out.println("Jogador criado com sucesso.");
                    break;
                case 2:
                    System.out.print("Digite o nome do jogador a ser removido: ");
                    String nomeRemover = scanner.nextLine();
                    caçaNíquel.removerJogador(nomeRemover);
                    System.out.println("Jogador removido com sucesso.");
                    break;
                case 3:
                    System.out.print("Digite o nome do jogador a ser buscado: ");
                    String nomeBuscar = scanner.nextLine();
                    Jogador jogadorBuscado = caçaNíquel.buscarJogador(nomeBuscar);
                    if (jogadorBuscado != null) {
                        System.out.println("Jogador encontrado: " + jogadorBuscado.getNome());
                    } else {
                        System.out.println("Jogador não encontrado.");
                    }
                    break;
                case 4:
                    caçaNíquel.listarJogadores();
                    break;
                case 5:
                    System.out.print("Digite o nome de usuário: ");
                    String nomeLogin = scanner.nextLine();
                    System.out.print("Digite a senha: ");
                    String senhaLogin = scanner.nextLine();
                    caçaNíquel.fazerLogin(nomeLogin, senhaLogin);
                    break;
                case 6:
                    caçaNíquel.fazerLogout();
                    break;
                case 7:
                    caçaNíquel.girar();
                    break;
                case 8:
                    if (caçaNíquel.isJogadorLogado()) {
                        System.out.println("Saldo atual: $" + caçaNíquel.jogadorLogado.getSaldo());
                    } else {
                        System.out.println("Faça login para ver o saldo.");
                    }
                    break;
                case 9:
                    if (caçaNíquel.isJogadorLogado()) {
                        System.out.print("Digite o valor a ser depositado: $");
                        double valorDeposito = scanner.nextDouble();
                        caçaNíquel.jogadorLogado.depositar(valorDeposito);
                        System.out.println("Depósito realizado com sucesso. Saldo atual: $" + caçaNíquel.jogadorLogado.getSaldo());
                    } else {
                        System.out.println("Faça login para depositar.");
                    }
                    break;
                case 10:
                    System.out.println("Obrigado por jogar!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 10);

        scanner.close();
    }
}