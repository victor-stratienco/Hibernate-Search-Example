package demo.hibernatesearch.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.FullTextFilterDefs;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "resume")
@FilterDefs( { @FilterDef(name = "rangeFilter", parameters = {
		@ParamDef(name = "beginDate", type = "date"),
		@ParamDef(name = "endDate", type = "date") }) })
@Filters( { @Filter(name = "rangeFilter", condition = ":beginDate <= lastUpdated and :endDate >= lastUpdated") })
@Indexed
@Analyzer(impl = org.apache.lucene.analysis.standard.StandardAnalyzer.class)
@FullTextFilterDefs( { @FullTextFilterDef(name = "rangeFilter", impl = demo.hibernatesearch.dao.hibernate.utils.RangeFilter.class, cache = true) })
public class Resume implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@DocumentId
	private Long id;

	@OneToOne
	@IndexedEmbedded
	private User applicant;

	@org.hibernate.annotations.Index(name = "summaryIndex")
	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String summary;

	@Lob
	@Field(name = "resume", index = Index.TOKENIZED, store = Store.NO)
	@FieldBridge(impl = demo.hibernatesearch.dao.hibernate.utils.WordDocHandlerBridge.class)
	private byte[] content; // MS Word Doc

	@Temporal(value = TemporalType.DATE)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@DateBridge(resolution = Resolution.DAY)
	@Boost(2.0f)
	private Date lastUpdated; // can't project

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
