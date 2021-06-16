package it.holyfamily.holybadge.pojos;

import java.time.LocalDate;

import it.holyfamily.holybadge.entities.Parishioner;

public class ParishionersOfGroup {

    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String note;
	private LocalDate dataNascita;
    private boolean member;

    public ParishionersOfGroup(Parishioner parishioner, boolean membership){
        this.id = parishioner.getId();
        this.name = parishioner.getName();
        this.surname = parishioner.getSurname();
        this.phoneNumber = parishioner.getPhoneNumber();
        this.note = parishioner.getNote();
        this.dataNascita = parishioner.getDataNascita();
        this.member = membership;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String cognome) {
        this.surname = cognome;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}


    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

}
