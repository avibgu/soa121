/**
 * NewsAggrSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.2  Built on : Sep 06, 2010 (09:42:01 CEST)
 */
package org.aggregate.news;


/**
 * NewsAggrSkeleton java skeleton for the axisService
 */
public class NewsAggrSkeleton implements NewsAggrSkeletonInterface {

	/**
	 * Auto generated method signature
	 *
	 * @param getNewsReq0
	 */

	public org.aggregate.news.Channel getNews(
			org.aggregate.news.GetNewsReq getNewsReq0) {
		return new NewsAggrSkeletonDelegate().getNews(getNewsReq0);
	}

}
