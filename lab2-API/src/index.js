const express   = require('express');
const morgan    = require('morgan');
const helmet    = require('helmet');
const cors      = require('cors'); 
const fs        = require('fs');
const app = express();

app.use(morgan('dev'));
app.use(helmet());
app.use(express.json());
app.use(cors());

static_path = 'data/';

app.get('/', (req, res) => {
    res.json({msg: "Iep, klk" });
});

app.post('/car', (req, res) => {
    const today = new Date();

    let rental = JSON.stringify({
        maker: req.body.maker,
        model: req.body.model,
        days:  req.body.days,
        units: req.body.units
    });
    let filename = static_path + today.getHours() + today.getMinutes() + today.getSeconds() + '.json'
    
    fs.appendFile(filename, rental, (err) => {
        if (err) return console.log(err);
        console.log('added ' + filename);
    }); 
    res.status(201);
    res.send(rental);
    res.end();
});

app.get('/car', (req, res) => {
    let cars = [];
    let files = fs.readdirSync(static_path);
    files.forEach((file, index) => {
        cars.push(JSON.parse(fs.readFileSync(static_path+file)));
        console.log(cars);
    });
    res.send(cars);
});


const port = process.env.PORT || 8000;
app.listen(port, () => {
    console.log(`Listening: http://localhost:${port}`);
});
