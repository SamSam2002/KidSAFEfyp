const express= require('express')
const app = express()
const mongoClient = require('mongodb').MongoClient
const url = "mongodb://localhost:27017"
    app.use(express.json())
    mongoClient.connect(url,(err,db)=>{
      if(err){
        console.log("Error while connecting mongo client")
      }else{
        const myDb= db.db('myDb')
        const collection= myDb.collection('Login')
        app.post('/signup', (req, res)=>{
            const newUser= {
                email:req.body.email,
                password: req.body.password
            }
            const query= {email : newUser.email}
            collection.findOne(query, (err,result)=>{
                if(result==null){
                    collection.insertOne(newUser,(err, result)=>{
                        res.status(200).send()

                    })
                }else{
                    resizeTo.status(400).send
                }
            })

        })
        app.post('/login',(req,res)=>{
            const query = {
                email:req.body.email,
                password: req.body.password
            }
            collection.findOne(query,(err, result)=>{
                if (result != null){
                    const objToSend={
                        email: result.email,

                        
                    }
                    res.status(200).send(JSON.Stringify(objToSend))

                }else{
                    res.status(404)
                }
            })

        })
      }
    })
    app.listen(3000, '0.0.0.0', ()=>{
        console.log("Listening on port 3000")

    })