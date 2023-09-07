package sv.edu.udb.beans;

public class ResultadoLogin {
    private boolean autenticado;
    private String nombreApellido;

    public ResultadoLogin(boolean autenticado, String nombreApellido) {
        this.autenticado = autenticado;
        this.nombreApellido = nombreApellido;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }
}