
$(document).ready(bodyLoad);

//var postServerUrl = "http://soa2.cs.bgu.ac.il:17172/";	TODO
//var getServerUrl = "http://soa3.cs.bgu.ac.il:17171/";		TODO

var postServerUrl = "http://127.0.0.1:17171/";
var getServerUrl = "http://127.0.0.1:17172/aggr";

var username;

function doPost(content, callback){

	alert(content);

	//var content = {"title": "title1", "author": "author1", "tags":{"tag1": "tag11", "tag2": "tag21", "tag3": "tag31", "tag4": "tag41", "tag5": "tag51"}, "content": "content1" };
	var params = {data: JSON.stringify([content])};
	
	$.post(postServerUrl, params, callback).error(function() { alert("doPost Failed"); });

}

function doGet(params, callback) {
//json
		///*
		$.ajax({
				type: "GET",
				url: getServerUrl,
				data: params,
				cache: false,
				dataType: "json",
				success: callback
		});
		//*/
	//$.get(getServerUrl, params, callback);
}

function doPostTestUpdate(params){
		
}



function postsTest(){		
		content = {"title": "t2", "author": "a2", "content": "c2" };
		doPost(content, doPostTestUpdate);
}

function doGetTestUpdate(params){
		alert("received answer");
		alert(params);
		alert(params.getElementsByTagName('title'));
		//alert(params.resopnseText);
		//$('#myDiv').html("-" + xml + "-");
		//data = JSON.stringify([params]);

		//alert("before");
		//xmlDocu = jQuery.parseXML(xml);
		//alert("after");
		
    //$xml = $( xmlDocu );
    //$title = $xml.find( "title" );

		/* append "RSS Title" to #someElement */
		//$( "#myDiv" ).append( $title.text() );



		//[Post1 as JSON, POST2 as JSON]
		posts = eval('(' + params.resopnseText + ')');
	
		alert("the posts after eval");
		alert(posts);
		
	for (k in posts) {
	
		post = posts[k];
		alert (k);
		alert(post);
	}
}

function doGetTest(){
		getParams = "author=a2";
		alert("doGetTest");
		doGet(getParams, doGetTestUpdate);
		//alert("doGetTest done");
}



function bodyLoad(){

	username = getCookie("username");
	
	initBinds();
	
	if (username && username != ""){
		
		initPageContentWhenLogedin();
	}
	else initPageContentWhenLogedout();
}

function getCookie(c_name) {

	var i,x,y,ARRcookies=document.cookie.split(";");
	
	for (i=0;i<ARRcookies.length;i++){
		
		x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
		y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
		x = x.replace(/^\s+|\s+$/g,"");
	  
		if (x==c_name)
			return unescape(y);
	}
}

function setCookie(c_name,value,exdays){

	var exdate=new Date();
	
	exdate.setDate(exdate.getDate() + exdays);
	
	var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
	
	document.cookie=c_name + "=" + c_value;
}

function login(){

	username = document.getElementById("usernameInput").value;

	if (username){
		setCookie("username", username, 10);
		bodyLoad();
	}

	else alert("enter username");
}

function logout(){

	if (username){
		setCookie("username", "", 10);
		bodyLoad();
	}
}

function initPageContentWhenLogedin(){

	var leftDiv = document.getElementById("leftDiv");
	
	// remove login button element..
	while ( leftDiv.childNodes.length > 0 )
		leftDiv.removeChild( leftDiv.firstChild );

	emptyTheRightPane();
		
	// add compose, read and logout elements..
	var element = document.getElementById("newPostButtonDiv").cloneNode(true);
	leftDiv.appendChild(element);
	element.style.visibility = "visible";
	element = document.getElementById("readPostsOptionsDiv").cloneNode(true);
	leftDiv.appendChild(element);
	element.style.visibility = "visible";
	element = document.getElementById("logoutDiv").cloneNode(true);
	leftDiv.appendChild(element);
	element.style.visibility = "visible";
	
	getPostsOfSpecificAuthor(username);
}

function initPageContentWhenLogedout(){

	var leftDiv = document.getElementById("leftDiv");
	
	// remove compose, read and logout elements..
	while ( leftDiv.childNodes.length > 0 )
		leftDiv.removeChild( leftDiv.firstChild );

	emptyTheRightPane();
		
	// add login button element..
	var element = document.getElementById("loginDiv").cloneNode(true);
	leftDiv.appendChild(element);
	element.style.visibility = "visible";
}

function showComposeElement(){

	emptyTheRightPane();
	
	var rightDiv = document.getElementById("rightDiv");

	var element = document.getElementById("composePostDiv").cloneNode(true);
	rightDiv.appendChild(element);
	element.style.visibility = "visible";
	
	element = document.getElementById("sendNewPostButtonDiv").cloneNode(true);
	rightDiv.appendChild(element);
	element.style.visibility = "visible";
}

function emptyTheRightPane(){

	var rightDiv = document.getElementById("rightDiv");
	
	// remove everything from the right pane..
	while ( rightDiv.childNodes.length > 0 )
		rightDiv.removeChild( rightDiv.firstChild );
}

function send(){
	
	var rightDiv = document.getElementById("rightDiv");
	
	var title = $("#postTitleInput").val();
	var tags = $("#postTagsInput").val();
	var content = $("#postContentInput").val();
	
	var mySplitResult = tags.split(", ");
	
	var jTags = "{";
	
	for(i = 0; i < mySplitResult.length; i++)
		jTags += "tag:" + mySplitResult[i] + ","; 

	jTags = jTags.substring(0, jTags.length-1);
	
	jTags += "}";

	var post = "{\"title\":\"" + title + "\"," +
				"\"author\":\"" + username + "\"," + 
				"\"tags:\"" + jTags + "\"," + 
				"\"content\":\"" + content + "\"}";
	
	doPost(post, handleGetPostsReply);
}

function initBinds(){
	
	$(document).keypress(function (e) {
		if (e.keyCode === 13){
		
			if (authorFocus == 1) searchByAuthor();
			else if (startDateFocus == 1) document.getElementById('endDateInput').focus();
			else if (endDateFocus == 1) searchByDates;
			else if (tagsFocus == 1) s;
		}
	 });
}

function searchByAuthor(){

	var author = document.getElementById("authorInput").textInput();
	getPostsOfSpecificAuthor(author);
}

function getPostsOfSpecificAuthor(author){

	doGet({author: author}, handleGetPostsReply);
}

function searchByDates(){

	var start = document.getElementById("startDateInput").textInput();
	var start = document.getElementById("endDateInput").textInput();
	
	getPostsBetweenDates(start, end);
}

function getPostsBetweenDates(start, end){

	doGet({startDate: start, endDate: end}, handleGetPostsReply);
}

function searchByTags(){

	var tags = document.getElementById("tagsInputTagList").textInput();
	
	var mySplitResult = tags.split(", ");
	
	getPostsOfTheseTags(mySplitResult);
}

function getPostsOfTheseTags(tags){

	var jTags = "{";
	
	for(i = 0; i < tags.length; i++){

		jTags += "tag:" + tags[i] + ","; 
	}
	
	jTags.substring(0, jTags.length-2);
	
	jTags += "}"
	
	doGet(jTags, handleGetPostsReply);
}

function handleGetPostsReply(data){

	emptyTheRightPane();
	updatePageWithPosts(data);
}

function updatePageWithPosts(data){

	data = JSON.stringify([data]);

	//[Post1 as JSON, POST2 as JSON]
	var posts = eval('(' + data + ')');
	
	var rightDiv = document.getElementById("rightDiv");
	
	for (k in posts) {
	
		var post = posts[k];
		
		var postDiv = document.getElementById("postDiv").cloneNode(true);
		
		// title
		postDiv.getElementsByTagName("label")[0].innerHTML =
			post.title + " (" + post.author + " " + post.date + ")";
		
		// content
		postDiv.getElementsByTagName("label")[1].innerHTML = post.content;
		
		// tags
		var tags = "";

		for (t in post.tags)
			 tags += post.tags[t] + " ";
			 
		postDiv.getElementsByTagName("label")[2].innerHTML = tags;
		
		postDiv.style.visibility = "visible";
		
		rightDiv.appendChild(postDiv)
	}
}
