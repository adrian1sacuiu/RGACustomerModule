function getAllCustomers(){
	xhr = $.ajax({
		type : 'GET',
		url : 'customer/getAllCustomers',
		data : '',
		success : function(data) {
			var a = [];
			$('#customerInfo').html('<table class="table table-hover table-striped" id="customers_data"><thead><tr><th>Id Customer</th><th>Email</th><th>First Name</th><th>Last Name</th><th>Address</th><th>Phone</th></tr></thead><tbody></tbody></table>');
			var i = 0;
			for(i=0;i<data.message.length;i++){
				a.push([data.message[i].idCustomer,data.message[i].email,data.message[i].firstName,data.message[i].lastName,data.message[i].address,data.message[i].phone]);
			}
			$('#customers_data').dataTable( {
				      "data": a,
				      "columns": [
				            { "title": "IdCustomer"},        
				            { "title": "Email" },
				            { "title": "FirstName" },
				            { "title": "LastName" },
				            { "title": "Address" },
				            { "title": "Phone" }
						         
				        ],"order": []
			} );
			$('#customers_data thead th').eq(0).hide();
			$('#customers_data tbody tr').each(function(){
				$("td",this).eq(0).hide();
			});
					
			$('#customers_data tbody').on('click', 'tr', function () {
				var id = $("td",this).eq(0).text();
				getCustomer(id);
					   	
			} );
		},
        error: function (error) {
        	$('#customerInfo').html('<h1 class="alert alert-danger">' + error.responseJSON.message + '</h1>');
        }
		
	});
}
function getCustomer(idCustomer){
	
	xhr = $.ajax({
		type : 'GET',
		url : 'customer/getCustomerById/'+idCustomer,
		data : '',
		success : function(data) {
			$('.modal-content').removeClass('alert alert-info alert-danger');
			
			$('.modal-title').html('View Customer');
				
			$('.modal-body').html('<table id="customerInfo" class="table col-lg-offset-1">');
				
			$('#customerInfo').html('<tr><td>Email:</td><td>' + data.message.email + '</td></tr>');
			$('#customerInfo').append('<tr><td>First Name:</td><td>' + data.message.firstName + '</td></tr>');
			$('#customerInfo').append('<tr><td>Last Name:</td><td>' + data.message.lastName + '</td></tr>');
			$('#customerInfo').append('<tr><td>Address:</td><td>' + data.message.address + '</td></tr>');
			$('#customerInfo').append('<tr><td>Phone:</td><td>' + data.message.phone + '</td></tr></table>');
			
			$('.modal-footer').html('<a href="customer/updateCustomer/' + data.message.idCustomer + 
						'"><button type="submit" class="btn btn-primary pull-left">Modifiy</button></a>');
			$('.modal-footer').append('<button id="deleteButton" type="button" onclick="deleteCustomer(' + data.message.idCustomer + 
						')" class="btn btn-primary col-sm-offset-1 pull-left">Delete</button>');
			$('.modal-footer').append('<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>');
		
			$('#modalButton').trigger('click');
		},
        error: function (error) {
        	$('.modal-content').removeClass('alert alert-info alert-danger');
        	
			$('.modal-content').addClass('alert alert-danger');
			$('.modal-title').html(error.responseJSON.status);
			$('.modal-body').html(error.responseJSON.message);
			setTimeout(function() {$('#myModal').modal('hide');}, 2000);
			$('#myModal').on('hide.bs.modal', function (e) {
        		  window.location="/RGACustomerModule";
        	});
			
			$('#modalButton').trigger('click');
        }
	});
}

function deleteCustomer(idCustomer){
	$.ajax({
		type : 'DELETE',
		url : 'customer/deleteCustomer/'+idCustomer,
		data : '',
		success : function(data) {
			$('.modal-title').html(data.status);
        	$('.modal-body').html(data.message);
        	$('.modal-footer').html('');
        	
			$('.modal-content').removeClass('alert alert-danger');
        	$('.modal-content').addClass('alert alert-info');
        		
        	setTimeout(function() {$('#myModal').modal('hide');}, 2000);
	        $('#myModal').on('hide.bs.modal', function (e) {
	        	  window.location="/RGACustomerModule";
	        });
        		
			
		},
        error: function (error) {
        	$('.modal-title').html(error.responseJSON.status);
        	$('.modal-body').html(error.responseJSON.message);
        	$('.modal-footer').html('');
        	
        	$('.modal-content').removeClass('alert alert-info');
        	$('.modal-content').addClass('alert alert-danger');
        	
        	setTimeout(function() {$('#myModal').modal('hide');}, 2000);
	        $('#myModal').on('hide.bs.modal', function (e) {
	        	  window.location="/RGACustomerModule";
	        });
        }
	});
}

function addCustomer(){
	$.ajax({
        url: '/RGACustomerModule/customer/addCustomer',
        type: 'POST',
        data: $('#customerForm').serialize(),
        success: function(data) {
        	$('.modal-title').html(data.status);
        	$('.modal-body').html(data.message);
        	
        	$('.modal-content').removeClass('alert alert-danger');
        	$('.modal-content').addClass('alert alert-info');
        		
	        setTimeout(function() {$('#myModal').modal('hide');}, 2000);
	        $('#myModal').on('hide.bs.modal', function (e) {
	        	  window.location="/RGACustomerModule";
	        });
	        	
        	
        	$('#modalButton').trigger('click');
        },
        error: function (error) {
        	$('.modal-title').html(error.responseJSON.status);
        	$('.modal-body').html(error.responseJSON.message);
        	
        	$('.modal-content').removeClass('alert alert-info');
        	$('.modal-content').addClass('alert alert-danger');
        	
        	$('#modalButton').trigger('click');
        }
    });
}

function updateCustomer(){
	$.ajax({
        url: '/RGACustomerModule/customer/updateCustomer',
        type: 'POST',
        data: $('#customerForm').serialize(),
        success: function(data) {
        	$('.modal-title').html(data.status);
        	$('.modal-body').html(data.message);
        	
        	$('.modal-content').removeClass('alert alert-danger');
        	$('.modal-content').addClass('alert alert-info');
        	
        	setTimeout(function() {$('#myModal').modal('hide');}, 2000);
	        $('#myModal').on('hide.bs.modal', function (e) {
	        	  window.location="/RGACustomerModule";
	        });
	        $('#modalButton').trigger('click');
        },
        error: function (error) {
        	$('.modal-title').html(error.responseJSON.status);
        	$('.modal-body').html(error.responseJSON.message);
        	
        	$('.modal-content').removeClass('alert alert-info');
        	$('.modal-content').addClass('alert alert-danger');
        	
        	$('#modalButton').trigger('click');
        }
    });
}

function registerUser(){
	$.ajax({
        url: '/RGACustomerModule/user/registerUser',
        type: 'POST',
        data: $('#register_form').serialize(),
        success: function(data) {
        	$.ajax({
        	      url: '/RGACustomerModule/j_spring_security_check',
        	      type: 'POST',
        	      data: {j_username:data.message.username, j_password:data.message.password},
        	      success: function(data) {
						location.href = "/RGACustomerModule";
        	      }
        	});
        },
        error: function (error) {
        	$('#register_error').html(error.responseJSON.message);
    		$('#register_error').show();
        }
    });
}