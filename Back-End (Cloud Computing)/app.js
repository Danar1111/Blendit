const express = require('express');
const app = express();
const authRoutes = require('./routes/auth');
const db = require('./config/db');
const loadModel = require('./services/loadModel');
const authMiddleware = require('./middlewares/authMiddleware');

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

let model;
(async () => {
    try {
        model = await loadModel();
        app.set('model', model);
        console.log('Model loaded successfully.');

        await db.execute('SELECT 1');
        console.log('Database connection successful');

        const PORT = process.env.PORT || 8080;
        app.listen(PORT, () => {
            console.log(`Server is running on port ${PORT}`);
        });
    } catch (err) {
        console.error('Initialization error:', err);
        process.exit(1);
    }
})();

app.use(authRoutes);

app.use((req, res, next) => {
    res.status(404).json({ error: true, message: 'Not Found' });
});

app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).json({ error: true, message: 'Internal Server Error' });
});
