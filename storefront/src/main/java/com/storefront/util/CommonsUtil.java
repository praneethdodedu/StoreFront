package com.storefront.util;

import static com.storefront.util.ApplicationConstants.DATE_FORMATE_YYYY_MM_DDHH_MM_SS_SSS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class CommonsUtil {

	private static Logger logger = LoggerFactory.getLogger(CommonsUtil.class);

	/**
	 * Parses the string which we get from ACP into date
	 * 
	 * @param sDate
	 * @return Date
	 */
	public Date parseJsonStringIntoDate(String sDate) {
		try {
			return new SimpleDateFormat(DATE_FORMATE_YYYY_MM_DDHH_MM_SS_SSS)
					.parse(sDate.replace("T", "").replace("Z", ""));
		} catch (ParseException exception) {
			logger.error("Exception while parsing the date", exception);
		}
		return null;
	}

}
