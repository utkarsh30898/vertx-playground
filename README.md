# vertx-playground
Learning vertx

1) Introduction to Vert.x
   - build it using mv clean build
   - run int using mvn exec:java
   - A typical verticle looks like this

     ![image](https://github.com/utkarsh30898/vertx-playground/assets/49248032/5b646835-b6a1-4e7c-93a5-00db52c79aa8)

   - it is a class that extends Abstract Verticle
   - verticle can be thought as smallest unit of deployment
   - verticles are by default guaranteed to be run single threaded so you don't have to worry about concurrency issues
  
2) Introduction To Router
   - Help us in dealing with the multiple paths
   - add dependency vertx-web
     ![image](https://github.com/utkarsh30898/vertx-playground/assets/49248032/fbc48313-1016-4b2b-b243-e2248ba34758)
   - vert.x router gives us capabiity to setup different callback handlers for different paths
   - ctx is routing context object which you get on using router. It has request object in it.
   - Making code Modualrised :
     ![image](https://github.com/utkarsh30898/vertx-playground/assets/49248032/6cc01713-c773-4311-8d53-009338dad786)

     
