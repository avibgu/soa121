/**
 * NewsSubsSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.2  Built on : Sep 06, 2010 (09:42:01 CEST)
 */
package org.subscription.news;

import server.SubsServer;

/**
 * NewsSubsSkeleton java skeleton for the axisService
 */
public class NewsSubsSkeleton implements NewsSubsSkeletonInterface {

	protected SubsServer _SubsServer;

	public NewsSubsSkeleton() {

		_SubsServer = new SubsServer();
	}

	/**
	 * Auto generated method signature
	 *
	 * @param deleteElementRequest0
	 */

	public org.subscription.news.DeleteElementResponse deleteElement(
			org.subscription.news.DeleteElementRequest deleteElementRequest) {
		return _SubsServer.deleteElement(deleteElementRequest);
	}

	/**
	 * Auto generated method signature
	 *
	 * @param postCollectionRequest
	 */

	public org.subscription.news.PostCollectionResponse postCollection(
			org.subscription.news.PostCollectionRequest postCollectionRequest) {
		return _SubsServer.postCollection(postCollectionRequest);
	}

	/**
	 * Auto generated method signature
	 *
	 * @param putElementRequest
	 */

	public org.subscription.news.PutElementResponse putElement(
			org.subscription.news.PutElementRequest putElementRequest) {
		return _SubsServer.putElement(putElementRequest);
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getURLsRequest
	 */

	public org.subscription.news.URLsList getURLs(
			org.subscription.news.GetURLsRequest getURLsRequest) {
		return _SubsServer.getURLs(getURLsRequest);
	}

	/**
	 * Auto generated method signature
	 *
	 * @param deleteCollectionRequest
	 */

	public org.subscription.news.DeleteCollectionResponse deleteCollection(
			org.subscription.news.DeleteCollectionRequest deleteCollectionRequest) {
		return _SubsServer.deleteCollection(deleteCollectionRequest);
	}

}
