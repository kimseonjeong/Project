var express = require('express');
var router = express.Router();
var client = require('mongodb').MongoClient;
var fs = require('fs');

/* POST */
router.post('/',function(req,res,next){

	//outputarray 
	var outputarray = new Array();

	//query
	var query = {};

	//item array
	var items_array = new Array();

	//file name
	var name = '';
	
	//metadata database connect
	client.connect('mongodb://203.153.146.39/metadata', function(err,db){
		
		//kflow collection connect
		db.collection("kflow").find().toArray(function(err, result){

			//simulator name assign
			name = result[0].simulator.name;

			//outputarray 
			for(var i in result[0].output){

				outputarray[i] = JSON.stringify(result[0].output[i].name);
				outputarray[i] = outputarray[i].substring(1,outputarray[i].length-1);
				//1개를 뺌

			}			

		})
		
	})

	//simulation database connect
	client.connect('mongodb://203.153.146.39/simulation', function(err,db){

		//변수를 하나씩 쿼리문 dynamic
		//if thickness
		if (req.body.thickness != "")
		{

			//thickness query
			query['input.thickness']=req.body.thickness;

		}

		//if Umach
		if (req.body.Umach != "")
		{

			//Umach query
			query['input.Umach']=req.body.Umach;

		}

		//if AOA
		if (req.body.AOA != "")
		{

			//AOA query
			query['input.AOA']=req.body.AOA;

		}

		//if RE
		if (req.body.RE != "")
		{

			//RE query
			query['input.RE']=req.body.RE;

		}

		//if IVISC
		if (req.body.IVISC != "")
		{

			//IVISC query
			query['input.IVISC']=req.body.IVISC;

		}

		//if rho_inf
		if (req.body.rho_inf != "")
		{

			//rho_inf query
			query['input.rho_inf']=req.body.rho_inf;

		}

		//if t_inf
		if (req.body.t_inf != "")
		{

			//t_inf query
			query['input.t_inf']=req.body.t_inf;

		}

		//if p_inf
		if (req.body.p_inf != "")
		{

			//p_inf query
			query['input.p_inf']=req.body.p_inf;

		}

		//if t_wall
		if (req.body.t_wall != "")
		{

			//t_wall query
			query['input.t_wall']=req.body.t_wall;

		}

		//if intensity
		if (req.body.intensity != "")
		{

			//intensity query
			query['input.intensity']=req.body.intensity;

		}

		//if f_func
		if (req.body.f_func != "")
		{

			//f_func query
			query['input.f_func']=req.body.f_func;

		}

		//if f_order
		if (req.body.f_order != "")
		{

			//f_order query
			query['input.f_order']=req.body.f_order;

		}

		//if limiter
		if (req.body.limiter != "")
		{

			//limiter query
			query['input.limiter']=req.body.limiter;

		}

		//kflow collection connect
		var cursor = db.collection("kflow").find(query);

		//find query
		cursor.nextObject(function(err, items){

			//item o
			if(items!=null){

				//item array push
				items_array.push(items.output.aerodynamic.Cl, items.output.aerodynamic.Cdt, items.output.aerodynamic.Cdp, 
					items.output.aerodynamic.Cdf, items.output.aerodynamic.Cm);

				//item array push
				items_array.push(items.output.field.flo001, items.output.field.flo002, items.output.field.flo003, 
					items.output.field.flo004, items.output.field.flo005,
				items.output.field.flo006, items.output.field.flo007, items.output.field.flo008, items.output.field.flo009, 
				items.output.field.flo010,
				items.output.field.flo011, items.output.field.flo012, items.output.field.flo013, items.output.field.flo014, 
				items.output.field.flo015, 
				items.output.field.flo016);

				//item array push
				items_array.push(items.output.surface.sur002, items.output.surface.sur003, items.output.surface.sur004, 
					items.output.surface.sur005,
				items.output.surface.sur006, items.output.surface.sur007);

				//outputarray, name, item array render
				res.render('kflow_output',{outputarray:outputarray, name:name, items_array:items_array});

			}
			//item x
			else{

				//csv파일 만들기
				//total string 상단
				var total = '';
				total += "thickness,Umach,AOA,RE\n";

				//thickness
				total += req.body.thickness +",";

				//Umach
				total += req.body.Umach +",";

				//AOA
				total += req.body.AOA +",";

				//RE
				total += req.body.RE;
				
				//쭐빠굼
				total += "\n";
				
				//file wrie
				fs.writeFileSync('./test_data.csv', total, 'utf-8');

				//name render
				res.render('kflow_predict',{name:name});
			}
		})
		
	})

	

});


module.exports = router;
