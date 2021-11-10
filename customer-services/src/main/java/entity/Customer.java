package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@Table(name = "tbl_customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "El numero de identificacion no debe ser vacío.")
    @Size( min = 8 , max = 8, message = "El tamaño del número de documento es 8")
    @Column(name = "number_id" , unique = true ,length = 8, nullable = false)
    private String numberID;

    @NotEmpty(message = "El nombre no debe ser vacío.")
    @Column(name="first_name", nullable=false)
    private String firstName;

    @NotEmpty(message = "El apellido no debe ser vacío.")
    @Column(name="last_name", nullable=false)
    private String lastName;

    @NotEmpty(message = "El email no debe ser vacío.")
    @Email(message = "No es una direccion Email valida.")
    @Column(unique=true, nullable=false)
    private String email;

    @Column(name="photo_url")
    private String photoUrl;

    @NotNull(message = "La región no puede ser vacio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Region region;

    private String status;
    
}

