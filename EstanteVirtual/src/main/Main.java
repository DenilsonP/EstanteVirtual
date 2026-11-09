package main;

import estoque.LivroEstoque;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.Livro;

public class Main {
    public static void main(String[] args) {
        LivroEstoque estoque = new LivroEstoque();
        boolean executando = true;

        while (executando) {
            String menu = """
                === ESTANTE VIRTUAL / ESTOQUE ===
                1. Cadastrar Novo Livro
                2. Ver Minha Estante
                3. Sair
                
                Escolha uma opção:
                """;

            String opcaoStr = JOptionPane.showInputDialog(null, menu, "Menu Principal", JOptionPane.QUESTION_MESSAGE);

            if (opcaoStr == null) {
                break;
            }

            switch (opcaoStr.trim()) {
                case "1" -> cadastrarLivroView(estoque);
                case "2" -> exibirEstanteView(estoque);
                case "3" -> {
                    JOptionPane.showMessageDialog(null, "Obrigado por utilizar a Estante Virtual!", "Encerrando", JOptionPane.INFORMATION_MESSAGE);
                    executando = false;
                }
                default -> JOptionPane.showMessageDialog(null, "Opção inválida! Escolha 1, 2 ou 3.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private static void cadastrarLivroView(LivroEstoque estoque) {
        try {
            String titulo = JOptionPane.showInputDialog(null, "Título do livro:", "Novo Cadastro", JOptionPane.QUESTION_MESSAGE);
            if (titulo == null) return;

            String autor = JOptionPane.showInputDialog(null, "Nome do autor:", "Novo Cadastro", JOptionPane.QUESTION_MESSAGE);
            if (autor == null) return;

            String paginasStr = JOptionPane.showInputDialog(null, "Quantidade de páginas:", "Novo Cadastro", JOptionPane.QUESTION_MESSAGE);
            if (paginasStr == null) return;

            int paginas = Integer.parseInt(paginasStr.trim());

            int respostaLido = JOptionPane.showConfirmDialog(
                null,
                "Já concluiu a leitura deste livro?",
                "Status de Leitura",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            String lido = (respostaLido == JOptionPane.YES_OPTION) ? "S" : "N";

            if (estoque.adicionarAoEstoque(titulo, autor, paginas, lido)) {
                JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: Insira apenas números inteiros no campo de páginas.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Erro de Validação: " + e.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na Base de Dados: " + e.getMessage(), "Erro JDBC", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void exibirEstanteView(LivroEstoque estoque) {
        try {
            List<Livro> estante = estoque.consultarEstoque();

            if (estante.isEmpty()) {
                JOptionPane.showMessageDialog(null, "A sua estante virtual está vazia.", "Estante Virtual", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder relatorio = new StringBuilder("=== RELATÓRIO DA ESTANTE ===\n\n");
            int totalConcluidos = 0;

            for (Livro l : estante) {
                boolean concluido = "S".equalsIgnoreCase(l.getLido());
                if (concluido) totalConcluidos++;

                relatorio.append("ID: ").append(l.getId()).append("\n")
                         .append("Título: ").append(l.getTitulo()).append("\n")
                         .append("Autor: ").append(l.getAutor()).append("\n")
                         .append("Páginas: ").append(l.getPaginas()).append("\n")
                         .append("Concluído: ").append(concluido ? "Sim" : "Não").append("\n")
                         .append("--------------------------------------\n");
            }

            relatorio.append("\n=== ESTATÍSTICAS ===\n")
                     .append("Total de Livros Cadastrados: ").append(estante.size()).append("\n")
                     .append("Total de Livros Concluídos: ").append(totalConcluidos);

            JOptionPane.showMessageDialog(null, relatorio.toString(), "Minha Estante", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar a base de dados: " + e.getMessage(), "Erro JDBC", JOptionPane.ERROR_MESSAGE);
        }
    }
}