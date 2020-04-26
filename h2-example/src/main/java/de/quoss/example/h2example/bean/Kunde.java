package de.quoss.example.h2example.bean;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "UKS")
@Entity
@DynamicInsert
public class Kunde implements Serializable
{

	/**
	 * Versionsnummer f�r die Serialisierung
	 */
	private static final long serialVersionUID = 1L;

	public Kunde(final Integer kundenNr) {
		this.kundenNr = kundenNr;
	}

	/**
	 * Das Attribut: kundenNr
	 */
	@Column(name = "SNR")
	@Id
	public Integer kundenNr;

	/**
	 * Das Attribut: name
	 */
	@Column(name = "NAME")
	public String name;

	/**
	 * Das Attribut: geburtsDatum
	 */
	@Column(name = "GEBDAT")
	private Integer geburtsDatum;

	@PrePersist
	public void prePersist() {
		if (name == null) {
			name = "";
		}
	}

}
