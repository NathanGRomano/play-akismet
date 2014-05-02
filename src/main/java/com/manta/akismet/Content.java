package com.manta.akismet;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Content {

	/**
	 * The required fields
	 */

	public final static String[] required = {
		"blog",
		"user_ip",
		"user_agent"
	};
	
	/**
	 * The available fields
	 */

	public final static String[] fields = {
		"is_test",
		"blog",
		"user_ip",
		"user_agent",
		"referrer", 
		"permalink",
		"comment_type",
		"comment_author",
		"comment_author_email",
		"comment_author_uri",
		"comment_content",
		"comment_date_gmt",
		"comment_post_modified_gmt",
		"blog_lang",
		"blog_charset"
	};
	
	/**
	 * The default values
	 */

	public final static Map<String, String> defaultValues;
	
	static {
		defaultValues = new HashMap<String, String>();
		defaultValues.put("blog", "http://www.manta.com");
		defaultValues.put("user_ip", null);
		defaultValues.put("user_agent", null);
		defaultValues.put("referrer", "");
		defaultValues.put("permalink", "");
		defaultValues.put("comment_type", "");
		defaultValues.put("comment_author", "");
		defaultValues.put("comment_author_email", "");
		defaultValues.put("comment_author_uri", "");
		defaultValues.put("comment_content", "");
		defaultValues.put("comment_date_gmt", null);
		defaultValues.put("comment_date_modified_gmt", null);
		defaultValues.put("blog_lang", "en");
		defaultValues.put("blog_charset", "utf8");
	}
	
	/**
	 * the default test values
	 */

	public final static Map<String, String> testingValues;
	
	static {
		testingValues = new HashMap<String, String>();
		testingValues.put("blog", "http://www.manta.com");
		testingValues.put("user_ip", "127.0.0.1");
		testingValues.put("user_agent", "Manta/1.0");
		testingValues.put("referrer", "http://www.manta.com");
		testingValues.put("permalink", "http://www.manta.com/blog/article/1");
		testingValues.put("comment_type", "comment");
		testingValues.put("comment_author", "admin");
		testingValues.put("comment_author_email", "admin@manta.com");
		testingValues.put("comment_author_uri", "http://www.manta.com/m/admin");
		testingValues.put("comment_content", "Buy a PBL");
		testingValues.put("comment_date_gmt", null);
		testingValues.put("comment_date_modified_gmt", null);
		testingValues.put("blog_lang", "en");
		testingValues.put("blog_charset", "utf8");
	}
	
	/**
	 * Our private data which contains the fields and their values
	 */

	private Map<String, String> data = null;

	/**
	 * If we are a "test" content
	 */

	private Boolean testing = Boolean.FALSE;
	
	/**
	 * Our classification
	 */
	
	private Classification classification = Classification.UNKNOWN;
	
	
	/**
	 * Creates a test instance given the classification
	 * 
	 * @param classification
	 * @return
	 */
	
	public static Content makeTestContent (Classification classification) {
		Content c = new Content(true);
		if (classification == Classification.SPAM) {
			c.setCommentAuthor("viagra-test-123");
		} else if (classification == Classification.HAM) {
			c.getData().put("user_role", "administrator");
		}
		c.classification = classification;
		return c;
	}
	
	/**
	 * The default constructor
	 */
	
	public Content () { }
	
	/**
	 *  Flags if this content will be used for testing or not
	 */

	public Content (Boolean testing) {
		this.testing = testing;
		if (testing) {
			getData().put("is_test", "1");
		}
	}
	
	/**
	 * Sets the data for this instance
	 * 
	 * @param in
	 */
	
	public Content (Map<String, String> in) {
		this.data = in;
	}
	
	/**
	 * Initializes the data for this instance
	 * 
	 * @return
	 */
	
	public Map<String, String> getData () {
		if (data == null) {
			data = new HashMap<String, String>();
		}
		return data;
	}
	
	/**
	 * Tells us if we are test data
	 * 
	 * @return 
	 */
	
	public Boolean isTest () {
		return testing;
	}
	
	/**
	 * Gets the classification
	 * 
	 * @return
	 */
	
	public Classification getClassification () {
		return classification;
	}
	
	/**
	 * Sets the classification
	 * 
	 * @param in
	 */
	
	protected Content setClassification (Classification classification) {
		this.classification = classification;
		return this;
	}
	
	/**
	 * Gets an initialized value for the given "name".  If we the value is not set the defaultValue will be used.
	 * If this instance is a "test" instance the value will be set to testValue.
	 * 
	 * @param name
	 * @return
	 */
	
	public String getValue (String name) {
		String value = getData().get(name);
		if (value == null) {
			if (testing) {
				value = testingValues.get(name);
			}
			else {
				value = defaultValues.get(name);
			}
			if (value != null) {
				getData().put(name, value);
			}
		}
		return value;
	}
	
	/**
	 * Gets the "blog" field value.  The default is "http://www.manta.com"
	 * 
	 * @return
	 */

	public String getBlog () {
		return getValue("blog");
	}
	
	/**
	 * Sets the "blog" field
	 * 
	 * @param in
	 */

	public Content setBlog (String in) {
		getData().put("blog", in);
		return this;
	}

	/**
	 * Gets the "user_ip" field value.  The default is null and the default test value is "127.0.0.1"
	 * 
	 * @return
	 */

	public String getUserIp () {
		return getValue("user_ip");
	}

	/**
	 * Sets the "user_ip" field
	 * 
	 * @param in
	 */

	public Content setUserIp(String in) {
		getData().put("user_ip", in);
		return this;
	}
	
	/**
	 * Gets the "user_agent" field value.  The default value is null and the default test value is "Manta/1.0"
	 * 
	 * @return
	 */

	public String getUserAgent () {
		return getValue("user_agent");
	}
	
	/**
	 * Sets the "user_agent" field 
	 * 
	 * @param in
	 */

	public Content setUserAgent (String in) {
		getData().put("user_agent", in);
		return this;
	}

	/**
	 * Gets the "referrer" field value.  The default value is blank and the default test value is "http://www.manta.com"
	 * 
	 * @return
	 */

	public String getReferrer () {
		return getValue("referrer");
	}
	
	/**
	 * Sets the "referrer" field
	 * 
	 * @param in
	 */

	public Content setReferrer (String in) {
		getData().put("referrer", in);
		return this;
	}
	
	/**
	 * Gets the "permalink field value.  The default value is blank and the default test value is "http://www.manta.com/blog/article/1"
	 * 
	 * @return
	 */

	public String getPermalink () {
		return getValue("permalink");
	} 
	
	/**
	 * Sets the "permalink" field.
	 * 
	 * @param in
	 */

	public Content setPermalink (String in) {
		getData().put("permalink", in);
		return this;
	}
	
	/**
	 * Gets the "comment_type" field value.  The default value is blank and the default test value is "comment"
	 * 
	 * @return
	 */

	public String getCommentType () {
		return getValue("comment_type");
	}
	
	/**
	 * Sets the "comment_type" field
	 * 
	 * @param in
	 */
	
	public Content setCommentType (String in) {
		getData().put("comment_type", in);
		return this;
	}
	
	/**
	 * Gets the "comment_author" field value.  The default value is blank and the default test value is "admin"
	 * 
	 * @return
	 */
	
	public String getCommentAuthor () {
		return getValue("comment_author");
	}
	
	/**
	 * Sets the "comment_author" field
	 * 
	 * @param in
	 */
	
	public Content setCommentAuthor (String in) {
		getData().put("comment_author", in);
		return this;
	}
	
	/**
	 * Gets the "comment_author_email" field value.  The default value is blank and the default test value is "admin@manta.com"
	 * 
	 * @return
	 */
	
	public String getCommentAuthorEmail () {
		return getValue("comment_author_email");
	}
	
	/**
	 * Sets the "comment_author_email" field 
	 * 
	 * @param in
	 */
	
	public Content setCommentAuthorEmail (String in) {
		getData().put("comment_author_email", in);
		return this;
	}
	
	/**
	 * Gets the "comment_author_uri" field value.  The default is blank and the default test value is "http://www.manta.com/m/admin"
	 * 
	 * @return
	 */
	
	public String getCommentAuthorUri () {
		return getValue("comment_author_uri");
	}

	/**
	 * Sets the "comment_author_uri" field
	 * 
	 * @param in
	 */

	public Content setCommentAuthorUri (String in) {
		getData().put("comment_author_uri", in);
		return this;
	}
	
	/**
	 * Gets the "comment_content" field.  The default is blank and the default test value is "Buy a PBL"
	 * 
	 * @return
	 */
	
	public String getCommentContent () {
		return getValue("comment_content");
	}
	
	/**
	 * Sets the "comment_content"
	 * 
	 * @param in
	 */
	
	public Content setCommentContent (String in) {
		getData().put("comment_content", in);
		return this;
	}
	
	/**
	 * Gets he "comment_date_gmt" field value.  The default is blank and the default test value is blank.
	 * 
	 * @return
	 */
	
	public String getÇommentDateGmt () {
		return getValue("comment_date_gmt");
	}
	
	/**
	 * Sets the "comment_date_gmt" field
	 * 
	 * @param in
	 */
	
	public Content setCommentDateGmt (String in) {
		getData().put("comment_date_gmt", in);
		return this;
	}
	
	/**
	 * Gets the "comment_post_modified_gmt" field value.  The default is blank and the default test value is blank.
	 * 
	 * @return
	 */
	
	public String getCommentPostModifiedGmt () {
		return getValue("comment_post_modified_gmt");
	}
	
	/**
	 * Sets the "comment_post_modified_gmt" field  
	 * 
	 * @param in
	 */

	public Content setCommentPostModidifedGmt (String in) {
		getData().put("comment_post_modified_gmt", in);
		return this;
	}
	
	/**
	 * Gets the "blog_lang" field value.  The default is blank and the default test value is "en"
	 *
	 * @return
	 */

	public String getBlogLang () {
		return getValue("blog_lang");
	}
	
	/**
	 * Sets the "blog_lang" field  
	 * 
	 * @param in
	 */

	public Content setBlogLang (String in) {
		getData().put("blog_lang", in);
		return this;
	}
	
	/**
	 * Gets the "blog_charset" field value.  The default is blank and the default test value is "utf8"
	 * 
	 * @return
	 */

	public String getBlogCharset () {
		return getValue("blog_charset");
	}

	/**
	 * Sets the "blog_charset" field 
	 * 
	 * @param in
	 */

	public Content setBlogCharset (String in) {
		getData().put("blog_charset", in);
		return this;
	}
	
	/**
	 * Converts to form data
	 *
	 * @return
	 */
	
	public String toFormData () {
		// TODO we should validate the content and throw an exception
        StringBuilder sb = new StringBuilder();
        int i;
        String value;
        for (i=0; i<Content.fields.length-1; i++) {
                value = getValue(Content.fields[i]);
                if (value != null) {
                        try {
							sb.append(Content.fields[i] + "=" + java.net.URLEncoder.encode(value, "UTF-8") + "&");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                }
        }
        value = getValue(Content.fields[i]);
        if (value != null) {
                try {
					sb.append(Content.fields[i] + "=" + java.net.URLEncoder.encode(value, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return sb.toString();
	}

}
