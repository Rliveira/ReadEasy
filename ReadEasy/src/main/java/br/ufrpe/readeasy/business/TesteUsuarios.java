package br.ufrpe.readeasy.business;

import java.time.LocalDate;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ControladorUsuario;
import br.ufrpe.readeasy.business.IControladorUsuario;

public class TesteUsuarios {
    public static void main(String[] args) {
        Endereco endereco1 = new Endereco(11111, "Rua A", "Bairro X", "Cidade1", "Estado1");
        Endereco endereco2 = new Endereco(22222, "Rua B", "Bairro Y", "Cidade2", "Estado2");
        Endereco endereco3 = new Endereco(33333, "Rua C", "Bairro Z", "Cidade3", "Estado3");
        Endereco endereco4 = new Endereco(44444, "Rua D", "Bairro W", "Cidade4", "Estado4");
        Endereco endereco5 = new Endereco(55555, "Rua E", "Bairro V", "Cidade5", "Estado5");
        Endereco endereco6 = new Endereco(66666, "Rua F", "Bairro U", "Cidade6", "Estado6");
        Endereco endereco7 = new Endereco(77777, "Rua G", "Bairro T", "Cidade7", "Estado7");
        Endereco endereco8 = new Endereco(88888, "Rua H", "Bairro S", "Cidade8", "Estado8");
        Endereco endereco9 = new Endereco(99999, "Rua I", "Bairro R", "Cidade9", "Estado9");

        Usuario cliente1 = new Cliente("João", "123.456.789-01", LocalDate.of(1990, 5, 15),
                "joao123", "senha123", endereco1, "123456789");

        Usuario cliente2 = new Cliente("Maria", "987.654.321-09", LocalDate.of(1985, 8, 20),
                "maria456", "senha456", endereco2, "987654321");

        Usuario cliente3 = new Cliente("Pedro", "222.333.444-55", LocalDate.of(1982, 10, 8),
                "pedro555", "senha555", endereco3, "111223344");

        Usuario fornecedor1 = new Fornecedor("Nova Editora", "123.654.789-01", LocalDate.of(1990, 5, 15),
                "fornecedor1", "senha1", endereco4, "123456789", TipoFornecedor.EDITORA);

        Usuario fornecedor2 = new Fornecedor("Amanda Bonatti", "234.567.890-12", LocalDate.of(1985, 8, 20),
                "fornecedor2", "senha2", endereco5, "234567890", TipoFornecedor.ESCRITOR_INDEPENDENTE);

        Usuario admteste = new Funcionario("Lucas", "777.888.999-00", LocalDate.of(1988, 3, 5),
                "admin1", "senha3", endereco6, "777888999", true, null);

        Usuario adm1 = new Funcionario("Lucas", "777.888.999-00", LocalDate.of(1988, 3, 5),
                "admin1", "senha3", endereco6, "777888999", true, (Funcionario) admteste);

        Usuario adm2 = new Funcionario("Chris", "999.888.777-66", LocalDate.of(1982, 10, 8),
                "admin2", "senha4", endereco7, "999888777", true, (Funcionario) adm1);

        Usuario funcionario1 = new Funcionario("Peri", "111.222.333-44", LocalDate.of(1980, 1, 10),
                "funcionario1", "senha1", endereco8, "111222333", false, (Funcionario) adm1);

        Usuario funcionario2 = new Funcionario("Gustavo", "555.666.777-88", LocalDate.of(1995, 7, 20),
                "funcionario2", "senha2", endereco9, "555666777", false, (Funcionario) adm2);


        IControladorUsuario controladorUsuario = ControladorUsuario.getInstance();
        try {
            //Teste de cadastro de usuários
            controladorUsuario.cadastrarUsuario(cliente1);
            controladorUsuario.cadastrarUsuario(cliente2);
            //controladorUsuario.cadastrarUsuario(cliente2); //Usuário já cadastrado
            controladorUsuario.cadastrarUsuario(cliente3);
            controladorUsuario.cadastrarUsuario(fornecedor1);
            controladorUsuario.cadastrarUsuario(fornecedor2);
            controladorUsuario.cadastrarUsuario(adm1);
            controladorUsuario.cadastrarUsuario(adm2);
            controladorUsuario.cadastrarUsuario(funcionario1);
            controladorUsuario.cadastrarUsuario(funcionario2);
            System.out.println("Usuários cadastrados com sucesso!");

            System.out.println("*".repeat(50));
            System.out.println("Lista de usuários cadastrados:");
            System.out.println(controladorUsuario.listarUsuarios());
            System.out.println("*".repeat(50));

            //Teste de remoção de usuários
            controladorUsuario.removerUsuario(cliente3);
            //controladorUsuario.removerUsuario(cliente3); //Usuário não cadastrado
            controladorUsuario.removerUsuario(fornecedor2);
            controladorUsuario.removerUsuario(funcionario2);
            System.out.println("\nUsuários removidos com sucesso!");

            System.out.println("*".repeat(50));
            System.out.println("Lista de usuários cadastrados:");
            System.out.println(controladorUsuario.listarUsuarios());
            System.out.println("*".repeat(50));

            //Teste de atualização de usuários
            System.out.println("\nAtualizando usuários...");
            System.out.println(cliente2);
            controladorUsuario.atualizarCliente(cliente2, "Joana", "234.455.123-67", LocalDate.of(1985, 8, 20),
                    "maria456", "senha456", endereco3, "987654321");
            System.out.println("Atualização realizada com sucesso!");
            System.out.println(cliente2);
            System.out.println("*".repeat(50));
            System.out.println(adm1);
            controladorUsuario.atualizarFuncionario(adm1, "Admin1", "333.555.111-04", LocalDate.of(1988, 3, 5),
                    "admin1", "senha3", endereco6, "777888999", true, (Funcionario) adm1);
            System.out.println("Atualização realizada com sucesso!");
            System.out.println(adm1);
            //controladorUsuario.atualizarFornecedor(fornecedor1, "Fornecedor1", "123.456.789-01", LocalDate.of(1990, 5, 15),
            //"fornecedor1", "senha1", endereco4, "23467842460", TipoFornecedor.INSTITUICAO_PRIVADA);
            //Cpf a ser atualizado já existe
            System.out.println("*".repeat(50));
            System.out.println(adm1);
            System.out.println("*".repeat(50));

            //Teste de busca de usuários
            System.out.println("\nBuscando usuários...");
            System.out.println("*".repeat(50));
            System.out.println("Buscando usuário com CPF 123.456.789-01...");
            System.out.println(controladorUsuario.procurarUsuario("333.555.111-04")); //Usuário cadastrado
            System.out.println("*".repeat(50));
            System.out.println("Buscando usuário com CPF 987.654.321-09...");
            //System.out.println(controladorUsuario.procurarUsuario("987.654.321-09")); //Usuário não cadastrado
            System.out.println("*".repeat(50));
            System.out.println("Buscando usuário com CPF 222.333.444-55...");
            //System.out.println(controladorUsuario.procurarUsuario("222.333.444-55")); //Usuário removido
            System.out.println("*".repeat(50));

            //Adicionando endereços de entrega
            System.out.println("\nAdicionando endereços de entrega...");
            System.out.println("*".repeat(50));
            System.out.println("Adicionando endereço de entrega para cliente1...");
            controladorUsuario.adicionarEnderecoDeEntrega(cliente1, endereco1);
            //controladorUsuario.adicionarEnderecoDeEntrega(cliente1, endereco1); //Endereço já cadastrado
            System.out.println("*".repeat(50));
            System.out.println("Adicionando endereço de entrega para cliente2...");
            controladorUsuario.adicionarEnderecoDeEntrega(cliente2, endereco2);
            controladorUsuario.adicionarEnderecoDeEntrega(cliente2, endereco3);
            System.out.println("*".repeat(50));

            //Listando endereços de entrega
            System.out.println("\nListando endereços de entrega...");
            System.out.println("*".repeat(50));
            System.out.println("Listando endereços de entrega para cliente1...");
            controladorUsuario.listarEnderecosDeEntrega(cliente1);
            System.out.println("*".repeat(50));
            System.out.println("Listando endereços de entrega para cliente2...");
            controladorUsuario.listarEnderecosDeEntrega(cliente2);
            System.out.println("*".repeat(50));

            //Removendo endereços de entrega
            System.out.println("\nRemovendo endereços de entrega para cliente2...");
            System.out.println("*".repeat(50));
            controladorUsuario.removerEnderecoDeEntrega(cliente2, endereco2);
            //controladorUsuario.removerEnderecoDeEntrega(cliente2, endereco2); //Endereço não cadastrado
            System.out.println("*".repeat(50));
            controladorUsuario.listarEnderecosDeEntrega(cliente2);

            //Teste de listagem de usuários
            System.out.println("\nListando usuários...");
            System.out.println("*".repeat(50));
            System.out.println("Lista de usuários cadastrados:");
            System.out.println((controladorUsuario.listarUsuarios()));
            System.out.println("*".repeat(50));
            System.out.println("Lista de clientes cadastrados:");
            System.out.println(controladorUsuario.listarClientes());
            System.out.println("*".repeat(50));
            System.out.println("Lista de fornecedores cadastrados:");
            System.out.println(controladorUsuario.listarFornecedores());
            System.out.println("*".repeat(50));
            System.out.println("Lista de funcionários cadastrados:");
            System.out.println(controladorUsuario.listarFuncionarios());
            System.out.println("*".repeat(50));
            System.out.println("Lista de administradores cadastrados:");
            System.out.println(controladorUsuario.listarAdms());
            System.out.println("*".repeat(50));


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}