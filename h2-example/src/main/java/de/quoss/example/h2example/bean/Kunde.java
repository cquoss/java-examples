package de.quoss.example.h2example.bean;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "UKS")
@Entity
@DynamicInsert
public class Kunde implements Serializable
{

	/**
	 * Versionsnummer für die Serialisierung
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public Kunde(final Integer kundenNr, final String name, final String geburtsname) {
		this.kundenNr = kundenNr;
		this.name = name;
		this.geburtsname = geburtsname;
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
	 * Das Attribut: geburtsname
	 */
	@Column(name = "GEBNAME")
	private String geburtsname;

	protected Kunde() {}

}
