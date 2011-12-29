
/**
 * NewsSubsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.2  Built on : Sep 06, 2010 (09:42:01 CEST)
 */

    package org.subscription.news;

    /**
     *  NewsSubsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class NewsSubsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public NewsSubsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public NewsSubsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for deleteElement method
            * override this method for handling normal response from deleteElement operation
            */
           public void receiveResultdeleteElement(
                    org.subscription.news.NewsSubsStub.DeleteElementResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteElement operation
           */
            public void receiveErrordeleteElement(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for postCollection method
            * override this method for handling normal response from postCollection operation
            */
           public void receiveResultpostCollection(
                    org.subscription.news.NewsSubsStub.PostCollectionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from postCollection operation
           */
            public void receiveErrorpostCollection(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for putElement method
            * override this method for handling normal response from putElement operation
            */
           public void receiveResultputElement(
                    org.subscription.news.NewsSubsStub.PutElementResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from putElement operation
           */
            public void receiveErrorputElement(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getURLs method
            * override this method for handling normal response from getURLs operation
            */
           public void receiveResultgetURLs(
                    org.subscription.news.NewsSubsStub.URLsList result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getURLs operation
           */
            public void receiveErrorgetURLs(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteCollection method
            * override this method for handling normal response from deleteCollection operation
            */
           public void receiveResultdeleteCollection(
                    org.subscription.news.NewsSubsStub.DeleteCollectionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteCollection operation
           */
            public void receiveErrordeleteCollection(java.lang.Exception e) {
            }
                


    }
    