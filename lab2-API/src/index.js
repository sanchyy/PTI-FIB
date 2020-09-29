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

const data_path = 'data/data.json';

app.get('/', (req, res) => {
    res.json({msg: "Iep, klk" });
});

app.post('/car', (req, res) => {

    let rental = {
        maker: req.body.maker,
        model: req.body.model,
        days:  req.body.days,
        units: req.body.units
    }; 

    let cars = JSON.parse(fs.readFileSync(data_path));
    console.log(cars);
    cars['car'].push(rental)
    console.log(cars);

    fs.writeFileSync(data_path, JSON.stringify(cars), (err) => {
        if (err) return console.log(err);
        console.log('added new car');
    });
    res.status(201);
    res.send(rental);
    res.end();
});

app.get('/car', (req, res) => {
    res.send(JSON.parse(fs.readFileSync(data_path)));
});

const port = process.env.PORT || 8080;
app.listen(port, () => {
    if (!fs.existsSync(data_path)) {
        fs.appendFile(data_path, JSON.stringify({car: []}), (err) => {
            if (err) return console.log(err);
        });
    }
    console.log(`Listening: http://localhost:${port}`);
});
