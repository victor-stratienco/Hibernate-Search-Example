package demo.hibernatesearch.dao.hibernate.utils;

import java.io.IOException;
import java.util.BitSet;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Filter;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.annotations.Key;
import org.hibernate.search.filter.FilterKey;
import org.hibernate.search.filter.StandardFilterKey;

public class RangeFilter extends Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private org.apache.lucene.search.RangeFilter rangeFilter;

	private String fieldName, lowerTerm, upperTerm;
	private boolean includeLower, includeUpper;

	public RangeFilter() {

	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getLowerTerm() {
		return lowerTerm;
	}

	public void setLowerTerm(String lowerTerm) {
		this.lowerTerm = lowerTerm;
	}

	public String getUpperTerm() {
		return upperTerm;
	}

	public void setUpperTerm(String upperTerm) {
		this.upperTerm = upperTerm;
	}

	public boolean isIncludeLower() {
		return includeLower;
	}

	public void setIncludeLower(boolean includeLower) {
		this.includeLower = includeLower;
	}

	public boolean isIncludeUpper() {
		return includeUpper;
	}

	public void setIncludeUpper(boolean includeUpper) {
		this.includeUpper = includeUpper;
	}

	public BitSet bits(IndexReader reader) throws IOException {
		rangeFilter = new org.apache.lucene.search.RangeFilter(fieldName,
				lowerTerm, upperTerm, includeLower, includeUpper);
		return rangeFilter.bits(reader);
	}

	@Key
	public FilterKey getKey() {
		StandardFilterKey key = new StandardFilterKey();
		key.addParameter(lowerTerm);
		key.addParameter(upperTerm);
		key.addParameter(includeLower);
		key.addParameter(includeUpper);
		return key;
	}

	@Factory
	public Filter getFilter() {
		return new CachingWrapperFilter(this);
	}

}
