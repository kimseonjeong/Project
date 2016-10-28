var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

//index
var routes = require('./routes/index');
var users = require('./routes/users');

//home
var home = require('./routes/home');

//kflow input
var kflow_input = require('./routes/kflow_input');

//kflow output
var kflow_output = require('./routes/kflow_output');

//kflow predict
var kflow_predict = require('./routes/kflow_predict');

//kflow file download
var download = require('./routes/download');

//simulation2 input
var simulation2_input = require('./routes/simulation2_input');

//simulation2 output
var simulation2_output = require('./routes/simulation2_output');

//simulation3 input
var simulation3_input = require('./routes/simulation3_input');

//simulation3 output
var simulation3_output = require('./routes/simulation3_output');


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

//router connect
app.use('/', routes);
app.use('/users', users);
//home ejs
app.use('/home', home);

//simulation2 input ejs
app.use('/simulation2_input', simulation2_input);

//simulation2 output ejs
app.use('/simulation2_output', simulation2_output);

//simulation3 input ejs
app.use('/simulation3_input', simulation3_input);

//simulation3 output ejs
app.use('/simulation3_output', simulation3_output);

//kflow input ejs
app.use('/kflow_input', kflow_input);

//kflow output ejs
app.use('/kflow_output', kflow_output);

//kflow file download ejs
app.use('/download', download);

//kflow predict ejs
app.use('/kflow_predict', kflow_predict);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});


module.exports = app;
