
import twitter4j.Paging;
//import com.newyorktimesapi.example.Utils.Utility;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetsFinalProject {
	public static void main (String[] args) throws TwitterException, IOException {
		
		
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("What do you want to know about a sports figure (athlete, coach, or owner)? "
				+ "\nEnter an integer 1-3: ");
		System.out.println("1. Twitter feed");
		System.out.println("2. Campaign donations");
		System.out.println("3. New York Times articles");
		
		
		boolean inputAccepted = false;
		while (!inputAccepted) {
			try {
	            int scannedNumber;
	            
	            String userInput = s.nextLine();

	            scannedNumber = Integer.parseInt(userInput);

	            if (!(scannedNumber==(1) || scannedNumber==(2) || (scannedNumber==3))) {
	                throw new Exception("Not a valid number! Please enter 1, 2, or 3: ");
	            }
	            else {
	            	inputAccepted=true;
	            	if (scannedNumber==1) {
	            		twitter();
	            	}
	            	if (scannedNumber==2) {
	            		campaignContributions();
	            		//bulk data?
	            		
	            	}
	            	if (scannedNumber==3) {
	            		getRelevantArticles();
	            	}
	            }


	        } 
			catch (Exception e) {
	            System.out.println("Please enter a valid input, 1, 2, or 3: ");
	        }
		}
    	
		
    	
	}
	
	public static void twitter() throws TwitterException {
		
		
		Scanner s = new Scanner(System.in);

		System.out.println("Want to know what your favorite sports figure has been Tweeting for the past year? ");
		System.out.println("Enter his or her Twitter username, without the @ symbol (for ex., realDonaldTrump)"
				+ "\nPlease be sure to spell correctly (it might take a couple seconds to verify): ");
		String name = s.nextLine();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.setDebugEnabled(true).setOAuthConsumerKey("TVfSji8fj3re91ArDUoLUBMtF");
		cb.setDebugEnabled(true).setOAuthConsumerSecret("LJXMvFLS1ZozyAsrUppsK9JuublB3sYcBMncjaALB42oHfmmBW");
		cb.setOAuthAccessToken("3000298052-tKiYi4T1pw1d4P5VoZ0D3EllL95Er0DmtQwvLsE");
		cb.setOAuthAccessTokenSecret("BobzYIHmCN4SZ5403dYSQJu9mILYKqIX0ZDNFBFO8CL2M");
		TwitterFactory tf = new TwitterFactory(cb.build());
		
		twitter4j.Twitter twitter = tf.getInstance();
		
		int pageno = 1;
	    ArrayList<String> tweets = new ArrayList<String>();
	    
	    
	    int mentions=0;
		
	    for (;;) {
	    	List<Status> all = new ArrayList<Status>();
	        all.addAll(twitter.getUserTimeline(name));
	        int length = all.size();
	        //System.out.println(length);
			if (length>0) break;
			System.out.println("Please enter a valid username: ");
			name = s.nextLine();
			
		}
		
		
		System.out.println("Would you like to know about this person's views on something? If no, type 'no'. If yes, type 'yes'");
		String response = s.nextLine();
		for (;;) {
			
			if ((response.equals("yes") || response.equals("no") )) break;
			System.out.println("Please enter a valid input, 'yes' or 'no': ");
			response = s.nextLine();
		}
		boolean querying=false;
		String q="";
		String query="";
		if (response.equals("yes")) {
			querying=true;
			System.out.println("Okay. What keyword should I search for?");
			q = s.nextLine();
			query = q.toLowerCase();
		}
		//String[] arr = new String[1];
		//arr[0]=query;
		//Query q = (Query)query;
		
		
		
		
		int count = 0;
	    System.out.println("\nShowing @" + name + "'s user timeline in the past year: ");
	    while (true) {

	      try {
	  	    List<Status> statuses = new ArrayList<Status>();

	        
	  	  int size = statuses.size();
	        Paging pg = new Paging(pageno++, 100);
	        statuses.addAll(twitter.getUserTimeline(name, pg));
	       
	        //System.out.println("size :" + size);

	        
	        for (Status status : statuses) {
	        	if (querying) {
	        		if(status.getText().toLowerCase().contains(query)){
	        			if (!tweets.contains(status.getUser().getName() + " : " + status.getText())) {
	        				
	        				count++;
	        				if (count< 2) System.out.println("\nThis user's name is: "  + status.getUser().getName());
		        			tweets.add(status.getUser().getName() + " : " + status.getText());
		        		    System.out.println("\n"+status.getCreatedAt().toString().substring(0, 10) + ": " + status.getText());
		        		    System.out.println("https://twitter.com/" + status.getUser().getScreenName() 
		        		    	    + "/status/" + status.getId());

	        			}
	        		  }
	        		/*else {
	        			System.out.println("Nope, not in this tweet. ");
	        		}*/
	        		
	        	}
	        	
	        		
	        	
	        	else {
	        		if (!tweets.contains(status.getUser().getName() + " : " + status.getText())) {
	        			tweets.add(status.getUser().getName() + " : " + status.getText());
	        		    System.out.println(status.getCreatedAt().toString().substring(0, 10) + ": " + status.getText());
	        		    System.out.println("https://twitter.com/" + status.getUser().getScreenName() 
	        		    	    + "/status/" + status.getId());
        			}	        		//System.out.println("tweeted at: " + status.getCreatedAt().toString());
	        	}
                
            }
	        if (statuses.size() == size)
	          break;
	      }
	      catch(TwitterException e) {

	        e.printStackTrace();
	      }
	    }
	    mentions = tweets.size();
	    if (response.equals("yes")) {
		    System.out.println("Total mentions of '" + q + "' in the past year: "+mentions);

	    }
		
		
	}
	
	public static String convertURLToString(URL u, HttpURLConnection h) {
		//ArrayList<String> contents = new ArrayList<String>();
		String contents="";
		try {
			Scanner in = new Scanner(h.getInputStream());
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				contents+=line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contents;
		
		
	}
	
	public static void regexStringFEC(String text) {
		String amount = Pattern.quote("\"contribution_receipt_amount\":") + "(.*?)" + Pattern.quote(",\"two_year_transaction_period\":");
		String recipient = Pattern.quote("\"name\":\"") + "(.*?)" + Pattern.quote("\",\"");
		String year = Pattern.quote("report_year\":") + "(.*?)" + Pattern.quote(",\"contributor");
		String result1="";
		String result3="";
		Pattern p1 = Pattern.compile(amount); 
		Pattern p2 = Pattern.compile(recipient);
		Pattern p3 = Pattern.compile(year);
		Matcher m1 = p1.matcher(text);
		Matcher m2 = p2.matcher(text);
		Matcher m3 = p3.matcher(text);
		while (m1.find() && m2.find() && m3.find()) {
			result1 = m1.group(1).split(",")[0];
			result3 = m3.group(1).split(",")[0];
			//System.out.println("got here!");
			System.out.println("\n" + "Donated $" + result1 + 
					" to " + m2.group(1) +
					" in " + result3);
			
		}
		if (result1.equals("")) {
			System.out.println("There are no campaign contributions for this person.");
		}
	}
	
	
	public static void campaignContributions () throws IOException {
		Scanner s = new Scanner(System.in);

		System.out.println("Want to know who your favorite sports figures (athletes, coaches, &  owners) support"
				+ " in political campaigns? (Data from Federal Election Commision)  "
				+ "\nI can find contributions by "
				+ "\na) just the name of the person (for ex., David Stern)"
				+ "\nb) the name of the organization/employer (for ex., National Basketball Association)"
				+ "\nc) both the name of a person and organization "
				+ " \n enter 'a' or 'b' or 'c'");
		
		
		String choice = s.nextLine();
	
		for (;;) {
			
			if ((choice.equals("a") || choice.equals("b") || choice.equals("c") )) break;
			System.out.println("Please enter a valid input, 'a', 'b', or 'c': ");
			choice = s.nextLine();
		}
		if (choice.equals("a")) {
			System.out.println("What name should I search for? ");
			String name = s.nextLine().replaceAll(" ", "%20");
			
			URL url = new URL("https://api.open.fec.gov/v1/schedules/schedule_a/?sort_null_only=false&sort=contribution_receipt_date&per_page=20"
					+ "&api_key=DbydCFLeEbpHNw5L8pBDJGPhPrujWENlZguhaALY&sort_hide_null=false"
					+ "&contributor_name=" + name);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			String all = convertURLToString(url, httpConnection);
			//System.out.println("got here!");
			regexStringFEC(all);
			
		}
		else if (choice.equals("b")) {
			System.out.println("Which employer/organization should I search for? ");
			String employer = s.nextLine().replaceAll(" ", "%20");
			URL url = new URL("https://api.open.fec.gov/v1/schedules/schedule_a/?sort_null_only=false&sort=contribution_receipt_date&per_page=20"
					+ "&api_key=DbydCFLeEbpHNw5L8pBDJGPhPrujWENlZguhaALY"
					+ "&contributor_employer=" + employer + "&sort_hide_null=false");
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			String all = convertURLToString(url, httpConnection);
			regexStringFEC(all);
		}
		else if  (choice.equals("c") ) {
			System.out.println("What name should I search for? ");
			String name = s.nextLine().replaceAll(" ", "%20");
			System.out.println("Which employer/organization should I search for? ");
			String employer = s.nextLine().replaceAll(" ", "%20");
			URL url = new URL("https://api.open.fec.gov/v1/schedules/schedule_a/?sort_null_only=false&sort=contribution_receipt_date&per_page=20"
					+ "&api_key=DbydCFLeEbpHNw5L8pBDJGPhPrujWENlZguhaALY"
					+ "&contributor_employer=" + employer + "&sort_hide_null=false"
					+ "&contributor_name=" + name);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			String all = convertURLToString(url, httpConnection);
			//System.out.println("got here!");
			regexStringFEC(all);
		}
		
		
		
		
		
		
		
	}
	
	
	
	public static void regexStringNYTIMES(String text) {
		String regexString1 = Pattern.quote("\"snippet\":\"") + "(.*?)" + Pattern.quote("\",\"lead_paragraph");
		String regexString2 = Pattern.quote("web_url\":\"") + "(.*?)" + Pattern.quote("\",\"snippet");
		String regexString3 = Pattern.quote("\"headline\":{\"main\":") + "(.*?)" + Pattern.quote(",\"kicker\"");
		Pattern p1 = Pattern.compile(regexString1); 
		Pattern p2 = Pattern.compile(regexString2);
		Pattern p3 = Pattern.compile(regexString3);
		Matcher m1 = p1.matcher(text);
		Matcher m2 = p2.matcher(text);
		Matcher m3 = p3.matcher(text);
		while (m1.find() && m2.find() && m3.find()) {
			System.out.println("\n" + m3.group(1) + "\n" + m1.group(1));
			System.out.println(m2.group(1));
			
		}
	}
	
	public static void getRelevantArticles () throws IOException {
		
		Scanner s = new Scanner(System.in);

		String choice="";
		
		
		boolean valid = false;
		
		System.out.println("Want to read some New York Times articles about a particular sports figure? "
				+ "I will list 10 suggestions!  "
				+ "\nI can give you articles where a) the person makes a headline OR "
				+ "b) mentions the person overall in the article"
				+ " \n enter 'a' or 'b'");
		choice = s.nextLine();
		for (;;) {
			
			if ((choice.equals("a") || choice.equals("b") )) break;
			System.out.println("Please enter a valid input, 'a' or 'b': ");
			choice = s.nextLine();
		}
		
		
		if (choice.equals("a")) {
			System.out.println("Type any keywords you want to see in the headline: ");
			String headline = s.nextLine();
			System.out.println("Type other keywords (if none, hit 'enter'): ");
			String name = s.nextLine().toLowerCase().replace(" ", "+");
			System.out.println("Would you like to browse for articles in a specific category? Please answer 'yes' or 'no");
			String answer = s.nextLine();
			for (;;) {
				
				if ((answer.equals("yes") || answer.equals("no") )) break;
				System.out.println("Please enter a valid input, 'yes' or 'no': ");
				answer = s.nextLine();
			}
			if (answer.equals("yes")) {
				System.out.println("Here is a list of some of the categories you can search from:"
						+ "\nCulture \nDining \nEducation \nEntrepreneurs \nEditorial \nFashion \nBusiness \nGiving \nHealth \nArts \nBooks \nObituary"
						+ "\nPolitics \nSports \nStyle \nSociety \nU.S. " + 
						"\n type in one category (for ex., 'Giving' or 'Sports'");
				String category = s.nextLine().replaceAll(" ", "+");
				URL url = new URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=" + 
									"qyJFTpNiUAnSovfufORO5bzIVZuZ1Oz5" 
									+"&q=" + name + "&fq=headline:(\""+ headline +"\")"+"&fq=news_desk:(\"" + category + "\")" );
				//System.out.println(url);
				URLConnection connection = url.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				String all = convertURLToString(url, httpConnection);
				regexStringNYTIMES(all);
				
			}
			
			else if (answer.equals("no")) {
				URL url = new URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=" + "qyJFTpNiUAnSovfufORO5bzIVZuZ1Oz5" +"&q=" + name + "&fq=headline:(\""+ headline +"\")");
				URLConnection connection = url.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				String all = convertURLToString(url, httpConnection);
				regexStringNYTIMES(all);
				
			}
			
		}
		else if (choice.equals("b")) {
			System.out.println("Type his/her name and/or keywords: ");
			String name = s.nextLine().toLowerCase().replace(" ", "+");
			System.out.println("Would you like to browse for articles in a specific category? Please answer 'yes' or 'no");
			String answer = s.nextLine();
			for (;;) {
				
				if ((answer.equals("yes") || answer.equals("no") )) break;
				System.out.println("Please enter a valid input, 'yes' or 'no': ");
				answer = s.nextLine();
			}
			if (answer.equals("yes")) {
				System.out.println("Here is a list of some of the categories you can search from"
						+ "\n Please pick one category:"
						+ "\nCulture \nDining \nEducation \nEntrepreneurs \nEditorial \nFashion \nBusiness \nGiving \nHealth \nArts \nBooks \nObituary"
						+ "\nPolitics \nSports \nStyle \nSociety \nU.S. " + 
						"\n type in one category (for ex., 'Giving' or 'Sports'");
				String category = s.nextLine().replaceAll(" ", "+").toLowerCase();
				for (;;) {
					
					if ((category.equals("culture") || category.equals("dining") || category.equals("education") || category.equals("entrepreneurs") ||
							category.equals("editorial") ||category.equals("fashion") || category.equals("business") || category.equals("giving") ||
							category.equals("health") || category.equals("arts") || category.equals("books") ||category.equals("obituary") ||
							category.equals("politics") || category.equals("sports") || category.equals("style") || category.equals("society") ||
							category.equals("u.s."))) {
						//System.out.println("yes");
						break;
					}
					System.out.println("Please enter a valid category ");
					category = s.nextLine();
				}
				URL url = new URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=" + 
									"qyJFTpNiUAnSovfufORO5bzIVZuZ1Oz5" 
									+"&q=" + name + "&fq=news_desk:(\"" + category + "\")" );
				//System.out.println(url);
				URLConnection connection = url.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				String all = convertURLToString(url, httpConnection);
				regexStringNYTIMES(all);
				
			}
			
			else if (answer.equals("no")) {
				URL url = new URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=" + "qyJFTpNiUAnSovfufORO5bzIVZuZ1Oz5" +"&q=" + name);
				URLConnection connection = url.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				String all = convertURLToString(url, httpConnection);
				regexStringNYTIMES(all);
				
			}
		}
		
		
		
		
	}
	
}
		
		
	
