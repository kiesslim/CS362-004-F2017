/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import junit.framework.TestCase;





/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {

   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }

   
   public void testManualTest()
   {
	   
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   //valid Scheme + Authority
	   System.out.print("\n########## Valid Scheme + Valid Authority#############\n");
	   System.out.println("http://www.google.com \n" + urlVal.isValid("http://www.google.com"));
	   System.out.println("http://google.com \n" + urlVal.isValid("http://google.com"));
	   System.out.println("http://123.123.11.1 \n" + urlVal.isValid("http://123.123.11.1"));
	   System.out.println("google.com: " + urlVal.isValid("google.com"));
	   System.out.println("http://1.1.1.1\n" + urlVal.isValid("http://1.1.1.1"));
	   System.out.println("http://0.0.0.0\n" + urlVal.isValid("http://0.0.0.0"));
	   System.out.println("http://255.255.255.255\n" + urlVal.isValid("http://255.255.255.255"));
	   assertTrue(urlVal.isValid("http://www.google.com"));
	   assertTrue(urlVal.isValid("http://google.com"));
	   assertTrue(urlVal.isValid("http://123.123.11.1"));
	   //assertTrue(urlVal.isValid("google.com"));
	  
	   
	   //invalid Scheme + valid Authority
	   System.out.print("\n########## Invalid Scheme + Valid Authority #############\n");
	   System.out.println("http//www.google.com \n" + urlVal.isValid("http//www.google.com"));
	   System.out.println("http.//www.google.com \n" + urlVal.isValid("http.//www.google.com"));
	   System.out.println("http:/www.google.com \n" + urlVal.isValid("http.//www.google.com"));
	   assertFalse(urlVal.isValid("http//www.google.com"));
	   assertFalse(urlVal.isValid("http.//www.google.com"));
	   assertFalse(urlVal.isValid("http.//www.google.com"));
	   
	   
	   //valid Scheme + invalid Authority
	   System.out.print("\n########## Valid Scheme + Invalid Authority #############\n");
	   System.out.println("http://www.google \n" + urlVal.isValid("http://www.google"));
	   System.out.println("http://256.256.256.256 \n" + urlVal.isValid("http://256.256.256.256"));
	   System.out.println("http://257.257.257.257 \n" + urlVal.isValid("http://257.257.257.257"));
	   System.out.println("1.1.1.1 \n" + urlVal.isValid(""+"1.1.1.1"));
	   System.out.println("http://-1.-1.-1.-1\n" + urlVal.isValid("http://-1.-1.-1.-1"));
	   assertFalse(urlVal.isValid("http://www.google"));
	   assertFalse(urlVal.isValid("http://256.256.256.256"));
	   assertFalse(urlVal.isValid("http://257.257.257.257"));
	   assertFalse(urlVal.isValid(""+"1.1.1.1"));
	   
	   // Valid Scheme+Authority + Valid Port number
	   System.out.print("\n########## Valid Scheme+Authority + Valid Port# #############\n");
	   System.out.println("http://www.google.com:80 \n" + urlVal.isValid("http://www.google.com:80"));
	   System.out.println("http://www.google.com:22 \n" + urlVal.isValid("http://www.google.com:22"));
	   System.out.println("http://www.google.com:650 \n" + urlVal.isValid("http://www.google.com:650"));
	   System.out.println("http://www.google.com:65534 \n" + urlVal.isValid("http://www.google.com:65534"));
	   System.out.println("http://www.google.com:65535 \n" + urlVal.isValid("http://www.google.com:65535"));
	   assertTrue(urlVal.isValid("http://www.google.com:80"));
	   assertTrue(urlVal.isValid("http://www.google.com:22"));
	   assertTrue(urlVal.isValid("http://www.google.com:650"));
	   assertTrue(urlVal.isValid("http://www.google.com:65534"));
	   assertTrue(urlVal.isValid("http://www.google.com:65535"));
	   
	   // Valid Scheme+Authority + Valid Port number
	   System.out.print("\n########## Valid Scheme+Authority + Invalid Port# #############\n");
	   System.out.println("http://www.google.com:65536 \n" + urlVal.isValid("http://www.google.com:65536"));
	   System.out.println("http://www.google.com:a \n" + urlVal.isValid("http://www.google.com:a"));
	   System.out.println("http://www.google.com:-10000 \n" + urlVal.isValid("http://www.google.com:-1000"));
	   System.out.println("http://www.google.com:120000 \n" + urlVal.isValid("http://www.google.com:120000"));
	   assertFalse(urlVal.isValid("http://www.google.com:65536"));
	   assertFalse(urlVal.isValid("http://www.google.com:a"));
	   assertFalse(urlVal.isValid("http://www.google.com:-1000"));
	   assertFalse(urlVal.isValid("http://www.google.com:120000"));
	   
	   
	   // Valid Scheme+Authority + valid Query
	   System.out.print("\n########## Valid Scheme+Authority + Valid Query #############\n");
	   System.out.println("http://www.mytest.com/page?field1=value1&field2=value2\n" + urlVal.isValid("http://www.mytest.com/page?field1=value1&field2=value2"));
	   System.out.println("http://www.google.com/search?field1=value1&field2=value2\n" + urlVal.isValid("http://www.google.com/search?field1=value1&field2=value2"));
	   System.out.println("https://www.google.de/search?dcr=0&source=hp&ei=d7MSWt_ANs6N0gW-xqa4Bg&q=best+coffee&oq=best+coffee&gs_l=psy-ab.3..0l10.13916.20661.0.20880.35.25.9.0.0.0.124.1849.20j2.25.0....0...1.1.64.psy-ab..1.31.1879.0..46j0i46k1j0i10k1.72.Dh0GurbZ7Js: \n" + urlVal.isValid("https://www.google.de/search?dcr=0&source=hp&ei=d7MSWt_ANs6N0gW-xqa4Bg&q=best+coffee&oq=best+coffee&gs_l=psy-ab.3..0l10.13916.20661.0.20880.35.25.9.0.0.0.124.1849.20j2.25.0....0...1.1.64.psy-ab..1.31.1879.0..46j0i46k1j0i10k1.72.Dh0GurbZ7Js"));
	   System.out.println("https://www.google.de/maps/@52.5068441,13.4247317,10z?hl=en\n" + urlVal.isValid("https://www.google.de/maps/@52.5068441,13.4247317,10z?hl=en"));
	   System.out.println("http://www.mytest.com?action=view \n" + urlVal.isValid("http://www.mytest.com?action=view"));
	   assertTrue(urlVal.isValid("http://www.mytest.com/page?field1=value1&field2=value2"));
	   assertTrue(urlVal.isValid("http://www.google.com/search?field1=value1&field2=value2"));
	   assertTrue(urlVal.isValid("https://www.google.de/search?dcr=0&source=hp&ei=d7MSWt_ANs6N0gW-xqa4Bg&q=best+coffee&oq=best+coffee&gs_l=psy-ab.3..0l10.13916.20661.0.20880.35.25.9.0.0.0.124.1849.20j2.25.0....0...1.1.64.psy-ab..1.31.1879.0..46j0i46k1j0i10k1.72.Dh0GurbZ7Js"));
	   assertTrue(urlVal.isValid("https://www.google.de/maps/@52.5068441,13.4247317,10z?hl=en"));
	   assertTrue(urlVal.isValid("http://www.mytest.com?action=view"));
	   
	   
	   // Valid Scheme+Authority + Invalid Query
	   System.out.print("\n\n########## Valid Scheme+Authority + Invalid Query #############\n");
	   System.out.print("http://www.google.com/search?field1=value1field2=value2 \n" + urlVal.isValid("http://www.google.com/search?field1=value1field2=value2"));
	   assertFalse(urlVal.isValid("http://www.google.com/search?field1=value1field2=value2"));
	   
	   // Valid Scheme+Authority + valid Path
	   System.out.print("\n\n########## Valid Scheme+Authority + Valid Path #############\n");
	   System.out.print("https://docs.oracle.com/javase/tutorial/essential/io/formatting.html \n" + urlVal.isValid("https://docs.oracle.com/javase/tutorial/essential/io/formatting.html" + "\n"));
	   assertTrue(urlVal.isValid("https://docs.oracle.com/javase/tutorial/essential/io/formatting.html"));
   }
   
   // Partition by schemes.
   public void testYourFirstPartition()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   String testURL;
	   
	   System.out.print("\n\n########## Testing partition: valid scheme: ##########\n");
	   for(int i = 0; i < validSchemes.length; i++) {
		   testURL = validSchemes[i].item += "www.google.com";
		   System.out.println(testURL + "\n" + urlVal.isValid(testURL));
	   }
	   
	   System.out.print("\n########## Testing partition: invalid scheme: ##########\n");
	   for(int i = 0; i < invalidSchemes.length; i++) {
		   testURL = invalidSchemes[i].item += "www.google.com";
		   System.out.println(testURL + "\n" + urlVal.isValid(testURL));
	   }
   }
   
   // Partition by authority/host.
   public void testYourSecondPartition(){
	   
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   String testURL;
	   String scheme = "http://";
	   System.out.print("\n########## Testing partition: valid authorities: ##########\n");
	   for(int i = 0; i < validAuthority.length; i++) {
		   testURL = scheme + validAuthority[i].item;
		   System.out.println(testURL + "\n" + urlVal.isValid(testURL));
	   }
	   
	   System.out.print("\n########## Testing partition: invalid authorities: ##########\n");
	   for(int i = 0; i < invalidAuthority.length; i++) {
		   testURL = scheme + invalidAuthority[i].item;
		   System.out.println(testURL + "\n" + urlVal.isValid(testURL));
	   }
   }
   
   // Partition by port.
   public void testYourThirdPartition(){
	   
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   String testURL;
	   String url = "http://www.google.com";
	   
	   System.out.print("\n\n########## Testing partition: valid ports: ##########\n");
	   for(int i = 0; i < validPorts.length; i++) {
		   testURL =  url + validPorts[i].item;
		   System.out.println(testURL + "\n" + urlVal.isValid(testURL));
	   }
	   
	   System.out.print("\n########## Testing partition: invalid ports: ##########\n");
	   for(int i = 0; i < invalidPorts.length; i++) {
		   testURL = url + invalidPorts[i].item;
		   System.out.println(testURL + "\n" + urlVal.isValid(testURL));
	   }
   }
   
   // Partition by path.
   public void testYourFourthPartition(){
	   
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   String testURL;
	   String url = "http://www.google.com";
	   
	   System.out.print("\n########## Testing partition: valid paths: ##########\n");
	   for(int i = 0; i < validPath.length; i++) {
		   testURL =  url + validPath[i].item;
		   System.out.println(testURL + "\n" + urlVal.isValid(testURL));
	   }
	   
	   System.out.print("\n########## Testing partition: invalid paths: ##########\n");
	   for(int i = 0; i < invalidPath.length; i++) {
		   testURL = url + invalidPath[i].item;
		   System.out.println(testURL + "\n" + urlVal.isValid(testURL));
	   }
   }
   
   // Partition by query.
   public void testYourFifthPartition(){
	   
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   String testURL;
	   String url = "http://www.google.com/search";
	   
	   System.out.print("\n########## Testing partition: valid queries: ##########\n");
	   for(int i = 0; i < validQuery.length; i++) {
		   testURL =  url + validQuery[i].item;
		   System.out.println(testURL + "\n" + urlVal.isValid(testURL));
	   }
	   
	   System.out.print("\n########## Testing partition: invalid queries: ##########\n");
	   for(int i = 0; i < invalidQuery.length; i++) {
		   testURL = url + invalidQuery[i].item;
		   System.out.println(testURL + "\n" + urlVal.isValid(testURL));
	   }
   }
   
   public void testIsValid()
   {
	   for(int i = 0;i<10000;i++)
	   {
		   
	   }
   }
   
   public void testAnyOtherUnitTest()
   {
	   
   }
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */
 //-------------------- Partitions based on valid and invalid URL parts: --------------------------------//
   
   // Used in research of valid ursl schemes:
   // https://help.contrast.co/hc/en-us/articles/200865293-URL-Scheme-Intro-for-App-Developers
   // https://en.wikipedia.org/wiki/URL
   ResultPair[] validSchemes = {
		   new ResultPair("http://", true),
           new ResultPair("ftp://", true),
           new ResultPair("irc://", true),
           new ResultPair("mailto://", true),
           new ResultPair("", true)
   };
   
   ResultPair[] invalidSchemes = {
           new ResultPair("3ht://", false),
           new ResultPair("http:/", false),
           new ResultPair("ftp", false),
           new ResultPair("httpd", false),
           new ResultPair("telnet", false)
   };
   
   // Used in research of valid authority/host names:
   // https://en.wikipedia.org/wiki/Hostname#Restrictions_on_valid_hostnames
   ResultPair[] validAuthority = {
		   new ResultPair("www.google.com", true),
           new ResultPair("go.au", true),
           new ResultPair("255.255.255.255", true),
           new ResultPair("0.0.0.0", true),
           new ResultPair("255.com", true)
   };
   
   ResultPair[] invalidAuthority = {
           new ResultPair("256.256.256.256", false),
           new ResultPair("www.ya hoo.com", false),
           new ResultPair("-aaa.", false),
           new ResultPair("www.goo/gle.com", false),
           new ResultPair("", false)
   };
   
 // Used in research of valid port numbers/ranges: 
 // https://stackoverflow.com/questions/113224/what-is-the-largest-tcp-ip-network-port-number-allowable-for-ipv4
 // https://en.wikipedia.org/wiki/Port_(computer_networking)#Use_in_URLs
   ResultPair[] validPorts = {
		   new ResultPair(":0", true),
           new ResultPair(":22", true),
           new ResultPair(":80", true),
           new ResultPair(":65335", true),
           new ResultPair(":abc", true)
   };
   
   ResultPair[] invalidPorts = {
           new ResultPair(":-10000", false),
           new ResultPair(":-1", false),
           new ResultPair(":65336", false),
           new ResultPair(":75000", false)
   };
   
   // Used in research of valid url paths:
   // https://stackoverflow.com/questions/4669692/valid-characters-for-directory-part-of-a-url-for-short-links
   // http://www.ietf.org/rfc/rfc3986.txt
   ResultPair[] validPath = {
		   new ResultPair("/test", true),
           new ResultPair("/$test", true),
           new ResultPair("/&test", true),
           new ResultPair("/*test", true),
           new ResultPair("", true)
   };
   
   ResultPair[] invalidPath = {
           new ResultPair("/..", false),
           new ResultPair("/:testpath", false),
           new ResultPair("/?testpath", false),
           new ResultPair("#testpath", false),
           new ResultPair("/test//file", false)
   };
  
   // Used in research of valid query strings:
   // https://perishablepress.com/how-to-write-valid-url-query-string-parameters/
   ResultPair[] validQuery = {
		   new ResultPair("?foo=bar", true),
           new ResultPair("?x=1&amp;y=2", true),
           new ResultPair("?key1=value1&key2=value2", true),
           new ResultPair("?title=Sample%20Book%20Title", true),
           new ResultPair("", true)
   };
   
   ResultPair[] invalidQuery = {
		   new ResultPair("#foo=bar", false),
		   new ResultPair("?action?edit", false),
		   new ResultPair("?x=1&;y=2", false),
		   new ResultPair("?title=Sample Book Title", false),
           new ResultPair("?foo:bar", false)
   };

}
