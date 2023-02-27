const UserService = require('../services/user.services')


exports.register = async(req,res,next)=>{
    try {
        const {name, email, password,role = "parent"} = req.body;
        console.log('... req.body', req.body);

        const successRes = await UserService.registerUser(name, email, password, role);

        res.json({status:true,success:"User Registered Successfully"});
    } catch (error) {
        throw error
    }
}

exports.login = async(req,res,next)=>{
    try {
        const {email, password} = req.body;

        const user = await UserService.checkUser(email);

        if(!user){
            throw new Error('User does not exists!');
        }
        const isMatch = await user.comparePassword(password);
        
        if(isMatch ===false){
            throw new Error('Password Invalid');
        }

        let tokenData = {_id:user._id,email:user.email};


        const token = await UserService.generateToken(tokenData,"secretKey",'1h')

        res.status(200).json({status:true,token:token})
        
    } catch (error) {
        throw error
    }
}
exports.addChild = async (req, res, next) => {
  try {
    const { name, email, pin } = req.body;

    const parent = await UserService.checkUser(req.user.email);

    if (!parent) {
      throw new Error('User does not exist!');
    }

    if (parent.role !== 'parent') {
      throw new Error('User is not a parent!');
    }

    const successRes = await UserService.registerUser(name, email, 'default', 'child', pin, parent._id);

    res.json({ status: true, success: "Child Registered Successfully", pin: pin });
  } catch (error) {
    throw error;
  }

};
exports.childLogin = async (req, res, next) => {
  try {
    const { email, pin } = req.body;

    const user = await UserService.checkUser(email);

    if (!user) {
      throw new Error('User does not exist!');
    }

    if (user.role !== 'child') {
      throw new Error('User is not a child!');
    }

    if (user.pin !== pin) {
      throw new Error('Invalid PIN!');
    }

    let tokenData = { _id: user._id, email: user.email };

    const token = await UserService.generateToken(tokenData, "secretKey", "1h");

    res.status(200).json({ status: true, token: token });
  } catch (error) {
    throw error;
  }
};

    
