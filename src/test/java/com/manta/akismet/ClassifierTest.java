package com.manta.akismet;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.io.IOException;

import org.junit.Test;

import com.manta.akismet.Classification;
import com.manta.akismet.Classifier;
import com.manta.akismet.Content;

import play.libs.F.Callback;

public class ClassifierTest {

	private final Object lock = new Object();
	private String result;

	@Test
	public void testClassify () throws InterruptedException, UnsupportedEncodingException, IOException {
		final ClassifierTest self = this;
		Classifier classifier = new Classifier();
		Content content = Content.makeTestContent(Classification.SPAM);
		content.setClassification(Classification.UNKNOWN);
		assertEquals(content.getClassification(),Classification.UNKNOWN);
		classifier.classify(content, Classification.SPAM, new Callback<String> () {	
			@Override
			public void invoke(String in) {
				self.result = in;
			}
		});
		synchronized (lock) {
			lock.wait(1000l);
		}
		assertTrue(result.equals("Thanks for making the web a better place."));
		assertEquals(content.getClassification(),Classification.SPAM);
		
	}

}
