package demo.hibernatesearch.dao.hibernate.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.poi.hwpf.usermodel.Range;
import org.hibernate.search.bridge.StringBridge;

/**
 * Bridge in Hibernate Search is a data-type converter between a Java property
 * type and its full text, indexed representation.
 * 
 */
public class WordDocHandlerBridge implements StringBridge {

	public String objectToString(Object arg0) {

		StringBuilder _result = new StringBuilder();
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream((byte[]) arg0);
			org.apache.poi.hwpf.HWPFDocument doc = new org.apache.poi.hwpf.HWPFDocument(
					bais);
			Range range = doc.getRange();
			int np = range.numParagraphs();
			for (int i = 0; i < np; i++) {
				_result.append(range.getParagraph(i).text());
				_result.append(" ");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return _result.toString();
	}

}
