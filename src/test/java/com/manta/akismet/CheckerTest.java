package com.manta.akismet;

import static org.junit.Assert.*;

import org.junit.Test;

import com.manta.akismet.Checker;
import com.manta.akismet.Classification;
import com.manta.akismet.Content;

import play.libs.F.Callback;

import java.io.IOException;

public class CheckerTest {
	
	private final Object lock = new Object();
	private String result;

	@Test
	public void testCheck () throws InterruptedException, IOException {

		final CheckerTest self = this;
		Checker checker = new Checker();
		Content content = Content.makeTestContent(Classification.SPAM);
		content.setClassification(Classification.UNKNOWN);
		assertEquals(content.getClassification(), Classification.UNKNOWN);
		checker.check(content, new Callback<String> () {		
			@Override
			public void invoke (String in) {
				self.result = in;
			}
		});
		synchronized (lock) {
			lock.wait(1000l);
		}
		assertTrue(result.equals("true"));
		assertEquals(content.getClassification(), Classification.SPAM);
		
		
		content = Content.makeTestContent(Classification.HAM);
		content.setClassification(Classification.UNKNOWN);
		assertEquals(content.getClassification(), Classification.UNKNOWN);
		checker.check(content, new Callback<String> () {		
			@Override
			public void invoke (String in) {
				self.result = in;
			}
		});
		synchronized (lock) {
			lock.wait(1000l);
		}
		assertTrue(result.equals("false"));
		assertEquals(content.getClassification(), Classification.HAM);
		
	}

}
