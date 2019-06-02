var express = require('express');
var app = express();
var cors = require('cors');
var bodyParser = require('body-parser');
var js2xmlparser = require("js2xmlparser");
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors());
app.get('/api/produtos/xml', function (req, res) {
setTimeout(function(){
res.set('Content-Type', 'text/xml');
res.send(js2xmlparser.parse("produtos",
{"produto":[{
id: 1,
nome: 'Chocolate',
categoria: 'doce',
descricao: 'Chocolate ao leite'
},
{
id: 2,
nome: 'Coxinha',
categoria: 'salgado',
descricao: 'Massa de mandioca e recheio de frango desfiado'
},
{
id: 3,
nome: 'Suco',
categoria: 'bebida',
descricao: 'Suco de laranja natural'
}]}
));
res.status(200).end();
}, 4000);
});
app.get('/api/produtos/json', function (req, res) {
setTimeout(function(){
res.header('Access-Control-Allow-Origin', '*')
.send(200,
[{
id: 1,
nome: 'Chocolate',
categoria: 'doce',
descricao: 'Chocolate ao leite'
},
{
id: 2,
nome: 'Coxinha',
categoria: 'salgado',
descricao: 'Massa de mandioca e rechei de franco desfiado'
},
{
id: 3,
nome: 'Suco',
categoria: 'bebida',
descricao: 'Suco de laranja natural'
}]
)
}, 4000);
});
app.listen(3000);
console.log('A API está no ar');