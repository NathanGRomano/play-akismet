package com.manta.akismet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import play.libs.F.Callback;
import play.libs.F.Promise;
import play.libs.WS.Response;

public class Classifier extends Call {
	
	public final static Map<Classification, String> path;
	static {
		path = new HashMap<Classification, String>();
		path.put(Classification.SPAM, "submit-spam");
		path.put(Classification.HAM, "submit-ham");
	}	

	/**
	 * Classifies the content with the specified classification
	 * 
	 * @see http://akismet.com/development/api/#submit-spam
	 * @see http://akismet.com/development/api/#submit-ham
	 * @param content
	 * @param classification
	 * @param cb
	 * @return Promise<Response>
	 * @throws UnsupportedEncodingException
	 * @throws IOExpcetion
	 */

	public Promise<Response> classify (final Content content, final Classification classification, final Callback<String> cb) throws UnsupportedEncodingException, IOException {
		return invoke(content, classification, cb);
	}

	@Override
	protected String getPath(Classification classification) {
		return Classifier.path.get(classification);
	}

	@Override
	protected void handleResponse(Content content, Classification classification, Response response) {
		content.setClassification(classification);
	}
	
}
