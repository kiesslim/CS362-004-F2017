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

//NOTE: org.assertj jar included in github repo. the external jar must be added to the build before running
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;

 



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
   
   //SOURCE: https://automationrhapsody.com/soft-assertions-not-fail-junit-test/
   
   @ClassRule
   private SoftAssertions softly = null;
   
   @Before
   public void setUp() {
	   softly = new SoftAssertions();
   }

   @After
   public void tearDown() {
	   softly.assertAll();
   }
   
 
   //prints to console if test fails. prints test name, url tested, expected result and actual result.
   public void urlAssertThat(String test, UrlValidator urlVal, String url, boolean expected) {
	   if (urlVal.isValid(url) != expected) {
		   System.out.println(test + " failed. '" + url + "' returned " + !expected +". Expected " + expected);
		   softly.fail(test + " failed. '" + url + "' returned " + !expected +". Expected " + expected);
	   }
   }
   
   
   public void testManualTest()
   {
	   
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   //valid Scheme + Authority
	   String testStr = "Manual Testing: Valid Scheme & Authority";
	   System.out.print("\n########## Valid Scheme + Valid Authority#############\n");
	   
	   urlAssertThat(testStr, urlVal, "http://www.google.com", true);
	   urlAssertThat(testStr, urlVal, "http://google.com", true);
	   urlAssertThat(testStr, urlVal, "http://123.123.11.1", true);
	   urlAssertThat(testStr, urlVal, "google.com", true);
	   urlAssertThat(testStr, urlVal, "http://1.1.1.1", true);
	   urlAssertThat(testStr, urlVal, "http://0.0.0.0", true);
	   urlAssertThat(testStr, urlVal, "http://0.0.0.0", true);
	   urlAssertThat(testStr, urlVal, "http://255.255.255.255", true);
	   
	   
	   //invalid Scheme + valid Authority
	   System.out.print("\n########## Invalid Scheme + Valid Authority #############\n");
	   testStr = "Manual Testing: Invalid Scheme + Valid Authority";
	   urlAssertThat(testStr, urlVal, "http//www.google.com", false);
	   urlAssertThat(testStr, urlVal, "http.//www.google.com", false);
	   urlAssertThat(testStr, urlVal, "http://www.google.com", false);

	   
	   //valid Scheme + invalid Authority
	   System.out.print("\n########## Valid Scheme + Invalid Authority #############\n");
	   testStr = "Manual Testing: Valid Scheme + Invalid Authority";
	   urlAssertThat(testStr, urlVal, "http://www.google", false);
	   urlAssertThat(testStr, urlVal, "http://256.256.256.256", false);
	   urlAssertThat(testStr, urlVal, "http://257.257.257.257", false);
	   urlAssertThat(testStr, urlVal, ""+"1.1.1.1", false);
	   urlAssertThat(testStr, urlVal, "http://-1.-1.-1.-1", false);
	   
	   
	   // Valid Scheme+Authority + Valid Port number
	   System.out.print("\n########## Valid Scheme+Authority + Valid Port# #############\n");
	   testStr = "Manual Testing: Valid Scheme+Authority + Valid Port#";

	   urlAssertThat(testStr, urlVal, "http://www.google.com:80", true);
	   urlAssertThat(testStr, urlVal, "http://www.google.com:22", true);
	   urlAssertThat(testStr, urlVal, "http://www.google.com:650", true);
	   urlAssertThat(testStr, urlVal, "http://www.google.com:65534", true);
	   urlAssertThat(testStr, urlVal, "http://www.google.com:65535", true);
	   
	   // Valid Scheme+Authority + Invalid Port number
	   System.out.print("\n########## Valid Scheme+Authority + Invalid Port# #############\n");
	   testStr = "Manual Testing: Valid Scheme & Authority + Invalid Port#";

	   urlAssertThat(testStr, urlVal, "http://www.google.com:65536", false);
	   urlAssertThat(testStr, urlVal, "http://www.google.com:a", false);
	   urlAssertThat(testStr, urlVal, "http://www.google.com:-1000", false);
	   urlAssertThat(testStr, urlVal, "http://www.google.com:120000", false);
	   
	   // Valid Scheme+Authority + valid Query
	   System.out.print("\n########## Valid Scheme+Authority + Valid Query #############\n");
	   testStr = "Manual Testing: Valid Scheme & Authority + Valid Query";

	   urlAssertThat(testStr, urlVal, "http://www.mytest.com/page?field1=value1&field2=value2", true);
	   urlAssertThat(testStr, urlVal, "http://www.google.com/search?field1=value1&field2=value2", true);
	   urlAssertThat(testStr, urlVal, "https://www.google.de/search?dcr=0&source=hp&ei=d7MSWt_ANs6N0gW-xqa4Bg&q=best+coffee&oq=best+coffee&gs_l=psy-ab.3..0l10.13916.20661.0.20880.35.25.9.0.0.0.124.1849.20j2.25.0....0...1.1.64.psy-ab..1.31.1879.0..46j0i46k1j0i10k1.72.Dh0GurbZ7Js", true);
	   urlAssertThat(testStr, urlVal, "http://www.mytest.com?action=view", true);
	   
	   // Valid Scheme+Authority + Invalid Query
	   System.out.print("\n\n########## Valid Scheme+Authority + Invalid Query #############\n");
	   testStr = "Manual Testing: Valid Scheme & Authority + Invalid Query";
	   urlAssertThat(testStr, urlVal, "http://www.google.com/search?field1=value1field2=value2", false);
	   
	   // Valid Scheme+Authority + valid Path
	   System.out.print("\n\n########## Valid Scheme+Authority + Valid Path #############\n");
	   testStr = "Manual Testing: Valid Scheme & Authority + Valid Path";
	   urlAssertThat(testStr, urlVal, "https://docs.oracle.com/javase/tutorial/essential/io/formatting.html", true);
   }
   
   // Partition by schemes.
   public void testYourFirstPartition()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   String testURL;
	   
	   System.out.print("\n\n########## Testing partition: valid scheme: ##########\n");
	   for(int i = 0; i < validSchemes.length; i++) {
		   testURL = validSchemes[i].item += "www.google.com";
		   //System.out.println(testURL + "\n" + urlVal.isValid(testURL));
		   urlAssertThat("Partition Test- Valid Scheme", urlVal, testURL, true);
	   }
	   
	   System.out.print("\n########## Testing partition: invalid scheme: ##########\n");
	   for(int i = 0; i < invalidSchemes.length; i++) {
		   testURL = invalidSchemes[i].item += "www.google.com";
		   //System.out.println(testURL + "\n" + urlVal.isValid(testURL));
		   urlAssertThat("Partition Test- Invalid Scheme", urlVal, testURL, false);
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
		   //System.out.println(testURL + "\n" + urlVal.isValid(testURL));
		   urlAssertThat("Partition Test- Valid Authority", urlVal, testURL,true);
	   }
	   
	   System.out.print("\n########## Testing partition: invalid authorities: ##########\n");
	   for(int i = 0; i < invalidAuthority.length; i++) {
		   testURL = scheme + invalidAuthority[i].item;
		   //System.out.println(testURL + "\n" + urlVal.isValid(testURL));
		   urlAssertThat("Partition Test- Invalid Authority", urlVal, testURL, false);
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
		   //System.out.println(testURL + "\n" + urlVal.isValid(testURL));
		   urlAssertThat("Partition Test- Valid Ports", urlVal, testURL, true);
	   }
	   
	   System.out.print("\n########## Testing partition: invalid ports: ##########\n");
	   for(int i = 0; i < invalidPorts.length; i++) {
		   testURL = url + invalidPorts[i].item;
		   //System.out.println(testURL + "\n" + urlVal.isValid(testURL));
		   urlAssertThat("Partition Test- Invalid Ports", urlVal, testURL, false);
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
		   //System.out.println(testURL + "\n" + urlVal.isValid(testURL));
		   urlAssertThat("Partition Test- Valid Path", urlVal, testURL, true);
	   }
	   
	   System.out.print("\n########## Testing partition: invalid paths: ##########\n");
	   for(int i = 0; i < invalidPath.length; i++) {
		   testURL = url + invalidPath[i].item;
		   //System.out.println(testURL + "\n" + urlVal.isValid(testURL));
		   urlAssertThat("Partition Test- Invalid Path", urlVal, testURL, false);
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
		   //System.out.println(testURL + "\n" + urlVal.isValid(testURL));
		   urlAssertThat("Partition Test- Valid Query", urlVal, testURL, true);
	   }
	   
	   System.out.print("\n########## Testing partition: invalid queries: ##########\n");
	   for(int i = 0; i < invalidQuery.length; i++) {
		   testURL = url + invalidQuery[i].item;
		   //System.out.println(testURL + "\n" + urlVal.isValid(testURL));
		   urlAssertThat("Partition Test- Invalid Query", urlVal, testURL, false);
	   }
   }
   
   public void testIsValid()
   {
      String my_scheme[] = {
   				   "http://",
   				   "ftp://",
   				   "https://",
   				   "sftp://",
   				   "ssh://",
   				   ""
      },
   		   my_authority[] = {
   				   "amazon.com",
   				   "google.com",
   				   "apple.com",
   				   "asp.net",
   				   "words.edu",
   				   "piazza.net",
   				   "250.19.101.15"
      },
   		   my_port[] = {
   				   ":0",
   				   ":10000",
   				   ":13000",
   				   ":47027",
   				   ":65535",
   				   ""
      },
   		   my_path[] = {
   				   "/11/30/2017/index.html",
   				   "/words/gohere/index.php",
   				   "/32/1358/2016/blah.htm",
   				   "/somePage/ice/index.php",
   				   "/?this=that",
   				   "/?a=b&b=c",
   				   ""
      },
   		   my_bad_scheme[] = {
   				   "sftp://",
   				   "ssh://"
      },
   		   my_bad_authority[] = {
   				   "256.0.0.0",
   				   "0.261.1.400",
   				   "156.93.49.-72",
   				   "-1.1.17.200",
   				   ""
      },
   		   my_bad_port[] = {
   				   ":-1",
   				   ":66541",
   				   ":75121",
   				   ":84610",
   				   ":99999",
   				   "-65535"
      },
   		   my_bad_path[] = {
   				   ".html",
   				   ".php",
   				   ".c",
   				   ".js",
   				   "ghfdhd"
      };
	 
    	  test_all_valid(my_scheme, my_authority, my_port, my_path);
    	  test_bad_scheme(my_bad_scheme, my_authority, my_port, my_path);
    	  test_bad_authority(my_scheme, my_bad_authority, my_port, my_path);
    	  test_bad_port(my_scheme, my_authority, my_bad_port, my_path);
    	  test_bad_path(my_scheme, my_authority, my_port, my_bad_path);
	}
   
   
   //Called in testIsValid() tests valid scheme, authority, port, path
   //All should assert as true.
   void test_all_valid(String[] my_scheme, String[] my_authority, String[] my_port, String[] my_path)
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
		for (byte i = 0; i < my_scheme.length; i++) {
			for (byte j = 0; j < my_authority.length; j++) {
				for (byte k = 0; k < my_port.length; k++) {
					for (byte m = 0; m < my_path.length; m++) {
						String testURL;
						testURL = my_scheme[i] + my_authority[j] + my_port[k] + my_path[m];
						//System.out.println(testURL + "\n" + urlVal.isValid(testURL));
						urlAssertThat("Unit Test- test_all_valid", urlVal, testURL, true);
					}
				}
			}
		}
   }
   
   //Called in testIsValid() tests valid authority, port, path: invalid scheme
   //All should assert as false.
   void test_bad_scheme(String[] my_bad_scheme, String[] my_authority, String[] my_port, String[] my_path)
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   for (byte i = 0; i < my_bad_scheme.length; i++) {
			for (byte j = 0; j < my_authority.length; j++) {
				for (byte k = 0; k < my_port.length; k++) {
					for (byte m = 0; m < my_path.length; m++) {
						String testURL;
						testURL = my_bad_scheme[i] + my_authority[j] + my_port[k] + my_path[m];
						//System.out.println(testURL + "\n" + urlVal.isValid(testURL));
						urlAssertThat("Unit Test- bad Scheme", urlVal, testURL, false);
					}
				}
			}
	    }
   }
   
   //Called in testIsValid() tests valid scheme, port, path: invalid authority
   //All should assert as false.
   void test_bad_authority(String[] my_scheme, String[] my_bad_authority, String[] my_port, String[] my_path)
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);

		for (byte i = 0; i < my_scheme.length; i++) {
			for (byte j = 0; j < my_bad_authority.length; j++) {
				for (byte k = 0; k < my_port.length; k++) {
					for (byte m = 0; m < my_path.length; m++) {
						String testURL;
						testURL = my_scheme[i] + my_bad_authority[j] + my_port[k] + my_path[m];
						//System.out.println(testURL + "\n" + urlVal.isValid(testURL));
						urlAssertThat("Unit Test- bad Authority", urlVal, testURL, false);
					}
				}
			}
		}
   }
   
   //Called in testIsValid() tests valid scheme, authority, path: invalid port
   //All should assert as false.
   void test_bad_port(String[] my_scheme, String[] my_authority, String[] my_bad_port, String[] my_path)
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
		for (byte i = 0; i < my_scheme.length; i++) {
			for (byte j = 0; j < my_authority.length; j++) {
				for (byte k = 0; k < my_bad_port.length; k++) {
					for (byte m = 0; m < my_path.length; m++) {
						String testURL;
						testURL = my_scheme[i] + my_authority[j] + my_bad_port[k] + my_path[m];
						//System.out.println(testURL + "\n" + urlVal.isValid(testURL));
						urlAssertThat("Unit Test- bad port", urlVal, testURL, false);
					}
				}
			}
		}
   }
   
   //Called in testIsValid() tests valid scheme, port, authority: invalid path
   //All should assert as false.
   void test_bad_path(String[] my_scheme, String[] my_authority, String[] my_port, String[] my_bad_path)
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
		for (byte i = 0; i < my_scheme.length; i++) {
			for (byte j = 0; j < my_authority.length; j++) {
				for (byte k = 0; k < my_port.length; k++) {
					for (byte m = 0; m < my_bad_path.length; m++) {
						String testURL;
						testURL = my_scheme[i] + my_authority[j] + my_port[k] + my_bad_path[m];
						//System.out.println(testURL + "\n" + urlVal.isValid(testURL));
						urlAssertThat("Unit Test- bad path", urlVal, testURL, false);
					}
				}
			}
		}
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
