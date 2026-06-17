package com.sistema.coxinha.state;

public class MovimentacaoContext {
    private MovimentacaoState state;

    public MovimentacaoContext() {
        this.state = new PendenteState();
    }

    public MovimentacaoContext(String status) {
        switch (status) {
            case "PENDENTE":
                this.state = new PendenteState();
                break;
            case "CONFIRMADA":
                this.state = new ConfirmadaState();
                break;
            case "ESTORNADA":
                this.state = new EstornadaState();
                break;
            case "CANCELADA":
                this.state = new CanceladaState();
                break;
            default:
                this.state = new PendenteState();
        }
    }

    public MovimentacaoState getState() {
        return state;
    }

    public void setState(MovimentacaoState state) {
        this.state = state;
    }

    public String getStatus() {
        return state.getStatus();
    }

    public void confirmar() {
        state.confirmar(this);
    }

    public void estornar() {
        state.estornar(this);
    }

    public void cancelar() {
        state.cancelar(this);
    }
}
