const UserModel = require('../model/user.model');
const jwt = require('jsonwebtoken');

class UserService{
    static async registerUser(name,email,password,role, parentId=null){
        try {
            const createUser = new UserModel({name,email,password,role, parentId});
            return await createUser.save();
        } catch (err) {
            throw err;            
        }
    }
    static async checkUser(email){
        try {
            return await UserModel.findOne({email});
        } catch (error) {
            throw error;
        }
    }
    static async checkUserById(userId) {
        try {
          return await UserModel.findById(userId);
        } catch (error) {
          throw error;
        }
      }
    
    static async generateToken(tokenData, secretKey, jwt_expire){
        return jwt.sign(tokenData, secretKey,{expiresIn:jwt_expire});
    }
    static async addChild(name, email, pin ) {
        try {
          const parent = await this.checkUser(email);
    
          if (!parent) {
            throw new Error('User does not exists!');
          }
    
          if (parent.role !== 'parent') {
            throw new Error('User is not a parent!');
          }
    
          
    
          const successRes = await this.registerUser(name, email, 'child',pin,  parent._id);
    
          return successRes;
        } catch (error) {
          throw error;
        }
      }
    
    }

module.exports = UserService;