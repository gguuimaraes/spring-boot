package br.com.gguuimaraes.springboot.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@Data
@MappedSuperclass
public abstract class DefaultModel extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long code;
}
