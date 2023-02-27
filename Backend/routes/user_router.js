const router = require('express').Router();
const UserController = require("../controller/user.controller");

const authMiddleware = require('../middleware/authMiddleware');
router.post('/registration',UserController.register);
router.post('/login',UserController.login);
router.post('/addChild', authMiddleware,UserController.addChild);
router.post('/childLogin', authMiddleware,UserController.childLogin);


module.exports = router;