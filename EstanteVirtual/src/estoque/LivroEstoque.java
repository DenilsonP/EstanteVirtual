package estoque;

import dao.LivroDAO;
import java.sql.SQLException;
import java.util.List;
import model.Livro;

public class LivroEstoque {
    private final LivroDAO livroDAO;

    public LivroEstoque() {
        this.livroDAO = new LivroDAO();
    }

    public boolean adicionarAoEstoque(String titulo, String autor, int paginas, String lido) throws SQLException, IllegalArgumentException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título não pode estar vazio.");
        }
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("O autor não pode estar vazio.");
        }
        if (paginas <= 0) {
            throw new IllegalArgumentException("O número de páginas deve ser maior que zero.");
        }

        Livro livro = new Livro(titulo.trim(), autor.trim(), paginas, lido);
        return livroDAO.inserir(livro);
    }

    public List<Livro> consultarEstoque() throws SQLException {
        return livroDAO.listarTodos();
    }
}