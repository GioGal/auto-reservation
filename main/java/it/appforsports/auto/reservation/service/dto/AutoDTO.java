package it.appforsports.auto.reservation.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Auto entity.
 */
public class AutoDTO implements Serializable {

    private Long id;

    @NotNull
    private String marca;

    @NotNull
    private String modello;

    @NotNull
    private String cilindrata;

    @NotNull
    private String alimentazione;

    @NotNull
    private String colore;

    @NotNull
    private Integer annoImmatricolazione;

    private String statoPrenotazione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getCilindrata() {
        return cilindrata;
    }

    public void setCilindrata(String cilindrata) {
        this.cilindrata = cilindrata;
    }

    public String getAlimentazione() {
        return alimentazione;
    }

    public void setAlimentazione(String alimentazione) {
        this.alimentazione = alimentazione;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public Integer getAnnoImmatricolazione() {
        return annoImmatricolazione;
    }

    public void setAnnoImmatricolazione(Integer annoImmatricolazione) {
        this.annoImmatricolazione = annoImmatricolazione;
    }

    public String getStatoPrenotazione() {
        return statoPrenotazione;
    }

    public void setStatoPrenotazione(String statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutoDTO autoDTO = (AutoDTO) o;
        if(autoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutoDTO{" +
            "id=" + getId() +
            ", marca='" + getMarca() + "'" +
            ", modello='" + getModello() + "'" +
            ", cilindrata='" + getCilindrata() + "'" +
            ", alimentazione='" + getAlimentazione() + "'" +
            ", colore='" + getColore() + "'" +
            ", annoImmatricolazione='" + getAnnoImmatricolazione() + "'" +
            ", statoPrenotazione='" + getStatoPrenotazione() + "'" +
            "}";
    }
}
