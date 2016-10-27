var express = require('express');
var router = express.Router();
var url = require('url');

/* GET users listing. */
router.get('/', function(req, res, next) {

	//post url 받은거 parsing
	var uri = url.parse(req.url, true);

	//path를 받아서 download
	res.download(uri.query.path);
	
});

module.exports = router;
