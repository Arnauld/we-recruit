var selectSkills = function() {
	$("#sk").children().each(function(index,elem){
		var $elem = $(elem);
		setTimeout(function() {
			$(elem).addClass("apply-icon");
		}, (index+1)*500);
	});
};
$(function() {
	$("#search-found").click(selectSkills);
});