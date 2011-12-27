/**
 * NewsSubsSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
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
	 * @return deleteElementResponse1
	 */

	public org.subscription.news.DeleteElementResponse deleteElement(
			org.subscription.news.DeleteElementRequest deleteElementRequest) {

		return _SubsServer.deleteElement(deleteElementRequest);
	}

	/**
	 * Auto generated method signature
	 *
	 * @param postCollectionRequest2
	 * @return postCollectionResponse3
	 */

	public org.subscription.news.PostCollectionResponse postCollection(
			org.subscription.news.PostCollectionRequest postCollectionRequest) {

		return _SubsServer.postCollection(postCollectionRequest);
	}

	/**
	 * Auto generated method signature
	 *
	 * @param putElementRequest4
	 * @return putElementResponse5
	 */

	public org.subscription.news.PutElementResponse putElement(
			org.subscription.news.PutElementRequest putElementRequest) {

		return _SubsServer.putElement(putElementRequest);
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getURLsRequest6
	 * @return uRLsList7
	 */

	public org.subscription.news.URLsList getURLs(
			org.subscription.news.GetURLsRequest getURLsRequest) {

		return _SubsServer.getURLs(getURLsRequest);
	}

	/**
	 * Auto generated method signature
	 *
	 * @param deleteCollectionRequest8
	 * @return deleteCollectionResponse9
	 */

	public org.subscription.news.DeleteCollectionResponse deleteCollection(
			org.subscription.news.DeleteCollectionRequest deleteCollectionRequest) {

		return _SubsServer.deleteCollection(deleteCollectionRequest);
	}

}
