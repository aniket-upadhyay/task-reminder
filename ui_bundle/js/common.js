var userEmail;

function firePostApi(url, payload, cb){
	
	$.ajax({
		  url: url,
		  type: 'POST',
		  data: JSON.stringify(payload),
		  dataType: "json",
	      contentType:"application/json",
		  error: function(error) {
			  console.log('error : ',error);
		  },
		  success: function(data) {
			  console.log(data);
			  if(cb){
				  cb(JSON.parse(data));
			  }
		  }
	});
	
}

function populateValues(container, response){
	$(container).find('input').each(function(index, input){
		var id = input.id;
		$("#"+id).val(response[id]);
	});
}