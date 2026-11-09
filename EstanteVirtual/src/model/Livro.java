package model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private int paginas;
    private String lido; // "S" para Sim ou "N" para Não

    public Livro() {}

    public Livro(int id, String titulo, String autor, int paginas, String lido) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.lido = lido;
    }

    public Livro(String titulo, String autor, int paginas, String lido) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.lido = lido;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getPaginas() { return paginas; }
    public void setPaginas(int paginas) { this.paginas = paginas; }

    public String getLido() { return lido; }
    public void setLido(String lido) { this.lido = lido; }
}