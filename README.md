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

3) Vertx Event Bus
   - Traditional code is called blockking code
   - Vert.x code is asyncronous or callback based.
   - Suppose helloNameVertx takes a lot of time in execution -> It will block the event loop -> But we don't want to block it as it handels multiple requests and        response in parallel so that we can handle much more load -> So, we create the new verticle and make it do that task and return the response to event handler       at a particular address and in main verticle you can go and listen to that response on that address.
   - We recieve the messesage using eventbus and then register a consumer at particular address and handle that message
           ![image](https://github.com/utkarsh30898/vertx-playground/assets/49248032/6632a663-bee3-4d8e-bc99-ec477216c037)
   - New HelloVerticle Class :
     ![image](https://github.com/utkarsh30898/vertx-playground/assets/49248032/fe77cecf-ae13-4966-903e-03496a9cdfac)

   - Deploy that verticle inside MainVerticle : vertx.deployVerticle(new HelloVertice());
   -  Now from Main Vertice pass request to that addr you want response from, you will get the repy and then handle and return that repy.
     ![image](https://github.com/utkarsh30898/vertx-playground/assets/49248032/deb2f7b3-2bbf-42ba-8242-ac042ef92705)


  

