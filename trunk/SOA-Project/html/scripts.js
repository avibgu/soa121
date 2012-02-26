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
