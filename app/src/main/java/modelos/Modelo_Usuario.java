package modelos;

public class Modelo_Usuario {
    private String usuarioNome;
    private String usuarioEmail;
    private String usuarioSenha;
    private String usuarioDataNascimento;
    private String usuarioImagemPerfil;
    private String usuarioId;

    public Modelo_Usuario(){

    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioSenha() {
        return usuarioSenha;
    }

    public void setUsuarioSenha(String usuarioSenha) {
        this.usuarioSenha = usuarioSenha;
    }

    public String getUsuarioDataNascimento() {
        return usuarioDataNascimento;
    }

    public void setUsuarioDataNascimento(String usuarioDataNascimento) {
        this.usuarioDataNascimento = usuarioDataNascimento;
    }

    public String getUsuarioImagemPerfil() {
        return usuarioImagemPerfil;
    }

    public void setUsuarioImagemPerfil(String usuarioImagemPerfil) {
        this.usuarioImagemPerfil = usuarioImagemPerfil;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}
