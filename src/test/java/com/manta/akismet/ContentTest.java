package com.manta.akismet;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.manta.akismet.Classification;
import com.manta.akismet.Content;

public class ContentTest {
	
	@Test
	public void testIsTest () {
		assertTrue((new Content(Boolean.TRUE)).isTest());
		assertFalse((new Content(Boolean.FALSE)).isTest());
	}
	
	@Test
	public void testClassification () {
		assertTrue((new Content()).getClassification().equals(Classification.UNKNOWN));
	}

	@Test
	public void testMakeTestContentSpam () {
		Content c = Content.makeTestContent(Classification.SPAM);
		assertTrue(c.isTest());
		assertTrue(c.getClassification().equals(Classification.SPAM));
		assertTrue(c.getData().get("comment_author").equals("viagra-test-123"));
	}
	
	@Test
	public void testMakeTestContentHam () {
		Content c = Content.makeTestContent(Classification.HAM);
		assertTrue(c.isTest());
		assertTrue(c.getClassification().equals(Classification.HAM));
		assertTrue(c.getData().get("user_role").equals("administrator"));
	}
	
	@Test
	public void testData () {
		Map<String, String> data = new HashMap<String, String>();
		Content c = new Content(data);
		assertTrue(c.getData().equals(data));
	}
	
	@Test
	public void testBlog () {
		assertTrue(new Content(Boolean.TRUE).getBlog().equals("http://www.manta.com"));
		assertTrue(new Content(Boolean.FALSE).getBlog().equals("http://www.manta.com"));
		assertTrue(new Content().setBlog("test").getBlog().equals("test"));
	}
	
	@Test
	public void testUserIp () {
		assertTrue(new Content(Boolean.TRUE).getUserIp().equals("127.0.0.1"));
		assertEquals(new Content(Boolean.FALSE).getUserIp(), null);
		assertTrue(new Content().setUserIp("test").getUserIp().equals("test"));
	}
	
	@Test
	public void testUserAgent () {
		assertTrue(new Content(Boolean.TRUE).getUserAgent().equals("Manta/1.0"));
		assertEquals(new Content(Boolean.FALSE).getUserAgent(), null);
		assertTrue(new Content().setUserAgent("test").getUserAgent().equals("test"));
	}
	
	@Test
	public void testReferrer () {
		assertTrue(new Content(Boolean.TRUE).getReferrer().equals("http://www.manta.com"));
		assertTrue(new Content(Boolean.FALSE).getReferrer().equals(""));
		assertTrue(new Content().setReferrer("test").getReferrer().equals("test"));
	}

	@Test
	public void testPermalink () {
		assertTrue(new Content(Boolean.TRUE).getPermalink().equals("http://www.manta.com/blog/article/1"));
		assertTrue(new Content(Boolean.FALSE).getPermalink().equals(""));
		assertTrue(new Content().setPermalink("test").getPermalink().equals("test"));
	}

	@Test
	public void testCommentType () {
		assertTrue(new Content(Boolean.TRUE).getCommentType().equals("comment"));
		assertTrue(new Content(Boolean.FALSE).getCommentType().equals(""));
		assertTrue(new Content().setCommentType("test").getCommentType().equals("test"));
	}

	@Test
	public void testCommentAuthor () {
		assertTrue(new Content(Boolean.TRUE).getCommentAuthor().equals("admin"));
		assertTrue(new Content(Boolean.FALSE).getCommentAuthor().equals(""));
		assertTrue(new Content().setCommentAuthor("test").getCommentAuthor().equals("test"));
	}

	@Test
	public void testCommentAuthorEmail () {
		assertTrue(new Content(Boolean.TRUE).getCommentAuthorEmail().equals("admin@manta.com"));
		assertTrue(new Content(Boolean.FALSE).getCommentAuthorEmail().equals(""));
		assertTrue(new Content().setCommentAuthorEmail("test").getCommentAuthorEmail().equals("test"));
	}

	@Test
	public void testCommentAuthorUri () {
		assertTrue(new Content(Boolean.TRUE).getCommentAuthorUri().equals("http://www.manta.com/m/admin"));
		assertTrue(new Content(Boolean.FALSE).getCommentAuthorUri().equals(""));
		assertTrue(new Content().setCommentAuthorUri("test").getCommentAuthorUri().equals("test"));
	}

	@Test
	public void testCommentContent () {
		assertTrue(new Content(Boolean.TRUE).getCommentContent().equals("Buy a PBL"));
		assertTrue(new Content(Boolean.FALSE).getCommentContent().equals(""));
		assertTrue(new Content().setCommentContent("test").getCommentContent().equals("test"));
	}

	@Test
	public void testCommentDateGmt () {
		assertEquals(new Content(Boolean.TRUE).getÇommentDateGmt(), null);
		assertEquals(new Content(Boolean.FALSE).getÇommentDateGmt(), null);
		assertTrue(new Content().setCommentDateGmt("test").getÇommentDateGmt().equals("test"));
	}

	@Test
	public void testCommentPostModifiedGmt () {
		assertEquals(new Content(Boolean.TRUE).getCommentPostModifiedGmt(), null);
		assertEquals(new Content(Boolean.FALSE).getCommentPostModifiedGmt(), null);
		assertTrue(new Content().setCommentPostModidifedGmt("test").getCommentPostModifiedGmt().equals("test"));
	}

	@Test
	public void testBlogLang () {
		assertTrue(new Content(Boolean.TRUE).getBlogLang().equals("en"));
		assertTrue(new Content(Boolean.FALSE).getBlogLang().equals("en"));
		assertTrue(new Content().setBlogLang("test").getBlogLang().equals("test"));
	}

	@Test
	public void testBlogCharset () {
		assertTrue(new Content(Boolean.TRUE).getBlogCharset().equals("utf8"));
		assertTrue(new Content(Boolean.FALSE).getBlogCharset().equals("utf8"));
		assertTrue(new Content().setBlogCharset("test").getBlogCharset().equals("test"));
	}
	
	@Test
	public void testToFormData () {
		assertEquals(Content.makeTestContent(Classification.SPAM).toFormData(), "is_test=1&blog=http%3A%2F%2Fwww.manta.com&user_ip=127.0.0.1&user_agent=Manta%2F1.0&referrer=http%3A%2F%2Fwww.manta.com&permalink=http%3A%2F%2Fwww.manta.com%2Fblog%2Farticle%2F1&comment_type=comment&comment_author=viagra-test-123&comment_author_email=admin%40manta.com&comment_author_uri=http%3A%2F%2Fwww.manta.com%2Fm%2Fadmin&comment_content=Buy+a+PBL&blog_lang=en&blog_charset=utf8");
		assertEquals(Content.makeTestContent(Classification.HAM).toFormData(), "is_test=1&blog=http%3A%2F%2Fwww.manta.com&user_ip=127.0.0.1&user_agent=Manta%2F1.0&referrer=http%3A%2F%2Fwww.manta.com&permalink=http%3A%2F%2Fwww.manta.com%2Fblog%2Farticle%2F1&comment_type=comment&comment_author=admin&comment_author_email=admin%40manta.com&comment_author_uri=http%3A%2F%2Fwww.manta.com%2Fm%2Fadmin&comment_content=Buy+a+PBL&blog_lang=en&blog_charset=utf8");
	}
	
}
