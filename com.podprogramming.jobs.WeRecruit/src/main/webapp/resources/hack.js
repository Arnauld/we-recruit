var selectSkills = function() {
	$("#sk").children().each(function(index,elem){
		var $elem = $(elem);
		if($elem.attr("id")!="sk_soc") {
			setTimeout(function() {
				$(elem).addClass("apply-icon");
			}, (index+1)*750);
		}
	});
};
$(function() {
	$("#search-found").click(selectSkills);
});