# ForecastYahooRest
You can find under this repository 2 projects (AndroidStudio) that aim to show how to build an Architecture for your Android project. This repository is bound to conferences I gave on good practices on Android. 

*ForecastRestWithLibs*
==========    
This project is bound to the conference I gave at DevoxxFr 2016 (3h of good practices).
You'll find a project where the architecture is based on n-tier and MVP, where we use the following librairies :
>-AppCompat/RecyclerView/CardView and Design    
>-EventBus    
>-SugarOrm    
>-Retrofit/OkIo/OkHttp and Moshi (from Square)    
>-Junit    
>-Mockito    
>-Espresso    
>-JodaTime   

The project is just there to display forecasts and show how to implement the use of a REST service.

*YahooForecast*
==========    
The good practice I inject in the project are those I explained in the conference "An Android Journey" that I gave at the DroidCon London in 2014 (https://skillsmatter.com/skillscasts/5971-an-android-journey) or at the ParisAUG (https://www.youtube.com/watch?v=VpeLYnwXlj4)  or ToulouseAUG (https://www.youtube.com/watch?v=br3drCNFti8) in 2014-2015.
You'll find a project where the architecture is based on n-tier and doesn't use any librairy. This project aims to show how difficult and painfull it is to build a project without librairy when trying to implement good practices.
It show you how to implement a simple application that consume a REST service, it implies :  
1. Use an n-tiers architecture  
2. Set the asynchronicity barrier on the service layer  
3. Watch the connectivity state to change the behavior when connectivity is lost (I won't make a http call if i'm not connected)  
4. Use a ServiceManager to load services and kill them when the application died  
5. ... And a lot of others good practices  

This project is the exercice of the "J'Assimile Android" one of mine Android training that help you becoming a good or a master Android developper: http://www.android2ee.com/Formations-Android/formation-j-assimile-android.html  
And : http://www.android2ee.com/Formations-Android/formation-complete.html  
  
**Last important stuff to notice a document that explains in details what is the project structure can be found under ForecastYahooRest\doc :)**  
And a last think : This project is the first version where I only use native component of the system, this is a educative project. 

 You can find the application on GooglePlay to better see what I'm doing here :https://play.google.com/store/apps/details?id=com.android2ee.formation.restservice.sax.forecastyahoo  
 
> Written with [StackEdit](https://stackedit.io/).
