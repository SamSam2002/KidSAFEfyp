const app = require('./app');
const db = require('./config/db');
const UserModel = require('./model/user.model')
const port = 5000;
app.get('/',(req,res)=>{
    res.send("hello world...")
});

app.listen(port,()=>{
    console.log(`Server Listening on Port http://localhost:${port}`);
}); 