We have already outlined the general function of this program in our Results document, 
however below I have also provided a more detailed description of the program:

The program uses user input to take in queries and keywords that the manipulate the url and 
add to the search function. 

For the Twitter method, you can search for anyone's Tweets, and also for specific words in their Tweets.
The method creates an array list of all the tweets, and thus makes sure that no duplicates are printed.
After configuring the user timeline, with methods in the Twitter4J library, the for loop goes through 
each status and adds that tweet to the array list. 
It also prints the date of the tweet, and if a search word is specified, then the string command .contains
evaluates whether the tweet includes the query and then displays it.

For the campaign contributions method, you can search for a person or an employer and find their contributions.
The code uses regex on the CSV file, parsing for specific endpoints to display. The url was displayed by the
developer for the FEC. 

Similarly, for the NYTimes method, you can search for a string that you want in the headline or generally
in the article, and also include any other keywords. The query will be included in the url, and then the resulting
csv doc is parsed and regexed to display the headline of the article, the url, and a snippet of the piece. 

Hope you enjoy! 
