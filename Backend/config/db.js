const mongoose = require('mongoose');


const connection = mongoose.createConnection('mongodb://127.0.0.1:27017/Kidesafeapp').on('open',()=>{
    console.log("mongodb Connected");
}).on('error',()=>{
    console.log("Mongodb Connection error");
});



module.exports = connection;