package com.manta;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.io.IOException;

import org.junit.Test;

import com.manta.Akismet;
import com.manta.akismet.Classification;
import com.manta.akismet.Content;
import com.manta.akismet.Key;

import play.libs.F.Callback;

public class AkismetTest {
	
	private final Object lock = new Object();
	private Classification result;
	
	private Content getSpam () {
		Content content = new Content(true);
		content.setBlog("www.manta.com");
		content.setUserIp("127.0.0.1");
		content.setCommentAuthor("viagra-test-123");
		content.setCommentContent("this is spam");
		return content;
	}
	
	private Content getHam () {
		Content content = new Content(true);
		content.setBlog("www.manta.com");
		content.setUserIp("127.0.0.1");
		content.setCommentAuthor("admin");
		content.getData().put("user_role", "administrator");
		return content.setCommentContent("this is ham");
	}

  @Test 
  public void testSetKey () throws IOException {
    String key = Key.get();
    Akismet.setKey(key);
    assertTrue(Key.get().equals(key));
  }

	@Test
	public void testIsSpam () throws InterruptedException, IOException {
		final AkismetTest self = this;
		Content content = getSpam();
		Akismet.check(content, new Callback<Classification> () {	
			@Override
			public void invoke(Classification b) {
				self.result = b;
			}
		});
		synchronized (lock) {
			lock.wait(1000l);
		}
		assertEquals(result, Classification.SPAM);
		assertEquals(content.getClassification(), Classification.SPAM);
	}
	
	@Test
	public void testIsHam () throws InterruptedException, IOException {
		final AkismetTest self = this;
		Content content = getHam();
		Akismet.check(content, new Callback<Classification> () {	
			@Override
			public void invoke(Classification b) {
				self.result = b;
			}
		});
		synchronized (lock) {
			lock.wait(1000l);
		}
		assertEquals(result, Classification.HAM);
		assertEquals(content.getClassification(), Classification.HAM);
	}
	
	@Test
	public void testSubmitSpam () throws InterruptedException, UnsupportedEncodingException, IOException {
		final AkismetTest self = this;
		Content content = getSpam(); 
		Akismet.classify(content, Classification.SPAM, new Callback<Classification> () {	
			@Override
			public void invoke(Classification b) {
				self.result = b;
			}
		});
		synchronized (lock) {
			lock.wait(1000l);
		}
		assertEquals(result, Classification.SPAM);
		assertEquals(content.getClassification(), Classification.SPAM);
	}
	
	@Test
	public void testSubmitHam () throws InterruptedException, UnsupportedEncodingException, IOException {
		final AkismetTest self = this;
		Content content = getHam();
		Akismet.classify(content, Classification.HAM, new Callback<Classification> () {	
			@Override
			public void invoke(Classification b) {
				self.result = b;
			}
		});
		synchronized (lock) {
			lock.wait(1000l);
		}
		assertEquals(result, Classification.HAM);
		assertEquals(content.getClassification(), Classification.HAM);
	}
}
