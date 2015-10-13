package com.rga.customer.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * Entity representing the <code>USERS</code> table.
 * 
 * @author Adrian Sacuiu
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "getAllCustomers", query = "FROM Customer")
})
@Table(name = "CUSTOMERS")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idCustomer;
	private String email;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CUSTOMER")
	public Long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((idCustomer == null) ? 0 : idCustomer.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (idCustomer == null) {
			if (other.idCustomer != null)
				return false;
		} else if (!idCustomer.equals(other.idCustomer))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [idCustomer=" + idCustomer + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", phone=" + phone + "]";
	}

}
