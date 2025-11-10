package com.example.inmoprop.models;

import java.util.Date;

public class Pago {
    private int idPago;
    private Date fechaPago;
    private Double monto;
    private String detalle;
    private Boolean estado;
    private int idContrato;
    private Contrato contrato;

    public Pago(int idPago, Date fechaPago, Double monto, String detalle, Boolean estado, int idContrato, Contrato contrato) {
        this.idPago = idPago;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.detalle = detalle;
        this.estado = estado;
        this.idContrato = idContrato;
        this.contrato = contrato;
    }

    public Pago() {
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
