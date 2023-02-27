const jwt = require('jsonwebtoken');

const authenticateUser = (req, res, next) => {
  try {
    const authHeader = req.headers.authorization;
    if (!authHeader) {
      throw new Error('Authorization header not found');
    }
    const token = authHeader.split(' ')[1];
    const decodedToken = jwt.verify(token, 'secretKey');
    req.user = { _id: decodedToken._id, email: decodedToken.email, role: decodedToken.role };
    next();
  } catch (err) {
    res.status(401).json({ message: 'Authentication failed', error: err.message });
  }
};

module.exports = authenticateUser;
