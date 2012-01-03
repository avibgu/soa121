
/**
 * NewsSubsSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.2  Built on : Sep 06, 2010 (09:42:01 CEST)
 */
    package org.subscription.news;
    /**
     *  NewsSubsSkeletonInterface java skeleton interface for the axisService
     */
    public interface NewsSubsSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteElementRequest
         */

        
                public org.subscription.news.DeleteElementResponse deleteElement
                (
                  org.subscription.news.DeleteElementRequest deleteElementRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param postCollectionRequest
         */

        
                public org.subscription.news.PostCollectionResponse postCollection
                (
                  org.subscription.news.PostCollectionRequest postCollectionRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param putElementRequest
         */

        
                public org.subscription.news.PutElementResponse putElement
                (
                  org.subscription.news.PutElementRequest putElementRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getURLsRequest
         */

        
                public org.subscription.news.URLsList getURLs
                (
                  org.subscription.news.GetURLsRequest getURLsRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteCollectionRequest
         */

        
                public org.subscription.news.DeleteCollectionResponse deleteCollection
                (
                  org.subscription.news.DeleteCollectionRequest deleteCollectionRequest
                 )
            ;
        
         }
    