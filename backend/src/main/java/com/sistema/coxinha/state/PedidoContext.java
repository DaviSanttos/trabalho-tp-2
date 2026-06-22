package com.sistema.coxinha.state;

public class PedidoContext {
    private PedidoState state;

    public PedidoContext() {
        this.state = new PendenteState();
    }

    public PedidoContext(String status) {
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

    public void setState(PedidoState state) {
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
