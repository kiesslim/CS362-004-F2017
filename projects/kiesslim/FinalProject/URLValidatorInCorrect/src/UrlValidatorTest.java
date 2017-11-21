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
	   
	   //invalid Scheme + valid Authority
	   System.out.print("\n########## Invalid Scheme + Valid Authority #############\n");
	   System.out.println("http//www.google.com \n" + urlVal.isValid("http//www.google.com"));
	   System.out.println("http.//www.google.com \n" + urlVal.isValid("http.//www.google.com"));
	   System.out.println("http:/www.google.com \n" + urlVal.isValid("http.//www.google.com"));
	   
	   //valid Scheme + invalid Authority
	   System.out.print("\n########## Valid Scheme + Invalid Authority #############\n");
	   System.out.println("http://www.google \n" + urlVal.isValid("http://www.google"));
	   System.out.println("http://256.256.256.256 \n" + urlVal.isValid("http://256.256.256.256"));
	   System.out.println("http://257.257.257.257 \n" + urlVal.isValid("http://257.257.257.257"));
	   System.out.println("1.1.1.1 \n" + urlVal.isValid(""+"1.1.1.1"));
	   
	   // Valid Scheme+Authority + Valid Port number
	   System.out.print("\n########## Valid Scheme+Authority + Valid Port# #############\n");
	   System.out.println("http://www.google.com:80 \n" + urlVal.isValid("http://www.google.com:80"));
	   System.out.println("http://www.google.com:22 \n" + urlVal.isValid("http://www.google.com:22"));
	   System.out.println("http://www.google.com:650 \n" + urlVal.isValid("http://www.google.com:650"));
	   System.out.println("http://www.google.com:65534 \n" + urlVal.isValid("http://www.google.com:65534"));
	   System.out.println("http://www.google.com:65535 \n" + urlVal.isValid("http://www.google.com:65535"));
	   
	   // Valid Scheme+Authority + Valid Port number
	   System.out.print("\n########## Valid Scheme+Authority + Invalid Port# #############\n");
	   System.out.println("http://www.google.com:65536 \n" + urlVal.isValid("http://www.google.com:65536"));
	   System.out.println("http://www.google.com:a \n" + urlVal.isValid("http://www.google.com:a"));
	   System.out.println("http://www.google.com:-10000 \n" + urlVal.isValid("http://www.google.com:-1000"));
	   System.out.println("http://www.google.com:120000 \n" + urlVal.isValid("http://www.google.com:120000"));
	   
	   
	   // Valid Scheme+Authority + valid Query
	   System.out.print("\n########## Valid Scheme+Authority + Valid Query #############\n");
	   System.out.println("http://www.mytest.com/page?field1=value1&field2=value2\n" + urlVal.isValid("http://www.mytest.com/page?field1=value1&field2=value2"));
	   System.out.println("http://www.google.com/search?field1=value1&field2=value2\n" + urlVal.isValid("http://www.google.com/search?field1=value1&field2=value2"));
	   System.out.println("https://www.google.de/search?dcr=0&source=hp&ei=d7MSWt_ANs6N0gW-xqa4Bg&q=best+coffee&oq=best+coffee&gs_l=psy-ab.3..0l10.13916.20661.0.20880.35.25.9.0.0.0.124.1849.20j2.25.0....0...1.1.64.psy-ab..1.31.1879.0..46j0i46k1j0i10k1.72.Dh0GurbZ7Js: \n" + urlVal.isValid("https://www.google.de/search?dcr=0&source=hp&ei=d7MSWt_ANs6N0gW-xqa4Bg&q=best+coffee&oq=best+coffee&gs_l=psy-ab.3..0l10.13916.20661.0.20880.35.25.9.0.0.0.124.1849.20j2.25.0....0...1.1.64.psy-ab..1.31.1879.0..46j0i46k1j0i10k1.72.Dh0GurbZ7Js"));
	   System.out.println("https://www.google.de/maps/@52.5068441,13.4247317,10z?hl=en\n" + urlVal.isValid("https://www.google.de/maps/@52.5068441,13.4247317,10z?hl=en"));
	   System.out.println("http://www.mytest.com?action=view \n" + urlVal.isValid("http://www.mytest.com?action=view"));
	   
	   // Valid Scheme+Authority + Invalid Query
	   System.out.print("\n########## Valid Scheme+Authority + Invalid Query #############\n");
	   System.out.print("http://www.google.com/search?field1=value1field2=value2 \n" + urlVal.isValid("http://www.google.com/search?field1=value1field2=value2"));
	  
	   
	   // Valid Scheme+Authority + valid Path
	   System.out.print("\n\n########## Valid Scheme+Authority + Valid Path #############\n");
	   System.out.println("https://docs.oracle.com/javase/tutorial/essential/io/formatting.html \n" + urlVal.isValid("https://docs.oracle.com/javase/tutorial/essential/io/formatting.html"));
	  
   }
   
   
   public void testYourFirstPartition()
   {
	   
   }
   
   public void testYourSecondPartition(){
	   
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
   

}
