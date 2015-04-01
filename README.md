# ForecastYahooRest
You can find the application on GooglePlay to better see what I'm doing here :https://play.google.com/store/apps/details?id=com.android2ee.formation.restservice.sax.forecastyahoo  
This project aims to show what REST architecture you should set when requesting a Rest service.  
It show you how to implement a simple application that consume a REST service, it implies :  
1. Use an n-tiers architecture  
2. Set the asynchronicity barrier on the service layer  
3. Watch the connectivity state to change the behavior when connectivity is lost (I won't make a http call if i'm not connected)  
4. Use a ServiceManager to load services and kill them when the application died  
5. ... And a lot of others good practices  

This project is the exercice of the "J'Assimile Android" one of mine Android training that help you becoming a good or a master Android developper: http://www.android2ee.com/Formations-Android/formation-j-assimile-android.html  
And : http://www.android2ee.com/Formations-Android/formation-complete.html  
  
The good practice I inject in the project are those that I explains in the conference "An Android Journey" that I gave at the DroidCon London in 2014 (https://skillsmatter.com/skillscasts/5971-an-android-journey) or at the ParisAUG (https://www.youtube.com/watch?v=VpeLYnwXlj4)  or ToulouseAUG (https://www.youtube.com/watch?v=br3drCNFti8) in 2014-2015.
  
Last important stuff to notice a document that explains in details what is the project structure can be found under ForecastYahooRest\doc :)  
And a last think : This project is the first version where I only use native component of the system, this is a educative project. The following branches (I am working on) are :  
1. WithSquareLibs,    
2. WithLibs and   
3. Lollipop  
