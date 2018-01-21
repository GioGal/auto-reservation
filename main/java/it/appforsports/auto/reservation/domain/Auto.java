package it.appforsports.auto.reservation.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Auto.
 */
@Entity
@Table(name = "auto")
public class Auto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "marca", nullable = false)
    private String marca;

    @NotNull
    @Column(name = "modello", nullable = false)
    private String modello;

    @NotNull
    @Column(name = "cilindrata", nullable = false)
    private String cilindrata;

    @NotNull
    @Column(name = "alimentazione", nullable = false)
    private String alimentazione;

    @NotNull
    @Column(name = "colore", nullable = false)
    private String colore;

    @NotNull
    @Column(name = "anno_immatricolazione", nullable = false)
    private Integer annoImmatricolazione;

    @Column(name = "stato_prenotazione")
    private String statoPrenotazione;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public Auto marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public Auto modello(String modello) {
        this.modello = modello;
        return this;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getCilindrata() {
        return cilindrata;
    }

    public Auto cilindrata(String cilindrata) {
        this.cilindrata = cilindrata;
        return this;
    }

    public void setCilindrata(String cilindrata) {
        this.cilindrata = cilindrata;
    }

    public String getAlimentazione() {
        return alimentazione;
    }

    public Auto alimentazione(String alimentazione) {
        this.alimentazione = alimentazione;
        return this;
    }

    public void setAlimentazione(String alimentazione) {
        this.alimentazione = alimentazione;
    }

    public String getColore() {
        return colore;
    }

    public Auto colore(String colore) {
        this.colore = colore;
        return this;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public Integer getAnnoImmatricolazione() {
        return annoImmatricolazione;
    }

    public Auto annoImmatricolazione(Integer annoImmatricolazione) {
        this.annoImmatricolazione = annoImmatricolazione;
        return this;
    }

    public void setAnnoImmatricolazione(Integer annoImmatricolazione) {
        this.annoImmatricolazione = annoImmatricolazione;
    }

    public String getStatoPrenotazione() {
        return statoPrenotazione;
    }

    public Auto statoPrenotazione(String statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
        return this;
    }

    public void setStatoPrenotazione(String statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Auto auto = (Auto) o;
        if (auto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Auto{" +
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
