package modelos;

public class Modelo_Ideia {
    private String ideiaId;
    private String ideiaTitulo;
    private String ideiaDescricao;
    private String ideiaImagemIdeia;
    private String ideiaWhatsApp;
    private String ideiaEmail;
    private String ideianomeUser;
    private int    ideiaCurtidas;
    private String ideiaImagemPerfil;
    private String ideiaIdUsuario;
    private String ideiaDataDaPub;


    public Modelo_Ideia(){

    }


    public Modelo_Ideia(String ideiaId, String ideiaTitulo, String ideiaDescricao, String ideiaImagemIdeia, String ideiaWhatsApp,String ideiaEmail,String ideianomeUser,int ideiaCurtidas, String ideiaImagemPerfil,String ideiaIdUsuario,String ideiaDataDaPub) {
        this.ideiaId = ideiaId;
        this.ideiaTitulo = ideiaTitulo;
        this.ideiaDescricao = ideiaDescricao;
        this.ideiaImagemIdeia = ideiaImagemIdeia;
        this.ideiaWhatsApp = ideiaWhatsApp;
        this.ideiaEmail = ideiaEmail;
        this.ideianomeUser = ideianomeUser;
        this.ideiaCurtidas = ideiaCurtidas;
        this.ideiaImagemPerfil = ideiaImagemPerfil;
        this.ideiaIdUsuario = ideiaIdUsuario;
        this.ideiaDataDaPub = ideiaDataDaPub;


    }




    // métodos getters e setters


    public String getIdeiaId() {
        return ideiaId;
    }

    public void setIdeiaId(String ideiaId) {
        this.ideiaId = ideiaId;
    }

    public String getIdeiaTitulo() {
        return ideiaTitulo;
    }

    public void setIdeiaTitulo(String ideiaTitulo) {
        this.ideiaTitulo = ideiaTitulo;
    }

    public String getIdeiaDescricao() {
        return ideiaDescricao;
    }

    public void setIdeiaDescricao(String ideiaDescricao) {
        this.ideiaDescricao = ideiaDescricao;
    }

    public String getIdeiaImagemIdeia() {
        return ideiaImagemIdeia;
    }

    public void setIdeiaImagemIdeia(String ideiaImagemIdeia) {
        this.ideiaImagemIdeia = ideiaImagemIdeia;
    }

    public String getIdeiaWhatsApp() {
        return ideiaWhatsApp;
    }

    public void setIdeiaWhatsApp(String ideiaWhatsApp) {
        this.ideiaWhatsApp = ideiaWhatsApp;
    }

    public String getIdeiaEmail() {
        return ideiaEmail;
    }

    public void setIdeiaEmail(String ideiaEmail) {
        this.ideiaEmail = ideiaEmail;
    }

    public String getIdeianomeUser() {
        return ideianomeUser;
    }

    public void setIdeianomeUser(String ideianomeUser) {
        this.ideianomeUser = ideianomeUser;
    }

    public int getIdeiaCurtidas() {
        return ideiaCurtidas;
    }

    public void setIdeiaCurtidas(int ideiaCurtidas) {
        this.ideiaCurtidas = ideiaCurtidas;
    }

    public String getIdeiaImagemPerfil() {
        return ideiaImagemPerfil;
    }

    public void setIdeiaImagemPerfil(String ideiaImagemPerfil) {
        this.ideiaImagemPerfil = ideiaImagemPerfil;
    }

    public String getIdeiaIdUsuario() {
        return ideiaIdUsuario;
    }

    public void setIdeiaIdUsuario(String ideiaIdUsuario) {
        this.ideiaIdUsuario = ideiaIdUsuario;
    }

    public String getIdeiaDataDaPub() {
        return ideiaDataDaPub;
    }

    public void setIdeiaDataDaPub(String ideiaDataDaPub) {
        this.ideiaDataDaPub = ideiaDataDaPub;
    }
}
