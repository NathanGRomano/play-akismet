package com.manta.akismet;

import java.io.IOException;

import play.libs.F.Callback;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.Response;
import play.libs.WS.WSRequestHolder;
import play.mvc.Http.HeaderNames;

public abstract class Call {
	
	/**
	 * Invoke this call using the content classification and a callback
	 * 
	 * @param content
	 * @param classification
	 * @param callback
	 * @return
   * @throws IOException
	 */

	public Promise<Response> invoke (final Content content, final Classification classification, final Callback<String> callback) throws IOException {
		final Call self = this;
		WSRequestHolder request = WS.url(getRequestURI(classification));
		request.setHeader(HeaderNames.USER_AGENT, Constants.userAgent);
		request.setContentType("application/x-www-form-urlencoded");
		Promise<Response> response = request.post(content.toFormData());
		response.onRedeem(new Callback<Response> () {		
			@Override
			public void invoke (Response response) throws Throwable {
				self.handleResponse(content, classification, response);
				callback.invoke(response.getBody());
			}
		});
		return response;
	}
	
	/**
	 * An alternative way of invoking the Call with just the content and callback
	 * 
	 * @param content
	 * @param callback
	 * @return
   * @throws IOException
	 */
	
	public Promise<Response> invoke (final Content content, Callback<String> callback) throws IOException {
		return invoke(content, content.getClassification(), callback);
	}
	
	/**
	 * Builds the request URI given the classification
	 * 
	 * @param classification
	 * @return
   * @throws IOException
	 */
	
	protected String getRequestURI (Classification classification) throws IOException {
		return Constants.scheme + "://" + Key.get() + "." + Constants.base + "/" + Constants.version + "/" + getPath(classification);
	}
	
	/**
	 * Gets the path from the classification
	 * 
	 * @param classification
	 * @return
	 */
	
	abstract protected String getPath (Classification classification);
	
	/**
	 * Handles the response before invoking the callback
	 * 
	 * @param content
	 * @param classification
	 * @param response
	 */
	
	abstract protected void handleResponse (Content content, Classification classification, Response response);
		
}
