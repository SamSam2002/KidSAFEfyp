const mongoose = require('mongoose');
const db = require('../config/db');
const bcrypt = require("bcrypt");

const {Schema}= mongoose;

const userSchema = new Schema({
    
        name: {
          type: String,
         
        },
        email: {
          type: String,
         
        },
        password: {
          type: String,
          
        },
        role: {
          type: String,
          enum: ['parent', 'child'],
         
        },
        pin:{
            type: String,  
           
          
        },
    }
);

userSchema.pre('save',async function(){
    try {
        //hashing password
        var user = this;

        const salt = await(bcrypt.genSalt(10));
        const hashpass = await bcrypt.hash(user.password,salt);

        user.password = hashpass;
    } catch (error) {
        console.log(error)
        
    }
});
//hashing the password
userSchema.methods.comparePassword = async function(userPassword){
    try {
        const isMatch = await bcrypt.compare(userPassword, this.password);
        return isMatch;
    } catch (error) {
        throw error;
    }
};
const UserModel = db.model('user',userSchema);

module.exports = UserModel;