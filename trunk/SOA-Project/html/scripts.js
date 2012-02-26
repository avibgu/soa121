
// NIR

MAX_TAGS = 5;

// add another field tag to add Posts
function addTagFieldReadPosts( whereToAddId )
{	
	if ( typeof tagFieldNum == 'undefined' ) {
		tagFieldNum = 1;
    }
		
	if(tagFieldNum > MAX_TAGS - 1)
		return;

	tagFieldNum++;
			
	finalString = "";
	for(i =1; i<= tagFieldNum; i++){
		finalString = finalString + '<input type="search" name="autor" id="authorInputTagList" value="" placeholder="Tag"/>';
	}

	$(whereToAddId).html(finalString);
		
	if(tagFieldNum > MAX_TAGS - 1){
		jQuery("#addTagBtnForRead").attr('disabled', 'disabled');
	}
}

function doPost(content){

	//var content = {"title": "title1", "author": "author1", "tags":{"tag1": "tag11", "tag2": "tag21", "tag3": "tag31", "tag4": "tag41", "tag5": "tag51"}, "content": "content1" };
	var params = {data: JSON.stringify([content])};

	$.post(postServerUrl, params, callback).error(function() { alert("doPost Failed"); });

}

function doGet(params, callback) {

	$.ajax({
	  type: "GET",
	  url: getServerUrl,
	  data: (params),
	  cache: false,
	  dataType: "text",
	  success: callback
	});
}


// AVI

//var postServerUrl = "http://soa2.cs.bgu.ac.il:17172/";	TODO
//var getServerUrl = "http://soa3.cs.bgu.ac.il:17171/";		TODO

var postServerUrl = "http://127.0.0.1:17171/";
var getServerUrl = "http://127.0.0.1:17172/aggr";

var username;

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
		
	// add login button element..
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

// TODO..

function send(){

	alert("send");
	getPostsOfSpecificAuthor(username);
}

function initBinds(){

	//$(".authorInput").bind( "click.enter", searchByAuthor);
	//$('.authorInput').keypress(function(e) {
		//if(e.keyCode == 13) {
			//alert('You pressed enter!');
		//}
	//});
	
	$(document).keypress(function (e) {
		if (e.keyCode === 13){
		
			if (authorFocus == 1) alert('author');
			else if (startDateFocus == 1) alert('start');
			else if (endDateFocus == 1) alert('end');
			else if (tagsFocus == 1) alert('tags');
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

function getPostsBetweenDates(start, end){

	doGet({startDate: start, endDate: end}, handleGetPostsReply);
}

function getPostsOfTheseTags(tags){

	var jTags = "{";
	
	for (t in tags){
	
		jTags += "tag:" + tags[t] + ","; 
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
		
		alert(posts);
		
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

