package com.manta.akismet;

import java.io.IOException;

import play.libs.F.Callback;
import play.libs.F.Promise;
import play.libs.WS.Response;

public class Checker extends Call {

	public final static String path = "comment-check";
	
	/**
	 * Checks if the content is either SPAM or HAM and updates the classification accordingly
	 * 
	 * @see http://akismet.com/development/api/#comment-check
	 * @param content
	 * @param cb
	 * @return Promise<Response>
	 */
	
	public Promise<Response> check (final Content content, final Callback<String> cb) throws IOException {
		return invoke(content, cb);
	}

	@Override
	protected String getPath(Classification classification) {
		return Checker.path;
	}

	@Override
	protected void handleResponse(Content content, Classification classification, Response response) {
        String body = response.getBody();
        if (body.equalsIgnoreCase("true")) {
        	content.setClassification(Classification.SPAM);
        }
        else if (body.equalsIgnoreCase("false")) {
            content.setClassification(Classification.HAM);
        }
		
	}

}
