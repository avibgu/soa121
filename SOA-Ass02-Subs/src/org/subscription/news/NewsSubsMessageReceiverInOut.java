
/**
 * NewsSubsMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
        package org.subscription.news;

        /**
        *  NewsSubsMessageReceiverInOut message receiver
        */

        public class NewsSubsMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        NewsSubsSkeletonInterface skel = (NewsSubsSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("deleteElement".equals(methodName)){
                
                org.subscription.news.DeleteElementResponse deleteElementResponse11 = null;
	                        org.subscription.news.DeleteElementRequest wrappedParam =
                                                             (org.subscription.news.DeleteElementRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.subscription.news.DeleteElementRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteElementResponse11 =
                                                   
                                                   
                                                         skel.deleteElement(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteElementResponse11, false, new javax.xml.namespace.QName("http://news.subscription.org/",
                                                    "deleteElement"));
                                    } else 

            if("postCollection".equals(methodName)){
                
                org.subscription.news.PostCollectionResponse postCollectionResponse13 = null;
	                        org.subscription.news.PostCollectionRequest wrappedParam =
                                                             (org.subscription.news.PostCollectionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.subscription.news.PostCollectionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               postCollectionResponse13 =
                                                   
                                                   
                                                         skel.postCollection(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), postCollectionResponse13, false, new javax.xml.namespace.QName("http://news.subscription.org/",
                                                    "postCollection"));
                                    } else 

            if("putElement".equals(methodName)){
                
                org.subscription.news.PutElementResponse putElementResponse15 = null;
	                        org.subscription.news.PutElementRequest wrappedParam =
                                                             (org.subscription.news.PutElementRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.subscription.news.PutElementRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               putElementResponse15 =
                                                   
                                                   
                                                         skel.putElement(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), putElementResponse15, false, new javax.xml.namespace.QName("http://news.subscription.org/",
                                                    "putElement"));
                                    } else 

            if("getURLs".equals(methodName)){
                
                org.subscription.news.URLsList uRLsList17 = null;
	                        org.subscription.news.GetURLsRequest wrappedParam =
                                                             (org.subscription.news.GetURLsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.subscription.news.GetURLsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               uRLsList17 =
                                                   
                                                   
                                                         skel.getURLs(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), uRLsList17, false, new javax.xml.namespace.QName("http://news.subscription.org/",
                                                    "getURLs"));
                                    } else 

            if("deleteCollection".equals(methodName)){
                
                org.subscription.news.DeleteCollectionResponse deleteCollectionResponse19 = null;
	                        org.subscription.news.DeleteCollectionRequest wrappedParam =
                                                             (org.subscription.news.DeleteCollectionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.subscription.news.DeleteCollectionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteCollectionResponse19 =
                                                   
                                                   
                                                         skel.deleteCollection(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteCollectionResponse19, false, new javax.xml.namespace.QName("http://news.subscription.org/",
                                                    "deleteCollection"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(org.subscription.news.DeleteElementRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.subscription.news.DeleteElementRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.subscription.news.DeleteElementResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.subscription.news.DeleteElementResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.subscription.news.PostCollectionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.subscription.news.PostCollectionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.subscription.news.PostCollectionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.subscription.news.PostCollectionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.subscription.news.PutElementRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.subscription.news.PutElementRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.subscription.news.PutElementResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.subscription.news.PutElementResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.subscription.news.GetURLsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.subscription.news.GetURLsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.subscription.news.URLsList param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.subscription.news.URLsList.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.subscription.news.DeleteCollectionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.subscription.news.DeleteCollectionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.subscription.news.DeleteCollectionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.subscription.news.DeleteCollectionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.subscription.news.DeleteElementResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.subscription.news.DeleteElementResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.subscription.news.DeleteElementResponse wrapdeleteElement(){
                                org.subscription.news.DeleteElementResponse wrappedElement = new org.subscription.news.DeleteElementResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.subscription.news.PostCollectionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.subscription.news.PostCollectionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.subscription.news.PostCollectionResponse wrappostCollection(){
                                org.subscription.news.PostCollectionResponse wrappedElement = new org.subscription.news.PostCollectionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.subscription.news.PutElementResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.subscription.news.PutElementResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.subscription.news.PutElementResponse wrapputElement(){
                                org.subscription.news.PutElementResponse wrappedElement = new org.subscription.news.PutElementResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.subscription.news.URLsList param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.subscription.news.URLsList.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.subscription.news.URLsList wrapgetURLs(){
                                org.subscription.news.URLsList wrappedElement = new org.subscription.news.URLsList();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.subscription.news.DeleteCollectionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.subscription.news.DeleteCollectionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.subscription.news.DeleteCollectionResponse wrapdeleteCollection(){
                                org.subscription.news.DeleteCollectionResponse wrappedElement = new org.subscription.news.DeleteCollectionResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (org.subscription.news.DeleteElementRequest.class.equals(type)){
                
                           return org.subscription.news.DeleteElementRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.subscription.news.DeleteElementResponse.class.equals(type)){
                
                           return org.subscription.news.DeleteElementResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.subscription.news.PostCollectionRequest.class.equals(type)){
                
                           return org.subscription.news.PostCollectionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.subscription.news.PostCollectionResponse.class.equals(type)){
                
                           return org.subscription.news.PostCollectionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.subscription.news.PutElementRequest.class.equals(type)){
                
                           return org.subscription.news.PutElementRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.subscription.news.PutElementResponse.class.equals(type)){
                
                           return org.subscription.news.PutElementResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.subscription.news.GetURLsRequest.class.equals(type)){
                
                           return org.subscription.news.GetURLsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.subscription.news.URLsList.class.equals(type)){
                
                           return org.subscription.news.URLsList.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.subscription.news.DeleteCollectionRequest.class.equals(type)){
                
                           return org.subscription.news.DeleteCollectionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.subscription.news.DeleteCollectionResponse.class.equals(type)){
                
                           return org.subscription.news.DeleteCollectionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    