package demo.hibernatesearch.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.NaturalId;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "user")
@Indexed
@Analyzer(impl = org.apache.lucene.analysis.standard.StandardAnalyzer.class)
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@DocumentId
	private Long id;

	@org.hibernate.annotations.Index(name = "firstNameIndex")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String firstName;

	@org.hibernate.annotations.Index(name = "lastNameIndex")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String lastName;

	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String middleName;

	@org.hibernate.annotations.Index(name = "emailAddressIndex")
	@NaturalId
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String emailAddress;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "applicant")
	@ContainedIn
	private Set<Resume> resumes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Set<Resume> getResumes() {
		return resumes;
	}

	public void setResumes(Set<Resume> resumes) {
		this.resumes = resumes;
	}

	public String getFullName() {
		return firstName + " " + middleName + " " + lastName;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
