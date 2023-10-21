package br.com.projeto.apiservice.modelo;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.com.projeto.apiservice.enums.Status;
import br.com.projeto.apiservice.enums.converters.StatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Entity
@Table(name = "pessoas")
@Getter
@Setter
@SQLDelete(sql = "UPDATE pessoas SET status = 'Inativo' WHERE codigo = ?")
@Where(clause = "status = 'Ativo'")
public class Pessoa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(length = 200, nullable = false)
    private String nome;

    @Column(length = 20, nullable = false)
    private String cpf;
    
    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ATIVO;
    
}
