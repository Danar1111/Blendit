const db = require('../config/db');
const predictClassification = require('../services/inferenceService');
const { validationResult } = require('express-validator');
const crypto = require('crypto');

exports.postPredictHandler = async (req, res, next) => {
    try {
        const { file } = req;
        const { skintone, undertone, skin_type } = req.body;

        if (!file) {
            return res.status(400).json({ error: 'No file uploaded.' });
        }

        const model = req.app.get('model');

        const { confidenceScore, label, description } = await predictClassification(model, file.buffer);
        const id = crypto.randomUUID();
        const createdAt = new Date().toISOString();

        const data = {
            id: id,
            shape: label,
            skintone: skintone,
            undertone: undertone,
            skin_type: skin_type,
            description: description,
            confidenceScore: confidenceScore,
            createdAt: createdAt
        };

        return res.status(201).json({
            status: 'success',
            message: confidenceScore > 60 ? 'Model is predicted successfully.' : 'Model is predicted successfully but under threshold. Please use the correct picture',
            data
        });
    } catch (error) {
        return res.status(400).json({ error: error.message });
    }
}

exports.listTutorial = async (req, res) => {
    try {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return res.status(400).json({ errors: errors.array() });
        }

        const { shape, skintone, undertone, skin_type } = req.query;

        const [rows] = await db.execute('SELECT * FROM recommendation WHERE skintone = ? AND undertone = ? AND skintype = ?',
            [skintone, undertone, skin_type]
        );

        const idForSearch = rows[0].id;

        const [result] = await db.execute('SELECT * FROM tutorial WHERE id_recommendation = ? AND facetype = ?',
            [idForSearch, shape]
        )

        res.status(200).send({ 
            error: false,
            message: 'Get tutorial successful',
            analystResult: {
                id: result[0].id,
                idRecommendation: result[0].id_recommendation,
                facetype: result[0].facetype,
                skin_preparation: result[0].skin_preparation.replace(/\r/g, ''),
                base_makeup: result[0].base_makeup.replace(/\r/g, ''),
                eye_makeup: result[0].eye_makeup.replace(/\r/g, ''),
                shade_lipstik: result[0].shade_lipstik.replace(/\r/g, ''),
                image_base: result[0].image_base.replace(/\r/g, ''),
                image_eye: result[0].image_eye.replace(/\r/g, ''),
                image_lips: result[0].image_lips.replace(/\r/g, '')
            } 
        });

    } catch (err) {
        console.error('Error fetching tutorial:', err);
        res.status(500).send({ error: true, message: 'Server error' });
    }
};

exports.productRecommendation = async (req, res) => {
    try {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return res.status(400).json({ errors: errors.array() });
        }

        const { skintone, undertone, skin_type } = req.query;

        const [total] = await db.execute('SELECT COUNT(*) as total FROM products WHERE skintone = ? AND undertone = ? AND skin_type = ?',
            [skintone, undertone, skin_type]
        );

        const totalItems = total[0].total;

        const [itemsNotLips] = await db.execute('SELECT * FROM products WHERE skintone = ? AND undertone = ? AND skin_type = ?',
            [skintone, undertone, skin_type]
        );

        const [itemsLips] = await db.execute('SELECT * FROM products WHERE undertone = ? AND makeup_type = "lip product"',
            [undertone]
        );

        const cleanedItemsNotLips = itemsNotLips.map(item => ({
            ...item,
            picture: item.picture.trim()
        }));

        const cleanedItemsLips = itemsLips.map(item => ({
            ...item,
            picture: item.picture.trim()
        }));

        const items = cleanedItemsNotLips.concat(cleanedItemsLips)

        res.status(200).send({ 
            error: false,
            message: 'Products items recommendation fetched successfully',
            totalItems: totalItems,
            items: items,
        });

    } catch (err) {
        console.error('Error fetching tutorial:', err);
        res.status(500).send({ error: true, message: 'Server error' });
    }
};