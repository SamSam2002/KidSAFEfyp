const express = require('express');
//body parser used to read whatever data comes to the req body
const body_parser = require('body-parser');
const userRouter = require('./routes/user_router')
const app = express()
app.use(express.urlencoded({extended:true}))
app.use(express.json())


app.use(body_parser.json());

app.use('/',userRouter);
module.exports = app;