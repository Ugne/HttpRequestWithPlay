$('document').ready(function(){
	$('#app-get-changes').on('click', function(e){
		e.preventDefault();
		$(this).blur();
		$('.alert-danger').hide();
		$('.label-danger').html("");
		if($('.app-date').val().length == 0){
			$('.app-date').parent().prev('.label-danger').html('Select a date');
		}else{
			$('#changesForm').submit();
		}
		
	});
	
	$('#app-get-chart').on('click', function(e){
		e.preventDefault();
		$(this).blur();
		$('.alert-danger').hide();
		$('.label-danger').html("");
		var error = false;
		if($('.selectpicker').val().length == 0){
			$('.bootstrap-select').parent().prev('.label-danger').html('Select a currency');
			error = true;
		}
		if($('.app-date-from').val().length == 0){
			$('.app-date-from').parent().prev('.label-danger').html('Select a date');
			error = true;
		}
		if($('.app-date-to').val().length == 0){
			$('.app-date-to').parent().prev('.label-danger').html('Select a date');
			error = true;
		}
			
		if(!error && Date.parse($('.app-date-from').val()) > Date.parse($('.app-date-to').val())){
			$('.app-date-from').parent().prev('.label-danger').html('Date from has to be before date to');
			$('.app-date-to').parent().prev('.label-danger').html('Date to has to be after date from');
			error = true;
		}
		
		if(!error){
			$('#chartForm').submit();
		}
	});
});
