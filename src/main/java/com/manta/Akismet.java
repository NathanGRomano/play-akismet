package com.manta;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import play.libs.F.Callback;
import play.libs.F.Promise;
import play.libs.WS.Response;

import com.manta.akismet.Checker;
import com.manta.akismet.Classification;
import com.manta.akismet.Classifier;
import com.manta.akismet.Content;
import com.manta.akismet.Key;


public class Akismet {
	
	static private Classifier classifier = new Classifier();
	static private Checker checker = new Checker();

  /**
   * Setst the api key
   *
   * @param key
   */

  static public void setKey (String key) {
    Key.set(key);  
  }

	
	/**
	 * Checks if the content is spam or ham.  True / false
	 * 
	 * @param content
	 * @param cb
	 * @return Promise<Response>
	 */
	
	static public Promise<Response> check (final Content content, final Callback<Classification> cb) throws IOException {
		return checker.check(content, new Callback<String> () {				
			public void invoke (String in) throws Throwable {
				cb.invoke(content.getClassification());
			}
		});
	}
	
	/**
	 * Submits content to be classified 
	 * 
	 * @param content
	 * @param classifcation
	 * @return Promise<Response>
	 * @throws UnsupportedEncodingException 
	 */

	static public Promise<Response> classify (final Content content, final Classification classification, final Callback<Classification> cb) throws UnsupportedEncodingException, IOException {
		return classifier.classify(content, classification, new Callback<String> () {				
			public void invoke (String in) throws Throwable {
				cb.invoke(content.getClassification());
			}
		});
	}
	
}
