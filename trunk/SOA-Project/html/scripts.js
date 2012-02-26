
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

	$.post(postServerUrl, params, callback).error("doPost Failed");

}

function doGet(params, callback) {

	$.ajax("GET", getServerUrl + "?" + params, callback).error("doGet Failed");
}


// AVI

//var postServerUrl = "http://soa2.cs.bgu.ac.il:17172/";	TODO
//var getServerUrl = "http://soa3.cs.bgu.ac.il:17171/";		TODO

var postServerUrl = "http://127.0.0.1:17171/";
var getServerUrl = "http://127.0.0.1:17172/aggr";

var username;

function bodyLoad(){

	username = getCookie("username");
	
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
	var rightDiv = document.getElementById("rightDiv");
	
	// remove login button element..
	while ( leftDiv.childNodes.length > 0 )
		leftDiv.removeChild( leftDiv.firstChild );

	// remove everything from the right pane..
	while ( rightDiv.childNodes.length > 0 )
		rightDiv.removeChild( rightDiv.firstChild );
		
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
	var rightDiv = document.getElementById("rightDiv");
	
	// remove compose, read and logout elements..
	while ( leftDiv.childNodes.length > 0 )
		leftDiv.removeChild( leftDiv.firstChild );

	// remove everything from the right pane..
	while ( rightDiv.childNodes.length > 0 )
		rightDiv.removeChild( rightDiv.firstChild );
		
	// add login button element..
	var element = document.getElementById("loginDiv").cloneNode(true);
	leftDiv.appendChild(element);
	element.style.visibility = "visible";
}

// TODO..

function getPostsOfSpecificAuthor(author){

	doGet("author=" + author, handleGetPostsReply);
}

function handleGetPostsReply(data){

	if (putReq.readyState == 4){
	
		if(putReq.status == 200) {
			updatePageWithPosts(data);
		}
		else{
			alert("result status != 200");
			alert(putReq.responseText);
		}
	}
}

function updatePageWithPosts(){

	alert("updatePageWithPosts");

	var jsonObjects = eval('(' + data + ')');
	var rightDiv = document.getElementById("rightDiv");
	
	for (k in jsonObjects) {
	
		jsonObjects[k];
	}
}

