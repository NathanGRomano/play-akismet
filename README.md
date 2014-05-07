[![Build Status](https://travis-ci.org/mantacode/play-akismet.svg?branch=master)](https://travis-ci.org/mantacode/play-akismet)

# Play Akismet # 

The purpose of this repo is to provide Java Play developers the ability to interface with the Akismet API:

# Installation

This is a Java Play project and we use Maven.

Download and install the Java Play framework.  (See download and instructions here: http://www.playframework.com).

Install Maven.  (See download and instructions here: http://maven.apache.org/download.cgi)

Acquire an Akismet API key at: http://akismet.com

Clone this repository.  

    > git clone git@github.com:mantacode/play-akismet.git

In order to build and install the maven project you must do the follwoing

    > AKISMET_API_KEY=[YOUR KEY] mvn install

You can update the property AKISMET_API_KEY in the file src/main/resources/config.properties instead of you passing the key as an environment variable.

# Running Tests

    > AKISMET_API_KEY=[YOUR KEY] mvn test

# API

## Content

A Content object represents the data you are either checking for spam or classifiy as spam or ham.

Each instance must have three required fields poplulated. `blog`, `user_ip` and `user_agent`. 

```java

Content content = new Content();
content.setBlog("http://www.manta.com/connect");
content.setUserIp("127.0.0.1");
content.setUserAgent("chrome");

```

Each instance by default will have the default classifcation of Classification.UNKOWN.  This value will be changed
after you check the content for spam or classify it as either SPAM or HAM.

```java

System.out.println(c.getClassification()) -> "UNKNOWN"

```

A Content instance gives you access to these fields.  The more fields you have populated the better the chance Akismet
will be able to check and classify your spam.
u
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

```java

content.setReferrer("http://www.google.com");
content.setPermalink("http://www.manta.com/connect/article/1");
content.setCommentType("comment");
content.setCommentAuthor("Nathan Romano");
content.setCommentAuthorEmail("nathan.g.romano@gmail.com");
content.setCommentAuthorUri("http://github.com/NathanGRomano");
content.setCommentContent("Nice article!");
content.setCommentDateGmt(getSomeGmtDate());
content.setCommentPostModifiedGmt(getSomeGmtDate());
content.setBlogLang("en");
content.setBlogCharset("UTF-8");

```

## Akismet

### check

In order to check the if either your content is spam or not.

```java

Content content = new Content();
content.setCommentContent("This is spam");

Akismet.check(content, new Callback<Classification> () {
  public void invoke (Classification classificaiton) {
    // classificaiton is either SPAM, HAM, or UNKNOWN
    // content.getClassification() will equal the value of "classificaiton"
  }
});

```

The method will also return a Promise<Response> object.

```java

Promise<Response> response = Akismet.check(...)
response.onRedeem(new Callback<Response> () {
  // print out the actual response from the API
  public void invoke (Response response) {
    System.out.println(response.getBody());
  }
})

```

### classify

You can submit your content and tell Akismet that it is either SPAM or HAM.

```java

Content content = new Content();
content.setCommentContent("This is spam"):

Akismet.classify(content, Classification.SPAM, new Callback<Classification> () {
  public void invoke (Classificaiton classificaiton) {
    // content.getClassification() will now be set to Classification.SPAM
  }
});

```

# TODO

The ability to "verify" your API key is not present in this binding.

Steps would be to:
1) Write a test for com.manta.Akismet#verify(String, Callback<Boolean> cb)
2) Write a test for a class com.manta.akismet.Verifier#verify(String key, Callback<Boolean> cb)
3) Verifier should be derived from com.manta.akismet.Call
4) Implment the Verifier
5) Implement the Akismet#verify method to use the verifier
